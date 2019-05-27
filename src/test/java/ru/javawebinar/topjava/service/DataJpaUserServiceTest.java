package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void testGetWithMeals() throws Exception {
        User adminWithMeals = service.getWithMeals(ADMIN_ID);
        assertMatch(adminWithMeals, ADMIN);
        MealTestData.assertMatch(adminWithMeals.getMeals(), MealTestData.ADMIN_MEALS);
    }

    @Test
    public void testGetWithMealsNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithMeals(777);
    }
}
