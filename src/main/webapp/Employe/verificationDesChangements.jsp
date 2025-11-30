<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 22/11/2025
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Vérifier les changements</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
        .verifModif {
            background-color: white; padding: 25px 40px;
            border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .nextAction {
            display: flex; justify-content: space-evenly; align-items: center;
        }
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
    <div class="verifModif">
        <ul>
            <c:forEach var="change" items="${changes}">
                <li>${change}</li>
            </c:forEach>
        </ul>
        <div class="nextAction">
            <!-- Formulaire avec chemin dynamique vers le servlet -->
            <form action="${pageContext.request.contextPath}/ModifierEmployeController" method="post">
                <input type="submit" value="Valider les changements">
            </form>

            <!-- Formulaire annulation avec chemin dynamique -->
            <form action="${pageContext.request.contextPath}/Employe/modifierEmploye.jsp">
                <input type="submit" value="Annuler">
            </form>
        </div>
    </div>
</div>
</body>
</html>
