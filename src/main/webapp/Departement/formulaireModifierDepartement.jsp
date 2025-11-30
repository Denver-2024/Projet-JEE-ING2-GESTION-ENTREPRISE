<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<html>
<head>
    <title>Gestion des départements</title>
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
        <h2><%= (request.getAttribute("departement") != null ? "Modifier un Département" : "Créer un Département") %></h2>

        <form action="../DepartementController" method="post">
            <%
                Departement d = (Departement) request.getAttribute("departement");
                boolean isModification = (d != null);
            %>
            <input type="hidden" name="action" value="<%= isModification ? "formulaireModifierDepartement" : "formulaireCreerDepartement" %>">
            <input type="hidden" name="id" value="<%= isModification ? d.getId_departement() : "" %>">

            <label for="nom">Nom :</label>
            <input type="text" name="nom" id="nom" value="<%= isModification ? d.getNom() : "" %>" required>

            <label for="description">Description :</label>
            <input type="text" name="description" id="description" value="<%= isModification ? d.getDescription() : "" %>" required>

            <label for="chefDepartement">Nom du chef de département :</label>
            <input type="text" name="chefDepartement" id="chefDepartement"
                   value="<%= (isModification && d.getChefDepartement() != null) ? d.getChefDepartement().getNom() : "" %>">

            <input type="submit" value="<%= isModification ? "Modifier" : "Créer" %>">
        </form>

        <hr>

        <h2>Rechercher un Département</h2>
        <form action="../DepartementController" method="get">
            <input type="hidden" name="action" value="rechercher">
            <label for="nomRecherche">Nom :</label>
            <input type="text" name="nom" id="nomRecherche" required>
            <input type="submit" value="Rechercher">
        </form>

        <hr>

        <a href="../DepartementController?action=liste">Afficher tous les départements</a>
    </div>
</div>
</body>
</html>
