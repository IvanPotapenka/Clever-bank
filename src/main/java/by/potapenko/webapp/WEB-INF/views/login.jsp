<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 25.08.2023
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post">
    <h1>Войти</h1>
    <c:if test="${find_user_error == true}">
        <p class="w3-text-red"> Адрес или пароль введены неверно!</p>
    </c:if>

    <label for="bank_id">Выберите банк</label>
    <select class="w3-round-large"
            id="bank_id"
            name="bank">
        <option value="">--Банк--</option>
        <option value="CLEVER">Clever-Bank</option>
        <option value="IRON">Iron-Bank</option>
        <option value="ALFA">Alfa-Bank</option>
        <option value="VTB">Vtb-Bank</option>
        <option value="TECHNO">Techno-Bank</option>
    </select><br>

    <label for="email"></label>
    <input class="w3-round-large"
           type="email"
           maxlength="20"
           placeholder="Введите email"
           name="email"
           id="email"
           required/><br>
    <label for="pwd"></label>

    <input class="w3-round-large"
           maxlength="20"
           minlength="8"
           type="password"
           placeholder="Введите пароль"
           name="password"
           id="pwd"
           required/></p>
    <button  type="submit">Войти в систему</button>
    </br>
    <p style="font-size: 14px">У вас нет аккаутна? <a href="${pageContext.request.contextPath}/registration">Регистрация</a>
</form>
</body>
</html>
