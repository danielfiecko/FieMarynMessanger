<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Registration Form</title>

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>

	<h2>Personal Informations</h2>
 
	<form:form method="POST" modelAttribute="user">
		<form:input type="hidden" path="id" id="id"/>
		<table>
			<tr>
				<td><label for="firstName">First Name: </label> </td>
				<td><form:input path="firstName" id="firstName"/></td>
				<td><form:errors path="firstName" cssClass="error"/></td>
		    </tr>

			<tr>
				<td><label for="secondName">Second Name: </label> </td>
				<td><form:input path="secondName" id="secondName"/></td>
			</tr>

			<tr>
				<td><label for="nickname">Nickname: </label> </td>
				<td><form:input path="nickname" id="nickname"/></td>
				<td><form:errors path="nickname" cssClass="error"/></td>
		    </tr>

			<tr>
				<td><label for="password">Password: </label> </td>
				<td><form:input type="password" path="password" id="password"/></td>
			</tr>

			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
	<br/>
	<br/>
	<a href="<c:url value='/index' />">Back</a>
</body>
</html>