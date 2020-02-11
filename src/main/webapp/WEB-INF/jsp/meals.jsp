<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals List</title>

</head>
<body>
<h1>Meals List</h1>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>
            Дата
        </th>
        <th>
            Время
        </th>
        <th>
            Описание
        </th>
        <th>
            Количество килокалорий
        </th>
        <th>
        </th>
        <th>
        </th>
        <c:forEach items="${mealsToList}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:if test="${mealTo.excess}">
    <tr style="background: indianred">
        </c:if>
        <c:if test="${!mealTo.excess}">
    <tr>
        </c:if>
        <td><%=DateTimeFormatter.ofPattern("dd MMM yyyy").format(mealTo.getDateTime())%>
        </td>
        <td><%=DateTimeFormatter.ofPattern("H : mm").format(mealTo.getDateTime())%>
        </td>
        <td>${mealTo.description}
        </td>
        <td align="center">${mealTo.calories}
        </td>
        <td><a href="meals.jsp/?action=delete&mealId=${mealTo.mealId}">Удалить</a>
        </td>
        <td><a href="edit.jsp/?action=edit&mealId=${mealTo.mealId}">Изменить</a>
        </td>
    </tr>
    </c:forEach>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><a href="edit.jsp/?action=edit&mealId=new">Добавить</a></td>
    </tr>
</table>
</body>
</html>
