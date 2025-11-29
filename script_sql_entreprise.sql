SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;

-- Suppression des tables si déjà existantes

DROP TABLE IF EXISTS Role_Autorisation;
DROP TABLE IF EXISTS Autorisation;
DROP TABLE IF EXISTS Employe_Projet;
DROP TABLE IF EXISTS Projet;
DROP TABLE IF EXISTS Fiche_de_paie;
DROP TABLE IF EXISTS Employe_Absence;
DROP TABLE IF EXISTS Historique_Paie;
DROP TABLE IF EXISTS Employe;
DROP TABLE IF EXISTS Departement;
DROP TABLE IF EXISTS Role;


-- TABLE ROLE
CREATE TABLE Role (
  id_role INT AUTO_INCREMENT PRIMARY KEY,
  nom ENUM('employe','chef_de_projet','chef_de_departement','administrateur_rh','administrateur') NOT NULL DEFAULT 'employe',
  description TEXT
);


-- TABLE AUTORISATION
CREATE TABLE Autorisation (
  id_autorisation INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(255) NOT NULL,
  description TEXT
);


-- TABLE ROLE_AUTORISATION
CREATE TABLE Role_Autorisation(
  id_role INT NOT NULL,
  id_autorisation INT NOT NULL,
  PRIMARY KEY(id_role, id_autorisation),

  FOREIGN KEY (id_role) REFERENCES Role(id_role)
      ON DELETE CASCADE ON UPDATE CASCADE,

  FOREIGN KEY (id_autorisation) REFERENCES Autorisation(id_autorisation)
      ON DELETE CASCADE ON UPDATE CASCADE
);


-- TABLE DEPARTEMENT
CREATE TABLE Departement (
     id_departement INT AUTO_INCREMENT PRIMARY KEY,
     nom VARCHAR(255) NOT NULL,
     description TEXT,
     id_chef_departement INT DEFAULT NULL
);


-- TABLE EMPLOYE
CREATE TABLE Employe (
     id_employe INT AUTO_INCREMENT PRIMARY KEY,
     nom VARCHAR(255) NOT NULL,
     prenom VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL DEFAULT 'JEE_Killers',
     adresse TEXT NOT NULL,

     id_departement INT NOT NULL,
     numero VARCHAR(20) NOT NULL,
     email VARCHAR(255) NOT NULL UNIQUE,

     sexe ENUM('M','F','X') NOT NULL DEFAULT 'X',
     grade ENUM('JUNIOR', 'INTERMEDIAIRE', 'SENIOR') NOT NULL DEFAULT 'JUNIOR',

     id_role INT NOT NULL,

     FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
         ON DELETE CASCADE ON UPDATE CASCADE,

     FOREIGN KEY (id_role) REFERENCES Role(id_role)
         ON DELETE CASCADE ON UPDATE CASCADE
);

-- Ajout de la clé étrangère chef de département après création Employe
ALTER TABLE Departement
    ADD CONSTRAINT fk_chef_dep
    FOREIGN KEY (id_chef_departement) REFERENCES Employe(id_employe)
    ON DELETE SET NULL ON UPDATE CASCADE;


-- TABLE PROJET
CREATE TABLE Projet (
    id_projet INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    etat ENUM('EN_COURS', 'TERMINE', 'ANNULE') NOT NULL DEFAULT 'EN_COURS',

    id_chef_projet INT NOT NULL,
    id_departement INT NOT NULL,

    FOREIGN KEY (id_chef_projet) REFERENCES Employe(id_employe)
        ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (id_departement) REFERENCES Departement(id_departement)
        ON DELETE CASCADE ON UPDATE CASCADE
);


-- TABLE EMPLOYE_PROJET
CREATE TABLE Employe_Projet (
    id_employe INT NOT NULL,
    id_projet INT NOT NULL,

    PRIMARY KEY(id_employe, id_projet),

    FOREIGN KEY (id_employe) REFERENCES Employe(id_employe)
        ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (id_projet) REFERENCES Projet(id_projet)
        ON DELETE CASCADE ON UPDATE CASCADE
);


-- TABLE FICHE DE PAIE
CREATE TABLE Fiche_de_paie (
    id_fiche_de_paie INT AUTO_INCREMENT PRIMARY KEY,
    id_employe INT NOT NULL,
    date_fiche DATE NOT NULL,
    salaire_base DECIMAL(11,2) NOT NULL,
    cotisation_salariale DECIMAL(11,2) NOT NULL,
    cotisation_patronale DECIMAL(11,2) NOT NULL,
    prime DECIMAL(11,2) NOT NULL DEFAULT 0,
    nombre_absences INT DEFAULT 0,
    FOREIGN KEY (id_employe) REFERENCES Employe(id_employe) ON DELETE CASCADE ON UPDATE CASCADE );


CREATE TABLE Employe_Absence (
     id_employe INT NOT NULL,
     date DATE,
     PRIMARY KEY (id_employe , date),
     FOREIGN KEY (id_employe) REFERENCES Employe(id_employe) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE Historique_Paie (
     id INT AUTO_INCREMENT PRIMARY KEY,
     date_execution DATE NOT NULL,
     status VARCHAR(20) NOT NULL,  -- SUCCESS or FAILED
     message VARCHAR(255)          -- optional: error message or notes
);

    COMMIT;
