<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 27/11/2025
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<html>
<head>
    <title>Recherche Fiche de paie</title>
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

        form { margin-bottom: 30px; }
        label { display: block; text-align: left; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], select {
            width: 100%; padding: 8px; margin-bottom: 15px;
            border: 1px solid #ccc; border-radius: 5px;
        }
        input[type="submit"] {
            background-color: black; border: none; color: white;
            padding: 10px; font-size: 16px; width: 100%;
            cursor: pointer; border-radius: 5px;
        }
        input[type="submit"]:hover { background-color: #333; }
        .recherchefiches {
            background-color: white; padding: 25px 40px;
            border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title { display: flex; justify-content: center; align-items: center; }
        .dateperiode { display: flex; justify-content: center; align-items: center; }
        .fiches { width: inherit; display: flex; justify-content: center; }
        .tablefiches {
            width: 100%; border: 2px solid black;
            border-radius: 10px; border-collapse: collapse;
        }
        .tablefiches th, .tablefiches td {
            border: 1px solid #000; text-align: center;
        }
        .actionTD {
            padding-top: 20px;
            display: flex; justify-content: center; align-items: center;
        }
    </style>
</head>
<body>
<div class="recherchefiches">
    <div class="title">
        <h1>Rechercher les fiches de paie par période</h1>
    </div>

    <c:if test="${not empty errorMessagePeriodeDate}">
        <p style="color: red;">${errorMessagePeriodeDate}</p>
    </c:if>
    <c:if test="${not empty errorDatesPeriode}">
        <p style="color: red;">${errorDatesPeriode}</p>
    </c:if>

    <div class="dateperiode">
        <!-- Formulaire avec chemin dynamique -->
        <form action="${pageContext.request.contextPath}/../EmployeController/RechercherLesFichesDePaieController" method="get">
            <input value="${sessionScope.employeFoundMatricule.id_employe}" type="hidden" name="employe">
            <label for="startDate">Date du début de la période souhaitée : </label><br>
            <input type="date" id="startDate" name="startDate"><br><br>

            <label for="endDate">Date de la fin de la période souhaitée : </label><br>
            <input type="date" id="endDate" name="endDate" required><br><br>

            <input type="submit" value="Rechercher">
        </form>
    </div>

    <c:if test="${not empty messagePasDeFiche}">
        <p style="color: red;">${messagePasDeFiche}</p>
    </c:if>

    <div class="fiches">
        <table class="tablefiches">
            <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="fiche" items="${fiches}">
                <tr>
                    <td>${fiche.id_fiche_de_paie}</td>
                    <td><fmt:formatDate value="${fiche.dateFiche}" pattern="MMMM yyyy"/></td>
                    <td class="actionTD">
                        <!-- Formulaire avec chemin dynamique -->
                        <form action="${pageContext.request.contextPath}/../EmployeController/GenererUneFichePDFController" method="get" target="_blank">
                            <input type="hidden" name="id_employe" value="${sessionScope.employeFoundMatricule.id_employe}">
                            <input type="hidden" name="id_fiche_de_paie" value="${fiche.id_fiche_de_paie}">
                            <input type="submit" value="Générer PDF"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
