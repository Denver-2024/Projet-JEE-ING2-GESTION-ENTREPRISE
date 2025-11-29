<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<html>
<head>
    <title>Gestion des départements</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
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
</body>
</html>
