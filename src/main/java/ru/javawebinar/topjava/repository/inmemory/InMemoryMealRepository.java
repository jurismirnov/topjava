package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(MEAL -> save(MEAL, MEAL.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(SecurityUtil.authUserId());
        }
        if (userId != SecurityUtil.authUserId()) {
            return null;
        }
        return repository.merge(meal.getId(), meal, (oldValue, newValue) -> newValue);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete{}", id);
        if (ValidationUtil.checkNotFoundWithId(repository.get(id), id).getUserId().equals(userId)) {
            return repository.remove(id) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get{}", id);
        Meal meal = ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        if (meal.getUserId().equals(userId)) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll{}", userId);
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}