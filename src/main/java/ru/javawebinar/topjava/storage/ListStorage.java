package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Meal> storage = new ArrayList<>();

    @Override
    void doSave(Meal meal, Object keyToSave) {
        storage.add(meal);
    }

    @Override
    void doUpdate(Meal meal, Object key) {
        storage.set((int) key, meal);
    }

    @Override
    Meal doGet(Object key) {
        return storage.get((int) key);
    }

    @Override
    void doDelete(Object key) {
        storage.remove((int) key);
    }

    @Override
    Integer getSearchKey(int mealId) {
        int i = 0;
        for (Meal meal : storage) {
            if (meal.getMealId() == mealId) {
                return i;
            }
            i++;
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public boolean checkExistence(int mealId) {
        for (Meal meal : getAll()) {
            if (meal.getMealId() == mealId) {
                return true;
            }
        }
        return false;
    }
}
