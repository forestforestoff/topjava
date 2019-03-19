package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new MealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.equals("") ? null : Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        log.info(meal.getId() == null ? "Add" : "Update {}", id);
        mealStorage.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.info("GetAll");
            request.setAttribute("mealList", MealsUtil.getFilteredWithExcess(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealStorage.delete(id);
                response.sendRedirect("meals");
                break;
            case "edit":
                Meal meal = mealStorage.get(getId(request));
                setForward(meal, request, response);
                break;
            case "add":
                Meal emptyMeal = new Meal(LocalDateTime.now(), "", 0);
                setForward(emptyMeal, request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }

    private void setForward(Meal meal, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealChange.jsp").forward(request, response);
    }
}
