<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login User Form</title>

    <style>

        .error {
            color: #ff0000;
        }
    </style>

</head>

<body>

<h2>Login Form</h2>

<form:form method="POST" modelAttribute="entryUser">
    <form:input type="hidden" path="id" id="id"/>
    <table>
        <tr>
            <td><label for="nickname">Nickname: </label></td>
            <td><form:input path="nickname" id="nickname"/></td>
            <td><form:errors path="nickname" cssClass="error"/></td>
        </tr>
        <tr>
            <td><label for="password">Password: </label></td>
            <td><form:input type="password" path="password" id="password"/></td>
            <td><form:errors path="password" cssClass="error"/></td>
        </tr>

        <tr>
            <td colspan="3">
                <input type="submit" value="Log In"/>
            </td>
        </tr>
    </table>
</form:form>

<br/>
<br/>
<a href="<c:url value='/index' />">Back</a>
</body>
</html>