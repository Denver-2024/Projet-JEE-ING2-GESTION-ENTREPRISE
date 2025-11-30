# Projet-JEE-ING2-GESTION-ENTREPRISE

![Projet de Gestion d'Entreprise](src/main/webapp/images/Logo.png)


## Table des matières
1. [Qu'est-ce que Gestion Entreprise ?](#quest-ce-que-gestion-entreprise)
2. [Prérequis techniques pour l'installation](#prerequis-techniques-pour-linstallation)
3. [Installation](#Installation)


## Qu'est-ce que Gestion Entreprise ?
**Gestion Entreprise** est un outil développé en JAVA qui offre les fonctionnalités principales de la gestion d'une entrerise :
- Sa structure (départements)
- Ses projets
- La base de ses employés et leurs affectations sur les projets et dans les départements de l'entreprise
- Le calcul et la génération des fiches de paies (téléchargeables en pdf).

**Gestion Entreprise** est un outil sécurisé, reposant sur une séparation des fonctions accessibles à des profils attribués en fonction des rôles.
Ainsi, cinq rôles sont définis :
- employé simple
- chef de projet
- chef de département
- administrateur RH
- et administrateur

En outre, les accès sont sécurisés par mots de passes, chiffrés et stockés en base de données en BCrypt.

## Prérequis techniques pour l'installation 
**Gestion Entreprise** est une application développée en Java  avec JEE. Pour l'installer, vous aurez besoin :
- des JDK
- de Maven pour le build
- d'un serveur d'application tel que Tomcat
- d'une base de données sous MySQL

## Installation
Les prérequis techniques sont supposés installés et fonctionnels.
- Commencer par récupérer un zip de l'application en cliquant sur le bouton vert "code" sur la page d'accueil, dernière option pour récupérer le zip.
- Sur votre PC, décomprésser l'application, vous récupérez sa structure arborescente.
- Utilisez Maven pour le compiler : Ouvrez un terminal, placez-vous à la racine du dossier et saisisez la commande  `mvn clean install`.
  Ceci compile et package l'application en un .WAR, et en même temps télécharge les dépendances automatiquement lorsqu'il y en a.
- Le WAR est créé dans le dossier target de l'application. Il faut désormaer le déployer au sein du serveur d'application (Tomcat) : copiez ce fichier dans le dossier par défaut, qui est en général "webapps" (ce dossier est spécifié dans la variable de configuration `$CATALINA_HOME`.
- Il reste ensuite à préparer la base de données : connctez-vous à votre serveur SQL, et créez une base de données que vous appelez "entreprise"
- dans cette base vide, exécutez dan l'ordre les 3 scripts SQL fournis à la racine de la structure arborescente :
    - script_sql_entreprise.sql
    - donnees_statiques.sql
    - jeu_donnees.sql

L'application est configurée pour se connecter à cette base de données en utilisant l'utilisateur root avec comme mot de passe "cytech0001". Vous aurez besoin, pour que la connexion fonctionne : soit de changer le mot de passe de l'utilisateur root dans MySQL, soit de modifier le mot de passe utilisé par l'application dans le fichier suivant :
/src/main/resources/persistence.xml, en adaptant la ligne :`<property name="jakarta.persistence.jdbc.password" value="cytech0001"/>`.
- Enfin, lancez Tomcat (en exécutant  startup.sh ou  startup.bat selon votre système d'exploitation), de façon à ce que Tomcat détecte automatiquement le WAR.
- Pour accéder à l'application, ouvrez votre navigateur et saisissez l'adresse : **http://localhost:8080/auth**, vous êtes rendu sur la page d'accueil de l'application.
- Connectez-vous avec un utilisateur par défaut : identifiant employé : "1", mot de passe : "JEE_Killers".
  
