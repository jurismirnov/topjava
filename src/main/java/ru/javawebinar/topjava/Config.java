package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDateTime;
import java.util.List;

public class Config {
    private static final Config INSTANCE = new Config();
    private Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }

    public List<MealTo> getMealToList(int maxCalories) {
        return MealsUtil.listMealsTo(storage.getAll(), maxCalories);
    }

    private Config() {
        storage = new ListStorage();
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
}
