package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void update(Meal meal);

    void save(Meal meal);

    Meal getById(int mealId);

    void delete(int mealId);

    List<Meal> getAll();

    boolean checkExistence(int mealId);
}

