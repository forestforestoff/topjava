package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;
    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        return MealsUtil.getWithExcess(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getSeparatelyFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getWithExcess(service
                .getDTFiltered(LocalDateTime.of(fixedStartDate(startDate), fixedStartTime(startTime)), LocalDateTime.of(fixedEndDate(endDate), fixedEndTime(endTime)),
                        authUserId()), authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    private LocalTime fixedStartTime(LocalTime time) {
        return time == null ? LocalTime.MIN : time;
    }

    private LocalTime fixedEndTime(LocalTime time) {
        return time == null ? LocalTime.MAX : time;
    }

    private LocalDate fixedStartDate(LocalDate date) {
        return date == null ? MIN_DATE : date;
    }

    private LocalDate fixedEndDate(LocalDate date) {
        return date == null ? MAX_DATE : date;
    }
}