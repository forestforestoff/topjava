<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meal</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt style="font-size: 18px">DateTime:</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}" required></dd>
    </dl>
    <dl>
        <dt style="font-size: 18px">Description:</dt>
        <dd><input type="text" name="description" value="${meal.description}" size=21 required autocomplete="on"></dd>
    </dl>
    <dl>
        <dt style="font-size: 18px">Calories:</dt>
        <dd><input type="number" name="calories" value="${meal.calories}" required></dd>
    </dl>

    <button type="submit">Save</button>
    <button type="reset">Reset</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>