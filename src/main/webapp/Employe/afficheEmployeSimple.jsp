<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Résultats de la recherche d'employés</title>
    <style>
        body {
            background: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca,
            #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff,
            #46eefa, #5ffbf1);
            background-attachment: fixed;
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
        .affiche {
            background-color: white;
            padding: 20px 30px;
            margin: 20px auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 70%;
        }
        h2 { text-align: center; }
        .detail { margin: 6px 0; }
        .detail span { font-weight: bold; }
    </style>
</head>
<body>
<header>
    <div style="display: flex; align-items: center; gap: 1rem;">
        <a href="${pageContext.request.contextPath}/dashboard"
           style="text-decoration: none; display: flex; align-items: center; gap: 1rem;">
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
    <c:forEach var="e" items="${employes}">
        <div class="affiche">
            <h2>Informations de l'employé</h2>
            <p class="detail"><span>Matricule :</span> ${e.id_employe}</p>
            <p class="detail"><span>Nom :</span> ${e.nom}</p>
            <p class="detail"><span>Prénom :</span> ${e.prenom}</p>
            <p class="detail"><span>Salaire :</span> ${e.salaire} €</p>
            <p class="detail"><span>Adresse :</span> ${e.adresse}</p>
            <p class="detail"><span>Département :</span> ${e.departement.nom}</p>
            <p class="detail"><span>Numéro de téléphone :</span> ${e.numero}</p>
            <p class="detail"><span>Email :</span> ${e.email}</p>
            <p class="detail"><span>Sexe :</span> ${e.sexe}</p>
            <p class="detail"><span>Grade :</span> ${e.grade}</p>
            <p class="detail"><span>Rôle :</span> ${e.role.nom}</p>
        </div>
    </c:forEach>
</div>
</body>
</html>
