package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "/rest/profile/meals";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal createdMeal = super.create(meal);
        URI uriOfNewMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+ "/{id}")
                .buildAndExpand(createdMeal.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewMeal).body(createdMeal);
    }

    @RequestMapping(value = "/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam(required = false) LocalDate startDate,
                                   @RequestParam(required = false) LocalTime startTime,
                                   @RequestParam(required = false) LocalDate endDate,
                                   @RequestParam(required = false) LocalTime endTime
    ) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

}