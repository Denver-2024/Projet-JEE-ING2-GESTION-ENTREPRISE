<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Departement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Ajouter un employé</title>
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


        #addform{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .radchoice {
            display: flex;
            gap: 20px; /* control spacing between choices */
            margin-bottom: 5px;
        }

        .radchoice span {
            display: flex;
            align-items: center;
        }

        .radchoice span label {
            margin-left: 4px;  /* tight label spacing */
        }

        label{
            margin: 5px;
        }
    </style>

</head>
<body>
<div id="addform">
    <c:if test="${not empty errorMessageInputNotFilled}">
        <p style="color:red; font-weight:bold;">
                ${errorMessageInputNotFilled}
        </p>
    </c:if>
    <c:if test="${not empty messageAjoutSucces}">
        <p style="color: green;">${messageAjoutSucces}</p>
    </c:if>
    <c:if test="${not empty errorSalaireTooLow}">
        <p style="color: red;">${errorSalaireTooLow}</p>
    </c:if>
    <c:if test="${not empty errorSalaireNotNumber}">
        <p style="color: red;">${errorSalaireNotNumber}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/../EmployeController/AddEmployeController" method="post">
        <div class="title">
            <h1>Ajouter un nouvel employé</h1>
        </div>
        <div class="radchoice">
            <label>Civilité :</label>
            <span><input type="radio" id="M" name="sexe" value="M"><label for="M">M</label></span>
            <span><input type="radio" id="F" name="sexe" value="F"><label for="F">F</label></span>
            <span><input type="radio" id="X" name="sexe" value="X"><label for="X">X</label></span>
        </div>



        <label for="nom">Nom : </label>
        <input type="text" name="nom" required>

        <label for="prenom">Prénom : </label>
        <input type="text" name="prenom" required>

        <label for="salaire">Salaire : </label>
        <input type="text" name="salaire" required>

        <label> Adresse : </label><br>
        <input type="text" name="adresse" required><br>

        <label> Numéro de téléphone : </label><br>
        <input type="text" name="numero" required><br>

        <label> Email : </label><br>
        <input type="text" name="email" required><br>


        <div class="radchoice">
            <label for="grade" >Grade :</label><br>
            <span><input type="radio" id="junior" name="grade" value="JUNIOR" required><label for="junior">Junior</label><br></span>
            <span><input type="radio" id="intermediaire" name="grade" value="INTERMEDIAIRE"><label for="intermediaire">Intermédiare</label></span>
            <span><input type="radio" id="senior" name="grade" value="SENIOR"><label for="senior">Senior</label></span>
        </div>



        <label>Département : </label>
        <select name="id_departement" required>
            <option value="" disabled selected hidden>-- Sélectionner un département --</option>
            <c:forEach var="d" items="${applicationScope.departementsFound}">
                <option value="${d.id_departement}">${d.nom}</option>
            </c:forEach>
        </select>

        <label>Rôle : </label>

        <select name="role" required>
            <option value="" disabled selected hidden>-- Sélectionner un rôle --</option>
            <c:forEach var="r" items="${applicationScope.rolesFound}">
                <option value="${r.id_role}">${r.nom}</option>
            </c:forEach>
        </select><br><br>

        <input value="Ajouter Employe" type="submit">
    </form><br>


</div>
</body>
</html>
