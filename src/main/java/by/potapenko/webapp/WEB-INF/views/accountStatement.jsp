<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 30.08.2023
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Statement</title>
</head>
<body>

<c:if test="${account_id == false}">
    <p>Такой счёт не существует или закрыт</p>
</c:if>

<c:if test="${account_id == true}">

    <h4>Выписка по счёту ${account.iban}</h4>

    <table style="font-size: 14px">
        <tr>
            <td>Клиент</td>
            <td>${user.fullName}</td>
        </tr>
        <tr>
            <td>Счёт</td>
            <td>${account.iban}</td>
        </tr>
        <tr>
            <td>Валюта</td>
            <td>${account.currency}</td>
        </tr>
        <tr>
            <td>Дата открытия</td>
            <td>${account.dateOfCreation}</td>
        </tr>
        <tr>
            <td>Период</td>
            <td>${date_from} - ${date_to}</td>
        </tr>
        <tr>
            <td>Дата и время формирования</td>
            <td>${date_time}</td>
        </tr>
        <tr>
            <td>Остаток</td>
            <td>${account.balance} ${account.currency}</td>
        </tr>
        <td>

        </td>
        <tr>
            <td>Дата</td>
            <td>Примечание</td>
            <td>Сумма</td>
        </tr>
        <td>
            ----------------------------
        </td>
        <td>
            ----------------------------
        </td>
        <td>
            ----------------------------
        </td>

        <c:forEach var="transaction" items="${transactions}">
            <tr>
                <td>${transaction.date}</td>
                <td>${transaction.type.getTITLE()}</td>
                <td>${transaction.amount}</td>
            </tr>

        </c:forEach>
    </table>
</c:if>
<p style="font-size: 14px">
    <button onclick="history.go(-1)">Вернуться назад</button>
</p>
</body>
</html>
