package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_BY_TIMESTAMP_DESC = Sort.by(Sort.Direction.ASC, "dateTime");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getOne(userId));
        if (!meal.isNew()) {
            if (get(meal.getId(), userId)== null) {
                return null;
            }
        }
        crudRepository.save(meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        if (meal != null) {
            crudRepository.delete(meal);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(Objects::nonNull)
                .filter(m -> m.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }
}
