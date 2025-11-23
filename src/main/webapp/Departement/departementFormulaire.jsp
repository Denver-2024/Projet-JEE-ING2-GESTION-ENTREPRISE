<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des départements</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Créer ou Modifier un Département</h2>
    <form action="DepartementController" method="post">
        <input type="hidden" name="id" value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>">

        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required>

        <label for="description">Description :</label>
        <input type="text" name="description" id="description" value="<%= request.getParameter("description") != null ? request.getParameter("description") : "" %>" required>

        <input type="submit" value="Enregistrer">
    </form>

    <hr>

    <h2>Rechercher un Département</h2>
    <form action="DepartementController" method="get">
        <input type="hidden" name="action" value="rechercher">
        <label for="nomRecherche">Nom :</label>
        <input type="text" name="nom" id="nomRecherche" required>
        <input type="submit" value="Rechercher">
    </form>

    <hr>

    <a href="DepartementController?action=liste">Afficher tous les départements</a>
</div>
</body>
</html>
