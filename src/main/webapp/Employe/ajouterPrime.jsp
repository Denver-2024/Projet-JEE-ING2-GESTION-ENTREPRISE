<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 25/11/2025
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Prime</title>
    <style>
        body{
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;

            font-family: 'Times New Roman', serif;
        }

        header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 10px 10px 0 0;
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
        .ajoutprime {
            background-color: white; padding: 25px 40px;
            border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title { display: flex; justify-content: center; align-items: center; }
    </style>
</head>
<body>
<header>
    <div style="display: flex; align-items: center; gap: 1rem;">
        <a href="${pageContext.request.contextPath}/dashboard" style="text-decoration: none; display: flex; align-items: center; gap: 1rem;">
            <img src="${pageContext.request.contextPath}/images/Logo.png"
                 alt="Logo Gestion Entreprise"
                 style="height: 60px; width: auto;">
            <h1 style="color: white; margin: 0;">Tableau de Bord - Gestion Entreprise</h1>
        </a>
    </div>
    <div>
        <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
        <span style="margin-left: 1rem;">Rôle: ${sessionScope.role}</span>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
    </div>
</header>
<div class="page">
    <div class="ajoutprime">
        <div class="title">
            <h1>Prime</h1>
        </div>

        <c:if test="${not empty errorPrime}">
            <p style="color:red; font-weight:bold;">${errorPrime}</p>
        </c:if>
        <c:if test="${not empty errorPrimeTrop}">
            <p style="color:red; font-weight:bold;">${errorPrimeTrop}</p>
        </c:if>
        <c:if test="${not empty successPrime}">
            <p style="color:green; font-weight:bold;">${successPrime}</p>
        </c:if>

        <!-- Formulaire avec chemin dynamique vers le servlet -->
        <form action="${pageContext.request.contextPath}/AjouterPrimeController" method="post">
            <label for="mois">Mois : </label><br>
            <select name="mois">
                <option value="${sessionScope.months.get(0)}">${sessionScope.months.get(0)}</option>
                <c:forEach var="m" items="${sessionScope.months}" varStatus="status">
                    <c:if test="${status.index != 0}">
                        <option value="${m}">${m}</option>
                    </c:if>
                </c:forEach>
            </select>

            <label for="prime">Montant (en €): </label>
            <input type="text" name="prime" id="prime">

            <input type="submit" value="Ajouter la prime">
        </form>
    </div>
</div>
</body>
</html>
