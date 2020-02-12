package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ConcurrentHashMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class MealServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) {
        storage = new ConcurrentHashMapStorage();
        storage.save(new Meal(1, LocalDateTime.of(2020, 1, 1, 7, 20), "Завтрак", 700));
        storage.save(new Meal(2, LocalDateTime.of(2020, 1, 1, 14, 30), "Обед", 2700));
        storage.save(new Meal(3, LocalDateTime.of(2020, 1, 1, 19, 00), "Ужин", 600));
        storage.save(new Meal(4, LocalDateTime.of(2020, 1, 2, 7, 20), "Завтрак", 780));
        storage.save(new Meal(5, LocalDateTime.of(2020, 1, 2, 14, 30), "Обед", 2300));
        storage.save(new Meal(6, LocalDateTime.of(2020, 1, 2, 19, 00), "Ужин", 400));
        storage.save(new Meal(7, LocalDateTime.of(2020, 1, 3, 7, 20), "Завтрак", 500));
        storage.save(new Meal(8, LocalDateTime.of(2020, 1, 3, 14, 30), "Обед", 2000));
        storage.save(new Meal(9, LocalDateTime.of(2020, 1, 3, 19, 00), "Ужин", 800));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("mealId");
        String date = request.getParameter("date");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate locDate = LocalDate.parse(date, dateFormatter);
        String time = request.getParameter("time");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H : mm");
        LocalTime locTime = LocalTime.parse(time, timeFormatter);
        String description = request.getParameter("description");
        String cals = request.getParameter("cCals");
        Meal meal = new Meal(Integer.parseInt(mealId), LocalDateTime.of(locDate, locTime), description, Integer.parseInt(cals));
        if (storage.checkExistence(meal.getMealId())) {
            storage.update(meal);
        } else {
            storage.save(meal);
        }
        response.sendRedirect("");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            String mealId = request.getParameter("mealId");
            if (action.equals("delete")) {
                storage.delete(Integer.parseInt(mealId));
                response.sendRedirect("meals");
                return;
            }
            if (action.equals("edit")) {
                Meal meal;
                if (mealId.equals("new")) {
                    meal = new Meal(LocalDateTime.now(), "", 0);
                } else {
                    meal = storage.getById(Integer.parseInt(mealId));
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp")
                        .forward(request, response);
                return;
            }
            response.sendRedirect("meals");
            return;
        }
        String ccal = request.getParameter("ccal");
        if (ccal == null) {
            ccal = "1000000";
        }
        List<MealTo> mealsToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, Integer.parseInt(ccal));
        request.setAttribute("mealsToList", mealsToList);
        request.getRequestDispatcher("meals.jsp")
                .forward(request, response);
    }
}


