<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des Projets</title>
    <style>
        a {
            text-decoration: none;
            display: block;
            text-align: center;
            margin-top: 15px;
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<h2>Créer ou Modifier un Projet</h2>
<form action="ProjetController" method="post">
    Nom : <input type="text" name="nom" required><br>
    Description : <input type="text" name="description" required><br>
    État :
    <select name="etat">
        <option value="EN_COURS">EN_COURS</option>
        <option value="TERMINE">TERMINE</option>
        <option value="EN_ATTENTE">EN_ATTENTE</option>
    </select><br>
    <!--Departement: <input type="text" name="departement" required>-->
    <input type="submit" value="Enregistrer">
</form>

<hr>

<h2>Rechercher un Projet</h2>
<form action="ProjetController" method="get">
    <input type="hidden" name="action" value="rechercher">
    Nom : <input type="text" name="nom" required>
    <input type="submit" value="Rechercher">
</form>

<hr>

<a href="ProjetController">Afficher tous les projets</a>
</body>
</html>
