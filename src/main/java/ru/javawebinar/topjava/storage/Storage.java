package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void clear();

    void update(Meal meal);

    void save(Meal meal);

    Meal get(int mealId);

    void delete(int mealId);

    boolean checkExistence(int mealId);

    List<Meal> getAll();
}

