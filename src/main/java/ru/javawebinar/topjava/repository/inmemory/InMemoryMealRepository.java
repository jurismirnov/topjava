package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

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
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete{}", id);
        if (repository.get(id).getUserId().equals(userId)) {
            return repository.remove(id) != null;
        } else {
            return false;
            //throw new NotFoundException("Repository delete: Meal not found or belongs to another user!");
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get{}", id);
        Meal meal = repository.get(id);
        if (meal.getUserId().equals(userId)) {
            return meal;
        } else {
            return null;
            //throw new NotFoundException("Repository get: Meal not found or belongs to another user!");
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