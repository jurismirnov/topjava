package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapStorage implements Storage {

    Map<Integer, Meal> map = new ConcurrentHashMap<>();

    @Override
    public void update(Meal meal) {
        map.replace(meal.getMealId(), meal);
    }

    @Override
    public void save(Meal meal) {
        map.put(meal.getMealId(), meal);
    }

    @Override
    public Meal getById(int mealId) {
        return map.get(mealId);
    }

    @Override
    public void delete(int mealId) {
        map.remove(mealId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean checkExistence(int mealId) {
        return map.containsKey(mealId);
    }
}
