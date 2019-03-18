package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorage implements Storage {

    private ConcurrentMap<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public MealStorage() {
        for (Meal meal : MealsUtil.MEALS) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
        }
    }

    @Override
    public void update(Meal meal) {
        if (meal.getId() != null) {
            mealMap.replace(meal.getId(), meal);
        }
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
