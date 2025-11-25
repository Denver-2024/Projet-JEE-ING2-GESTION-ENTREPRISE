<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Employe employe=(Employe) request.getAttribute("employe");
%>
<html>
<head>
    <title>Informations de l'employé</title>
    <style>
        body {
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
            font-family: 'Times New Roman', serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 50px;
        }
        form { margin-bottom: 20px; }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="submit"] {
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover { background-color: #333; }
        .affiche {
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="affiche">

    <!-- Bouton retour -->
    <form action="Employe/rechercheMatricule.jsp" method="get">
        <input type="submit" value="Retour à la page de recherche">
    </form>

    <!-- Affichage des infos employé -->
    <label>Matricule : <%=employe.getId_employe()%></label><br>
    <label>Nom : <%=employe.getNom()%></label><br>
    <label>Prénom : <%=employe.getPrenom()%></label><br>
    <label>Adresse : <%=employe.getAdresse()%></label><br>
    <label>Département : <%=employe.getDepartement().getNom()%></label><br>
    <label>Numéro de téléphone : <%=employe.getNumero()%></label><br>
    <label>Email : <%=employe.getEmail()%></label><br>
    <label>Sexe : <%=employe.getSexe()%></label><br>
    <label>Grade : <%=employe.getGrade()%></label><br>
    <label>Role : <%=employe.getRole().getNom()%></label><br><br>

    <!-- Formulaire modifier -->
    <form action="../EmployeController" method="post">
        <input type="hidden" name="action" value="modifier"/>
        <input type="hidden" name="id" value=<%=employe.getId_employe()%>/>
        <input type="submit" value="Modifier"/>
    </form>

    <!-- Formulaire changer rôle -->
    <form action="../EmployeController" method="post">
        <input type="hidden" name="action" value="changerRole"/>
        <input type="hidden" name="id" value=<%=employe.getId_employe()%>/>
        <input type="submit" value="Changer le rôle"/>
    </form>

    <!-- Formulaire supprimer -->
    <form action="../EmployeController" method="post">
        <input type="hidden" name="action" value="supprimer"/>
        <input type="hidden" name="id" value=<%=employe.getId_employe()%>/>
        <input type="submit" value="Supprimer"/>
    </form>

</div>
</body>
</html>
