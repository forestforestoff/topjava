<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="1" cellpadding="5">
    <tr>
        <th colspan="4" style="font-size: 20px">Meals</th>
        <td><a href="meals?action=add"><img src="img/add.png"></a></td>
    </tr>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    <c:forEach var="meals" items="${mealList}">
        <jsp:useBean id="meals"
                     type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="background-color: ${meals.excess ? "palevioletred" : "lightgreen"}">
            <td><%= TimeUtil.formatDateTime(meals.getDateTime())%>
            </td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
            <td><a href="meals?id=${meals.id}&action=delete"><img src="img/delete.png"></a></td>
            <td><a href="meals?id=${meals.id}&action=edit"><img src="img/pencil.png"></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
