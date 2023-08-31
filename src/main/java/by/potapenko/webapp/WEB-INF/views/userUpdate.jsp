<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 25.08.2023
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<h1>${user.fullName}</h1><br>

<c:if test="${update_user_error == false}">
    <p>Клиент успешно обновлен!</p>
</c:if><br>

<c:if test="${update_user_error == null}">

   <form method="post"> <label for="name">ФИО</label>
    <input
           type="text"
           maxlength="50"
           placeholder="ФИО полностью"
           name="full_name"
           value="${user.fullName}"
           id="name"
           required/><br>

    <label for="email">Email</label>
    <input class="w3-round-large"
           type="email"
           maxlength="50"
           placeholder="email"
           name="email"
           value="${user.email}"
           id="email"
           required/><br>
    <label for="pwd">Password</label>
    <input class="w3-round-large"
           maxlength="20"
           minlength="8"
           type="password"
           placeholder="пароль не менее 8 символов"
           name="password"
           id="pwd"
           required/></p><br>

    <input hidden type="number" name="id" value="${user.id}"/>
    <button type="submit">Update</button>
    </br>
   </form>
</c:if>
<c:if test="${update_user_error == true}">
    <p style="font-size: 20px" class="w3-text-red">Ошибка, попробуйте позже!</p>
</c:if><br>

<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
