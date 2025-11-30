<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Projets</title>
    <style>
        header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 10px 10px 0 0;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
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
    <div class="container">
        <h2>Créer un nouveau projet</h2>
        <%
            String messageErreur = (String) request.getAttribute("messageErreur");
            String messageSucces = (String) request.getAttribute("messageSucces");
            if (messageErreur != null) {
        %>
        <p class="messageErreur"><%= messageErreur %></p>
        <% } else if (messageSucces != null) { %>
        <p class="messageSucces"><%= messageSucces %></p>
        <% } %>


        <form action="../ProjetController" method="post">
            <input type="hidden" name="action" value="formulaireCreerProjet">

            <label for="nom">Nom :</label>
            <input type="text" name="nom" id="nom" required>

            <label for="description">Description :</label>
            <input type="text" name="description" id="description" required>

            <label for="chefProjet">Nom du chef de projet :</label>
            <input type="text" name="chefProjet" id="chefProjet" required>

            <label for="departement">Nom du département :</label>
            <input type="text" name="departement" id="departement" required>

            <input type="submit" value="Enregistrer">
        </form>

        <hr>

        <a href="../ProjetController?action=liste">Afficher tous les projets</a>
    </div>
</div>
</body>
</html>
