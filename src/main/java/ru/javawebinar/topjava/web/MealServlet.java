package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.Config;
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

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.sendRedirect("/topjava");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            String mealId = request.getParameter("mealId");
            if (action.equals("delete")) {
                storage.delete(Integer.parseInt(mealId));
                response.sendRedirect("/topjava");
                return;
            }
            if (action.equals("edit")) {
                Meal meal;
                if (mealId.equals("new")) {
                    meal = new Meal(ThreadLocalRandom.current().nextInt(0, 2_147_483_647), LocalDateTime.now(), "", 0);
                } else {
                    meal = storage.get(Integer.parseInt(mealId));
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                        .forward(request, response);
                return;
            }
            response.sendRedirect("/topjava");
            return;
        }
        String ccal = request.getParameter("ccal");
        if (ccal == null) {
            ccal = "1000000";
        }
        List<MealTo> mealsToList = MealsUtil.listMealsTo(storage.getAll(), Integer.parseInt(ccal));
        request.setAttribute("mealsToList", mealsToList);
        request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp")
                .forward(request, response);
    }
}


