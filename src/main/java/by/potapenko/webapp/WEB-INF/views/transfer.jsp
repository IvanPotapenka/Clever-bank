<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 30.08.2023
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Transfer</title>
</head>
<body>
<c:if test="${transfer_account_error == false}">
    <p style="font-size: 16px">Перевод прошел успешно!</p>
</c:if><br>

<c:if test="${account_id == true}">
<form method="post">

    <p style="font-size: 14px">
    <h1>Перевод</h1><br>
    <p style="font-size: 16px">Счёт оправителя: ${account.iban}</p>
    <p style="font-size: 16px">Баланс ${account.balance} ${account.currency}</p>
    <p style="font-size: 16px">Валюта текущего счета: ${account.currency}</p>
    <label for="account_id">Введите номер счёта получателя</label><br>
    <input
            type=text
            placeholder="28 символов"
            minlength="28"
            maxlength="28"
            name="iban"
            id="account_id"
    /></br>
    <label for="currency_id">Выберите валюту</label><br>
    <select
            id="currency_id"
            name="currency">
        <option value="">--Валюта--</option>
        <option value="BYN">Белорусский рубль</option>
        <option value="USD">Доллар США</option>
        <option value="EURO">Евро</option>
        <option value="RUB">Российский рубль</option>
    </select></br>

    <label for="deposit_id">Введите сумму</label><br>
    <input
            type=number
            minlength="0"
            placeholder="0.00"
            name="amount"
            id="deposit_id"
    />
    <input hidden type="number" name="id" value="${account.id}"/>
    <input hidden type="text" name="type" value="TRANSFER"/><br>
    <button type="submit">Перевести</button>
    </br>
</form>
</c:if>

<c:if test="${iban_error == true}">
    <p>Счёт отправителя и счёт получателя не должны совпадать</p>
</c:if>

<c:if test="${balance_error == true}">
    <p style="font-size: 16px">Недостаточно средств</p>
</c:if>

<c:if test="${transfer_account_error == true}">
    <p style="font-size: 16px">Произошла ошибка, попробуйте позже</p>
</c:if><br>

<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
