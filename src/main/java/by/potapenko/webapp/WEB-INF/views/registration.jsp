<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 25.08.2023
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form method="post">

    <c:if test="${create_user == null}">
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
        <h1>Registration</h1><br>
        <label for="name">ФИО</label>
        <input class="w3-round-large"
               type="text"
               maxlength="50"
               placeholder="Введите свое ФИО"
               name="full_name"
               id="name"
               required/><br>

        <c:if test="${email_error == true}">
            <p> Пользователь с таким email уже зарегистрирован!</p>
        </c:if>


        <label for="email">Email</label>
        <input class="w3-round-large"
               type="email"
               maxlength="50"
               placeholder="Введите email"
               name="email"
               id="email"
               required/><br>

        <label for="pwd">Password</label>
        <input class="w3-round-large"
               maxlength="20"
               minlength="8"
               type="password"
               placeholder="Введите пароль"
               name="password"
               id="pwd"
               required/></p><br>
        <button type="submit">Регистрация</button>
        </br>
        <p style="font-size: 12px">Если вы уже зарегистрированы,</p>
        <p style="font-size: 16px"><a href="/login">Войти в систему</a></p>
    </c:if>

    <c:if test="${create_user == true}">
        <p style="font-size: 20px"> Регистрация прошла успешно!</p>
        <a href="/login">Войти</a><br>
    </c:if>
    <c:if test="${create_user == false}">
        <p style="font-size: 20px"> Сожалеем, регистрация недоступна, попробуйте позже</p>
        <a href="/registration">Регистрация</a><br>
    </c:if>
</form>
</body>
</html>
