<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Home Page</title>
    </head>
    <body>
    <%@include file="/WEB-INF/views/header.jspf" %>

    <h1>Home Page</h1>

    <c:choose>
        <c:when test="${loggedUserNickname=='Guest' || loggedUserNickname==''}">
            <p> Please log in </p>
            <IMG SRC="http://tnijurl.com/underconstruction/" ALT="workinprogress">
        </c:when>
        <c:otherwise>
            <a href="<c:url value='/-${loggedUserNickname}-contacts' />"> Contacts </a>
        </c:otherwise>
    </c:choose>
    </body>
</html>