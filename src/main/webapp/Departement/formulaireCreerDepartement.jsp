<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer un Département</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Créer un Département</h2>

    <form action="../DepartementController" method="post">

        <input type="hidden" name="action" value="formulaireCreerDepartement">

        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" required>

        <label for="description">Description :</label>
        <input type="text" name="description" id="description" required>

        <label for="chefDepartement">Nom du chef de département :</label>
        <input type="text" name="chefDepartement" id="chefDepartement">

        <input type="submit" value="Créer">
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

    <a href="../DepartementController?action=liste">Retour à la liste des départements</a>
</div>
</body>
</html>
