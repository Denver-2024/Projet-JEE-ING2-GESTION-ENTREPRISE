<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Projets</title>

    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
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
</body>
</html>
