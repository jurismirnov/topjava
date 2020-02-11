package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

public abstract class AbstractStorage<SK> implements Storage {
    abstract void doSave(Meal meal, SK key);

    abstract void doUpdate(Meal meal, SK key);

    abstract Meal doGet(SK key);

    abstract void doDelete(SK key);

    abstract SK getSearchKey(int mealId);

    public void save(Meal meal) {
        SK result = doesNotExist(meal.getMealId());
        doSave(meal, result);
    }

    public void update(Meal meal) {
        SK result = doesExist(meal.getMealId());
        doUpdate(meal, result);
    }

    public void delete(int mealId) {
        SK result = doesExist(mealId);
        doDelete(result);
    }

    public Meal get(int mealId) {
        SK result = doesExist(mealId);
        return doGet(result);
    }

    private SK doesExist(int mealId) {
        SK searchKey = getSearchKey(mealId);
        return searchKey;
    }

    private SK doesNotExist(int mealId) {
        SK searchKey = getSearchKey(mealId);
        return searchKey;
    }

}
