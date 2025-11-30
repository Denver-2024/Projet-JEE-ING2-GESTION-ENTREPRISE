<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Informations de l'employé</title>
    <style>

        body{
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

        form {
            margin-bottom: 30px;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
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
        input[type="submit"]:hover {
            background-color: #333;
        }
        .affiche{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="affiche">
    <!-- Retour à la page de recherche -->
    <form action="${pageContext.request.contextPath}/Employe/rechercheMatricule.jsp">
        <input type="submit" value="Retour à la page de recherche">
    </form>

    <!-- Formulaire de modification -->
    <form action="${pageContext.request.contextPath}/Employe/modifierEmploye.jsp" method="get">
        <label>Matricule : ${sessionScope.employeFoundMatricule.id_employe}</label><br>
        <label>Nom : ${sessionScope.employeFoundMatricule.nom}</label><br>
        <label>Prénom : ${sessionScope.employeFoundMatricule.prenom}</label><br>
        <label>Salaire (en €) : ${sessionScope.employeFoundMatricule.salaire} €</label><br>
        <label>Adresse : ${sessionScope.employeFoundMatricule.adresse}</label><br>
        <c:forEach var="d" items="${applicationScope.departementsFound}">
            <c:if test="${d.id_departement == sessionScope.employeFoundMatricule.id_departement}">
                <label>Département : ${d.nom}</label><br>
            </c:if>
        </c:forEach>
        <label>Numéro de téléphone : ${sessionScope.employeFoundMatricule.numero}</label><br>
        <label>Email : ${sessionScope.employeFoundMatricule.email}</label><br>
        <label>Sexe : ${sessionScope.employeFoundMatricule.sexe}</label><br>
        <label>Grade : ${sessionScope.employeFoundMatricule.grade}</label><br>
        <label>Role : ${sessionScope.employeFoundMatricule.role.nom}</label><br><br>

        <input type="submit" value="Modifier">
    </form>

    <!-- Voir les fiches de paie -->
    <form action="${pageContext.request.contextPath}/Employe/rechercherFichesDePaie.jsp" method="get">
        <input type="submit" value="Voir les fiches de paie">
    </form>

</div>
</body>
</html>
