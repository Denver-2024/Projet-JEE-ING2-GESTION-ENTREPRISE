<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Projets</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Créer ou Modifier un Projet</h2>
    <form action="../ProjetController" method="post">
        <input type="hidden" name="id" value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>">
        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required>

        <label for="description">Description :</label>
        <input type="text" name="description" id="description" value="<%= request.getParameter("description") != null ? request.getParameter("description") : "" %>" required>

        <label for="etat">État :</label>
        <select name="etat" id="etat">
            <option value="EN_COURS" <%= "EN_COURS".equals(request.getParameter("etat")) ? "selected" : "" %>>EN_COURS</option>
            <option value="TERMINE" <%= "TERMINE".equals(request.getParameter("etat")) ? "selected" : "" %>>TERMINE</option>
            <option value="ANNULE" <%= "ANNULE".equals(request.getParameter("etat")) ? "selected" : "" %>>ANNULE</option>
        </select>
        <label for="chefProjet">Nom du chef de projet :</label>
        <input type="text" name="chefProjet" id="chefProjet"
               value="<%= request.getParameter("chefProjet") != null ? request.getParameter("chefProjet") : "" %>" required>

        <label for="departement">Nom du département :</label>
        <input type="text" name="departement" id="departement"
               value="<%= request.getParameter("departement") != null ? request.getParameter("departement") : "" %>" required>

        <input type="submit" value="Enregistrer">
    </form>

    <hr>

    <h2>Rechercher un Projet</h2>
    <form action="../ProjetController" method="get">
        <input type="hidden" name="action" value="rechercher">
        <label for="nomRecherche">Nom :</label>
        <input type="text" name="nom" id="nomRecherche" required>
        <input type="submit" value="Rechercher">
    </form>

    <hr>

    <a href="../ProjetController?action=liste">Afficher tous les projets</a>
</div>
</body>
</html>
