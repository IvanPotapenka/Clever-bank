<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 27.08.2023
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Открыть счет</title>
</head>
<body>
<c:if test="${create_account == null}">
<form method="post">
    <h1>Новый счёт</h1><br>
    <label for="account_id">Выберите тип счета</label>
    <select
            id="account_id"
            name="account">
        <option value="">--Счет--</option>
        <option value="CARD">Платежная карточка</option>
        <option value="DEPOSIT">Вклад</option>
    </select><br>
    <label for="currency_id">Выберите валюту</label>
    <select
            id="currency_id"
            name="currency">
        <option value="">--Валюта--</option>
        <option value="BYN">Белорусский рубль</option>
        <option value="USD">Доллар США</option>
        <option value="EURO">Евро</option>
        <option value="RUB">Российский рубль</option>
    </select><br>
    <button type="submit">Отправить заявку</button>
    </br>
    </c:if>

    <c:if test="${create_account == false}">
        <p style="font-size: 20px">Спасибо! Ваша заявка отправлена!</p>
    </c:if>
</form>
<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
