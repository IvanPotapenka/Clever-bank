<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customers</title>
</head>
<body>
<c:if test="${user_delete_success==true}">
    <p style="font-size: 20px"> Клиент был успешно удален!</p>
    <p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</c:if>
<c:if test="${user_delete_success==null}">
<p style="font-size: 16px">
<h1>Customers</h1>
    <a href="users/create" >Добавить клиента</a><br>
<table>
    <tr>
        <th>Номер клиента</th>
        <th>ФИО</th>
        <th>Email</th>
        <th>Редактировать</th>
        <th>Удалить</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.fullName}</td>
            <td>${user.email}</td>
            <td><a href="${pageContext.request.contextPath}/users/user/update?id=${user.id}">Редактировать</a></td>
            <td><a href="${pageContext.request.contextPath}/users/user/delete?id=${user.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>

</c:if>
</body>
</html>