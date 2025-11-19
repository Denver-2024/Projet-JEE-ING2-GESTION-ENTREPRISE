<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Projets</title>
    <link rel="stylesheet" type="text/css" href="CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Créer ou Modifier un Projet</h2>
    <form action="ProjetController" method="post">
        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" required>

        <label for="description">Description :</label>
        <input type="text" name="description" id="description" required>

        <label for="etat">État :</label>
        <select name="etat" id="etat">
            <option value="EN_COURS">EN_COURS</option>
            <option value="TERMINE">TERMINE</option>
            <option value="EN_ATTENTE">EN_ATTENTE</option>
        </select>

        <input type="submit" value="Enregistrer">
    </form>

    <hr>

    <h2>Rechercher un Projet</h2>
    <form action="ProjetController" method="get">
        <input type="hidden" name="action" value="rechercher">
        <label for="nomRecherche">Nom :</label>
        <input type="text" name="nom" id="nomRecherche" required>
        <input type="submit" value="Rechercher">
    </form>

    <hr>

    <a href="ProjetController">Afficher tous les projets</a>
</div>
</body>
</html>
