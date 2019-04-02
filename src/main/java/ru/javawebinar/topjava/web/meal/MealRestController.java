package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        return MealsUtil.getWithExcess(service.getAll(getAuthUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getSeparatelyFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> meals = service.getDtFiltered(
                fixedStartDate(startDate),
                fixedEndDate(endDate),
                getAuthUserId());
        return MealsUtil.getFilteredWithExcess(meals, authUserCaloriesPerDay(), fixedStartTime(startTime), fixedEndTime(endTime));
    }

    public Meal get(int id) {
        return service.get(id, getAuthUserId());
    }

    public void delete(int id) {
        service.delete(id, getAuthUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, getAuthUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, getAuthUserId());
    }

    private LocalTime fixedStartTime(LocalTime time) {
        return time == null ? LocalTime.MIN : time;
    }

    private LocalTime fixedEndTime(LocalTime time) {
        return time == null ? LocalTime.MAX : time;
    }

    private LocalDate fixedStartDate(LocalDate date) {
        return date == null ? LocalDate.MIN : date;
    }

    private LocalDate fixedEndDate(LocalDate date) {
        return date == null ? LocalDate.MAX : date;
    }
}