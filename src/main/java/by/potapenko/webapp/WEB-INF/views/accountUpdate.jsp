<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 29.08.2023
  Time: 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account deposit</title>
</head>
<body>



<c:if test="${update_account_error == null}">

    <h1>Мой счёт</h1>
    <table style="font-size: 12px">
        <tr>
            <th>Номер счёта</th>
            <th>Дата окрытия</th>
            <th>Тип счёта</th>
            <th>Валюта</th>
            <th>Баланс</th>

        </tr>
            <tr>
                <td>${account.iban}</td>
                <td>${account.dateOfCreation}</td>
                <td>${account.account}</td>
                <td>${account.currency}</td>
                <td>${account.balance}</td>
            </tr>
    </table>
</c:if>

<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
