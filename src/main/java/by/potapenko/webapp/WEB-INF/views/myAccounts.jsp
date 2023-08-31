<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 27.08.2023
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Мои счета</title>
</head>
<body>

<c:if test="${my_accounts == false}">
    <p style="font-size: 16px">У вас еще нет счетов</p>
</c:if>

<c:if test="${my_accounts == null}">
    <h4>Мои счета</h4>

    <table style="font-size: 14px">

    <c:forEach var="account" items="${accounts}">

        <tr>
            <td>Клиент</td>
            <td>${user.fullName}</td>
        </tr>
        <tr>
            <td>Номер счёта</td>
            <td>${account.iban}</td>
        </tr>
        <tr>
            <td>Дата окрытия</td>
            <td>${account.dateOfCreation}</td>
        </tr>
        <tr>
            <td>Тип счёта</td>
            <td>${account.account.getTITLE()}</td>
        </tr>
        <tr>
            <td>Валюта</td>
            <td>${account.currency}</td>
        </tr>
        <tr>
            <td>Баланс</td>
            <td>${account.balance}</td>
        </tr>
        <c:if test="${account.account == 'DEPOSIT'}">
        <tr>
            <td>Сумма процентов</td>
            <td>${account.interestAmount}</td>
        </tr>
        </c:if>
        <tr>
            <td><button><a href="${pageContext.request.contextPath}/my-accounts/deposit/account?id=${account.id}">Пополнить</a></button>
            </td>
            <td><button><a href="${pageContext.request.contextPath}/my-accounts/withdraw/account?id=${account.id}">Снять</a></button>
            </td>
            <td><button><a href="${pageContext.request.contextPath}/my-accounts/transfer/account?id=${account.id}">Перевести</a></button>
            </td>
            <form action="/my-accounts/account-statement" method="post">
            <td>
                <input  type="date"
                       min="2023-01-01"
                       max="2024-01-01"
                       name="date_from"
                       id="date_from_id"
                       required/>
            </td>
            <td>
                <input  type="date"
                        min="2023-01-01"
                        max="2024-01-01"
                        name="date_to"
                        id="date_to_id"
                        required/>
            </td>
            <td>
                <input hidden type="number" name="id" value="${account.id}"/>
                <button type="submit">Выписка</button>
            </td>
            </form>
        </tr>
        _____________________________________________________________________________________
    </c:forEach>
    </table>
</c:if>
<p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</body>
</html>
