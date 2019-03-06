package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> mealsWithExceeds = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal meal : mealList) {
            caloriesPerDayMap.put(meal.getDateTime().toLocalDate(),
                    caloriesPerDayMap.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories());
        }

        for (UserMeal usermeal : mealList) {
            if (TimeUtil.isBetween(usermeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExceeds.add(new UserMealWithExceed(usermeal.getDateTime(),
                        usermeal.getDescription(),
                        usermeal.getCalories(),
                        (caloriesPerDayMap.getOrDefault(usermeal.getDateTime().toLocalDate(), 0) > caloriesPerDay)));
            }
        }

        return mealsWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayMap = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(),
                        m.getDescription(),
                        m.getCalories(),
                        (caloriesPerDayMap.getOrDefault(m.getDateTime().toLocalDate(), 0) > caloriesPerDay))).collect(Collectors.toList());
    }
}
