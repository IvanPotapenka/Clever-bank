<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 29.08.2023
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Withdraw</title>
</head>
<body>

<c:if test="${withdraw_account_error == false}">
    <p style="font-size: 16px">Вы успешно сняли средства со своего счёта!</p>
</c:if>

<c:if test="${withdraw_account_error == null}">
<form method="post">

    <c:if test="${account_id == false}">
        <p>Такой счёт не существует или закрыт</p>
    </c:if>

    <c:if test="${account_id == true}">
        <p style="font-size: 16px">
        <h1>Снять со счёта</h1>

        <p style="font-size: 16px">Счёт для снятия: ${account.iban}</p>
        <p style="font-size: 16px">Валюта текущего счета - ${account.currency}</p>

        <label for="withdraw_id">Введите сумму</label>
        <input
                type=number
                minlength="0"
                placeholder="0.00"
                name="amount"
                id="withdraw_id"/>
        <input hidden type="number" name="id" value="${account.id}"/>
        <input hidden type="text" name="account_recipient" value="${account.iban}"/>
        <input hidden type="text" name="type" value="WITHDRAWAL"/>
        <button type="submit">Снять</button>
    </c:if>
</form>
</c:if>

<c:if test="${balance_error == true}">
    <p style="font-size: 16px">Недостаточно средств</p>
</c:if>

<c:if test="${withdraw_account_error == true}">
    <p style="font-size: 16px">Произошла ошибка, попробуйте позже</p>
</c:if>

<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
