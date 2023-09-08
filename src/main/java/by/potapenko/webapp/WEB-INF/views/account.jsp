<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 26.08.2023
  Time: 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>${bank.bankName}</h2><br>
<h3>Msr. ${user.fullName}</h3>

<p style="font-size: 16px"><button><a href="/account/my-accounts">Мои счета</a></button></p>
<p style="font-size: 16px"><button><a href="/account/bank-account/create">Открыть счёт</a></button></p><br>
<p style="font-size: 16px"><button><a href="/logout">Выйти из системы</a></button></p>
</body>
</html>
