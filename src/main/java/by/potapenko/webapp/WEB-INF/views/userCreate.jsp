<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 25.08.2023
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create</title>
</head>
<body>
<c:if test="${create_user == null}">
  <form method="post">
      <h1>Клиент</h1><br>
    <label for="name">ФИО</label>
    <input
           type="text"
           maxlength="50"
           placeholder="ФИО полностью"
           name="full_name"
           id="name"
           required/><br>
    <button type="submit">Создать</button>
    </br>
      <p style="font-size: 14px"><button onclick="history.go(-1)">Вернуться назад</button></p>
</c:if>

<c:if test="${create_user == false}">
    <p style="font-size: 20px"> Извините, произошла ошибка, попробуйте снова </p>
    <a href="users/create" >Добавить клиента</a><br>
</c:if>
</form>
</body>
</html>
