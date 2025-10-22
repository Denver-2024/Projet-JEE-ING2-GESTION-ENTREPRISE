<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 21/10/2025
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Model</title>
</head>
<body>
<%
    session.getAttribute("employeFound");

%>
<form  action="${pageContext.request.contextPath}/employe" method="post">
<input type="submit">
<label>${sessionScope.employeFound.id_employe}</label>
<label>${sessionScope.employeFound.nom}</label>
<label>${sessionScope.employeFound.prenom}</label>
<label>${sessionScope.employeFound.adresse}</label>
<label>${sessionScope.employeFound.id_departement}</label>
<label>${sessionScope.employeFound.numero}</label>
<label>${sessionScope.employeFound.email}</label>
<label>${sessionScope.employeFound.sexe}</label>
<label>${sessionScope.employeFound.grade}</label>
<label>${sessionScope.employeFound.id_role}</label>
</form>

<%
    session.getAttribute("departementFound");

%>
<form  action="${pageContext.request.contextPath}/departement" method="post">
    <input type="submit">
    <label>${sessionScope.departementFound.id_departement}</label>
    <label>${sessionScope.departementFound.nom}</label>
    <label>${sessionScope.departementFound.description}</label>
    <label>${sessionScope.departementFound.directeur}</label>

</form>


</body>
</html>
