<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 21/11/2025
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Recherche Employé</title>
    <style>

        body{
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;

            font-family: 'Times New Roman', serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 50px;
        }


        form {
            margin-bottom: 30px;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover {
            background-color: #333;
        }
        .rechercheE{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .fiches{
            width: inherit;
            display: flex;
            justify-content: center;
        }
        .tablemployes{
            width: 100%;
            border: 2px solid black;
            border-radius: 10px;
            border-collapse: collapse;

        }
        .tablemployes th, .tablemployes td{
            border: 1px solid #000;
            text-align: center;
            padding-left: 5px;
            padding-right: 5px;

        }
        .actionTD{
            padding-top: 20px;

            display: flex;
            justify-content: center;
            align-items: center;
        }


    </style>

</head>
<body>
<div class="rechercheE">
    <form action="${pageContext.request.contextPath}/../EmployeController/RechercheEmployeController" method="get">
        <div class="title">
            <h1> Rechercher un employé </h1>
        </div>
        <c:if test="${not empty errorMessageAucunParametre}">
            <p style="color: red;">${errorMessageAucunParametre}</p>
        </c:if>
        <c:if test="${not empty messageAucunEmploye}">
            <p style="color: red;">${messageAucunEmploye}</p>
        </c:if>
        <label for="nom">Nom : </label>
        <input type="text" name="nom" id="prenom"><br>
        <label for="prenom">Prénom : </label>
        <input type="text" name="prenom" id="prenom"><br>
        <label for="departement">Département : </label>
        <select id="departement" name="id_departement" >
            <option value="" disabled selected hidden>-- Sélectionner un département --</option>
            <!-- Then show all other departments -->
            <c:forEach var="d" items="${applicationScope.departementsFound}">

                <option value="${d.id_departement}">
                        ${d.nom}
                </option>
            </c:forEach>
        </select><br><br>


        <label for="grade">Grade :  </label>
        <select name="grade">
            <option value="" disabled selected hidden>-- Sélectionner un grade --</option>
            <c:set var="grades" value="${fn:split('JUNIOR,INTERMEDIAIRE,SENIOR', ',')}" />

            <c:forEach var="g" items="${grades}">
                <option value="${g}">${g}</option>
            </c:forEach>

        </select><br>
        <label>Rôle : </label>
        <select name="role" >
            <option value="" disabled selected hidden>-- Sélectionner un rôle --</option>
            <c:forEach var="r" items="${applicationScope.rolesFound}">
                <option value="${r.id_role}">${r.nom}</option>
            </c:forEach>
        </select><br><br>
        <input type="submit" value="Rechercher">
    </form>
    <div class="empoloyes">
        <c:if test="${not empty employes}">
            <table class="tablemployes">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Rôle</th>
                    <th>Département</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="employe" items="${employes}">
                    <tr>
                        <td>${employe.nom}</td>
                        <td>${employe.prenom}</td>
                        <td>${employe.role.nom}</td>
                        <c:forEach var="d" items="${applicationScope.departementsFound}">
                            <c:if test="${d.id_departement == employe.id_departement}">
                                <td>${d.nom}</td>
                            </c:if>
                        </c:forEach>
                        <td class="actionTD">
                            <form action="${pageContext.request.contextPath}/" method="get" >
                                <input type="hidden" name="id_employe" value="${employe.id_employe}">
                                <input type="submit" value="Sélectionner"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>


</body>
</html>
