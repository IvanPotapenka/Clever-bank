<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 29.08.2023
  Time: 8:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Deposit</title>
</head>
<body>
<c:if test="${deposit_account_error == false}">
    <p style="font-size: 16px">Вы успешно пополнили свой счёт!</p>
</c:if><br>

<form method="post">
    <c:if test="${account_id == false}">
        <p>Такой счёт не существует или закрыт</p>
    </c:if>
    <c:if test="${account_id == true}">
        <p style="font-size: 16px">
        <h1>Пополнить счёт</h1>

        <p style="font-size: 16px">Счёт для пополнения: ${account.iban}</p>
        <p style="font-size: 16px">Валюта текущего счета - ${account.currency}</p>
        <label for="deposit_id">Введите сумму</label>
        <input
                type=number
                minlength="0"
                placeholder="0.00"
                name="amount"
                id="deposit_id"
        /><br>
        <input hidden type="number" name="id" value="${account.id}"/>
        <input hidden type="text" name="type" value="REPLENISHMENT"/>
        <button type="submit">Пополнить</button>
    </c:if>
</form>

<c:if test="${deposit_account_error == true}">
    <p style="font-size: 16px">Произошла ошибка, попробуйте позже</p>
</c:if><br>
<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
