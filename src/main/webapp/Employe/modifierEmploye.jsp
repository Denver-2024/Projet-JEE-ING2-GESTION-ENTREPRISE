<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 21/11/2025
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
<head>
    <title>Modifier les informations de l'employé</title>
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
        .modif{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);

        }
        .nextAction{
            display: flex;
            justify-content: space-evenly;
            align-items: center;
        }


    </style>

</head>
<body>
<div class="modif">

<h1>Modification des informations de l'employé avec le matricule: ${sessionScope.employeFoundMatricule.id_employe}</h1>
<c:if test="${not empty errorMessageModifInputNotFilled}">
    <p style="color:red; font-weight:bold;">
            ${errorMessageModifInputNotFilled}
    </p>
</c:if>
    <c:if test="${not empty messagePasDeModification}">
        <p style="color: red;">${messagePasDeModification}</p>
    </c:if>
<div>
<form id="myForm" action="${pageContext.request.contextPath}/verification-modification-employe" method="post">
    <label for="nom">Nom :  </label>
    <input type="text" id="nom" name="nom" value="${sessionScope.employeFoundMatricule.nom}" ><br>

    <label for="prenom">Prénom :  </label>
    <input type="text" id="prenom" name="prenom" value="${sessionScope.employeFoundMatricule.prenom}" ><br>

    <label for="adresse">Adresse :  </label>
    <input type="text" id="adresse" name="adresse" value="${sessionScope.employeFoundMatricule.adresse}"><br>

    <label>Département : </label>
    <select id="departement" name="id_departement" data-original="${sessionScope.employeFoundMatricule.departement.id_departement}" required>
        <!-- First, show the current department as selected -->
        <c:forEach var="d" items="${applicationScope.departementsFound}">
            <c:if test="${d.id_departement == sessionScope.employeFoundMatricule.departement.id_departement}">
                <option value="${d.id_departement}" selected>
                        ${d.nom}
                </option>
            </c:if>
        </c:forEach>

        <!-- Then show all other departments -->
        <c:forEach var="d" items="${applicationScope.departementsFound}">
            <c:if test="${d.id_departement != sessionScope.employeFoundMatricule.departement.id_departement}">
                <option value="${d.id_departement}">
                        ${d.nom}
                </option>
            </c:if>
        </c:forEach>
    </select><br><br>

    <label for="numero">Numéro de téléphone :  </label>
    <input type="text" id="numero" name="numero" value="${sessionScope.employeFoundMatricule.numero}"><br>

    <label for="email">Email :  </label>
    <input type="text" id="email" name="email" value="${sessionScope.employeFoundMatricule.email}"><br>

    <label for="sexe">Sexe :  </label>
    <select name="sexe">
        <option  value="${sessionScope.employeFoundMatricule.sexe}" selected>${sessionScope.employeFoundMatricule.sexe}</option>
        <c:set var="sexes" value="${fn:split('M,F,X', ',')}" />

        <c:forEach var="s" items="${sexes}">
            <c:if test="${s != sessionScope.employeFoundMatricule.sexe}">
                <option value="${s}">${s}</option>
            </c:if>
        </c:forEach>

    </select><br>

    <label for="grade">Grade :  </label>
    <select name="grade">
        <option  value="${sessionScope.employeFoundMatricule.grade}" selected>${sessionScope.employeFoundMatricule.grade}</option>
        <c:set var="grades" value="${fn:split('JUNIOR,INTERMEDIAIRE,SENIOR', ',')}" />

        <c:forEach var="g" items="${grades}">
            <c:if test="${g != sessionScope.employeFoundMatricule.grade}">
                <option value="${g}">${g}</option>
            </c:if>
        </c:forEach>

    </select><br>





    <input type="submit" value="Modifier">

</form>
</div>
    <form action="${pageContext.request.contextPath}/changerLeRole.jsp">
        <input type="submit" value="Changer le rôle de l'employé">
    </form>

    <div class="nextAction">
<form action="${pageContext.request.contextPath}/verificationDeLaSuppression.jsp" >
    <input type="submit" value="Supprimer l'employé">
</form>
        <form action="${pageContext.request.contextPath}/afficheEmploye.jsp">
            <input type="submit" value="Annuler">
        </form>
    </div>


<form>

</form>
</div>



</body>
</html>
