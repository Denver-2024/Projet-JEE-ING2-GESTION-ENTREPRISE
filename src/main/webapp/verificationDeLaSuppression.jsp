<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 22/11/2025
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Supprimer</title>
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

        .verifSuppression{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .nextAction{
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 40px
        }




        </style>
</head>
<body>
<div class="verifSuppression">
<form action="${pageContext.request.contextPath}/index.jsp">
    <input type="submit" value="Acceuil">
</form>
<h1>Êtes-vous sûr de vouloir supprimer l'employé ${sessionScope.employeFoundMatricule.id_employe}</h1><br>
    <div class="nextAction">
    <form action="${pageContext.request.contextPath}/supprimer-employe" method="post">

    <input type="submit" value="Supprimer">


</form>
    <form action="${pageContext.request.contextPath}/modifierEmploye.jsp">
        <input type="submit" value="Annuler">
    </form><br>
    </div>


<c:if test="${not empty messageEmployeDeleted}">
    <p style="color: green;">${messageEmployeDeleted}</p>
</c:if>
</div>
</body>
</html>
