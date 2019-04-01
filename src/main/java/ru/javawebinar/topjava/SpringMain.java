package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println("***block for meals***");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println("***create new meal \"Чипсики\"***");
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), "Чипсики", 700));
            System.out.println("***output from 30.05.2015 15:00 to 31.05.2015 17:00***");
            List<MealTo> mealTos = mealRestController.getSeparatelyFiltered(
                    LocalDate.of(2015, Month.MAY, 30), LocalTime.of(15, 0),
                    LocalDate.of(2015, Month.MAY, 31), LocalTime.of(17, 0));
            mealTos.forEach(System.out::println);
            System.out.println("***getall***");
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("***delete meal with id=7***");
            mealRestController.delete(7);
            System.out.println("***getall***");
            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
