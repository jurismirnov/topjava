<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Редактируем запись</title>
</head>
<body>

<form method="post" action="topjava" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="mealId" value="${meal.mealId}">
    <dl>
        <dt>Дата</dt>
        <dd><input type="text" name='date' size=50
                   value="<%=DateTimeFormatter.ofPattern("dd MMM yyyy").format(meal.getDateTime())%>"></dd>
    </dl>
    <dl>
        <dt>Время</dt>
        <dd><input type="text" name='time' size=7
                   value="<%=DateTimeFormatter.ofPattern("H : mm").format(meal.getDateTime())%>"></dd>
    </dl>
    <dl>
        <dt>Описание</dt>
        <dd><input type="text" name='description' size=255 value="${meal.description}"></dd>
    </dl>
    <dl>
        <dt>Кол-во килокалорий</dt>
        <dd><input type="text" name='cCals' size=50 value="${meal.calories}"></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()">Отменить</button>
</form>

</body>
</html>
