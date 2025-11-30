<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Absence</title>
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

        form { margin-bottom: 30px; }
        label { display: block; text-align: left; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], select {
            width: 100%; padding: 8px; margin-bottom: 15px;
            border: 1px solid #ccc; border-radius: 5px;
        }
        input[type="submit"] {
            background-color: black; border: none; color: white;
            padding: 10px; font-size: 16px; width: 100%;
            cursor: pointer; border-radius: 5px;
        }
        input[type="submit"]:hover { background-color: #333; }
        .ajoutabsence {
            background-color: white; padding: 25px 40px;
            border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title { display: flex; justify-content: center; align-items: center; }
        .dateabsence { display: flex; justify-content: center; align-items: center; }
    </style>
</head>
<body>
<div class="ajoutabsence">
    <div class="title">
        <h1>Absence</h1>
    </div>

    <c:if test="${not empty errorMessageAbsenceDate}">
        <p style="color: red;">${errorMessageAbsenceDate}</p>
    </c:if>
    <c:if test="${not empty errorDatesAbsence}">
        <p style="color: red;">${errorDatesAbsence}</p>
    </c:if>
    <c:if test="${not empty messageAbsenceAjoutee}">
        <p style="color: green;">${messageAbsenceAjoutee}</p>
    </c:if>

    <div class="dateabsence">
        <!-- Formulaire avec chemin dynamique vers le servlet -->
        <form action="${pageContext.request.contextPath}/AjouterAbsenceController" method="post">
            <label for="startDate">Date du début de l'absence : </label><br>
            <input type="date" id="startDate" name="startDate"
                   min="${sessionScope.firstDayOfMonth}"
                   max="${sessionScope.lastDayOfYear}" required><br><br>

            <label for="endDate">Date de la fin de l'absence : </label><br>
            <input type="date" id="endDate" name="endDate"
                   min="${sessionScope.firstDayOfMonth}"
                   max="${sessionScope.lastDayOfYear}" required><br><br>

            <input type="submit" value="Ajouter l'absence">
        </form>
    </div>
</div>
</body>
</html>
