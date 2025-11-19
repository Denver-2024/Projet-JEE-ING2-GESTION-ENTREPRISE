-- Departement

INSERT INTO Departement (nom, description) VALUES
   ('Informatique', 'Développement logiciel et infrastructure'),
   ('Ressources Humaines', 'Gestion administrative du personnel'),
   ('Marketing', 'Communication interne et externe'),
   ('Logistique', 'Flux, transport et stock'),
   ('Finance', 'Gestion comptable et financière'),
   ('Support Client', 'Service après-vente et relation client');



-- Employe
INSERT INTO Employe (nom, prenom, adresse, id_departement, numero, email, sexe, grade, id_role) VALUES
        ('Dupont','Jean','1 Rue de la bretonnière, Paris',1,'0600000001','jean.dupont@entreprise.com','M','senior',1),
        ('Martin','Sophie','15 Rue de l_hotel de ville, Pontoise',2,'0600000002','sophie.martin@entreprise.com','F','senior',2),

        ('Bernard','Lucas','33 boulevard du port, Cergy',1,'0600000003','lucas.bernard@entreprise.com','M','senior',3),
        ('Moreau','Julie','4 Rue de Londres, Paris',2,'0600000004','julie.moreau@entreprise.com','F','senior',3),
        ('Lemoine','Hugo','15 Rue de la Fayette, Paris',3,'0600000005','hugo.lemoine@entreprise.com','M','senior',3),
        ('Petit','Emma','6 Rue de Bruxelles, Cergy',4,'0600000006','emma.petit@entreprise.com','F','senior',3),
        ('Schneider','Paul','17 Rue de Bruxelles, Massy',5,'0600000007','paul.schneider@entreprise.com','M','senior',3),
        ('Rossi','Carla','8 Rue de Rouen, Creil',6,'0600000008','carla.rossi@entreprise.com','F','senior',3),

        ('Leroy','Paul','9 Place de la République, Juvisy',1,'0600000009','paul.leroy@entreprise.com','M','intermediaire',4),
        ('Garcia','Maria','40 Rue du Général de Gaulle , Osny',2,'0600000010','maria.garcia@entreprise.com','F','intermediaire',4),
        ('Nguyen','Minh','11 Rue de l_hotel de ville, Paris',3,'0600000011','minh.nguyen@entreprise.com','M','intermediaire',4),
        ('Diallo','Awa','12 Rue de Lyon, Paris',4,'0600000012','awa.diallo@entreprise.com','F','intermediaire',4),

        ('Morel','Thomas','13 Rue de Paris, Juvisy',1,'0600000013','thomas.morel@entreprise.com','X','junior',5),
        ('Fabre','Chloé','14 Rue du Général de Gaulle, Paris',2,'0600000014','chloe.fabre@entreprise.com','F','junior',5),
        ('Meyer','Louis','40 Rue du Général de Gaulle , Osny',3,'0600000015','louis.meyer@entreprise.com','M','junior',5),
        ('Perez','Sara','6 Rue de la Fayette, Eragny',4,'0600000016','sara.perez@entreprise.com','F','junior',5),
        ('Khan','Imran','17 Rue de Cergy, Neuville',5,'0600000017','imran.khan@entreprise.com','M','junior',5),
        ('Fischer','Anna','18 Rue de Lyon, Ermont',6,'0600000018','anna.fischer@entreprise.com','X','junior',5),
        ('Hussein','Youssef','9 Rue de Rouen, Saint-Denis',1,'0600000019','youssef.hussein@entreprise.com','M','junior',5),
        ('Barbier','Lucie','2 Rue de Rouen, Ermont',3,'0600000020','lucie.barbier@entreprise.com','F','junior',5);


-- Définition chef de département
UPDATE Departement SET id_chef_departement = 3 WHERE id_departement = 1;
UPDATE Departement SET id_chef_departement = 4 WHERE id_departement = 2;
UPDATE Departement SET id_chef_departement = 5 WHERE id_departement = 3;
UPDATE Departement SET id_chef_departement = 6 WHERE id_departement = 4;
UPDATE Departement SET id_chef_departement = 7 WHERE id_departement = 5;
UPDATE Departement SET id_chef_departement = 8 WHERE id_departement = 6;


-- Projet
INSERT INTO Projet (nom, description, etat, id_chef_projet, id_departement) VALUES
    ('ERP Migration', 'Migration du système ERP', 'EN_COURS', 9, 1),
    ('Refonte Site Web', 'Nouvelle version du site corporate', 'EN_COURS', 9, 1),
    ('Campagne Pub TV', 'Nouvelle publicité nationale', 'TERMINE', 11, 3),
    ('Étude Marché 2025', 'Analyse des tendances', 'EN_COURS', 11, 3),
    ('Optimisation Stock', 'Automatisation stock', 'ANNULE', 12, 4),
    ('Transport Durable', 'Projet éco-logistique', 'EN_COURS', 12, 4),
    ('Paie+ 2025', 'Refonte du logiciel paie', 'EN_COURS', 10, 2),
    ('Onboarding RH', 'Digitalisation onboarding', 'TERMINE', 10, 2),
    ('Audit Financier', 'Audit externe annuel', 'EN_COURS', 13, 5),
    ('Prévisions Budgétaires', 'Budget prévisionnel 2026', 'TERMINE', 13, 5),
    ('Assistance Premium', 'Nouveau service support', 'EN_COURS', 14, 6),
    ('Support Multilingue', 'Extension langues support', 'EN_COURS', 14, 6);


-- Employes affectés à des projets

INSERT INTO Employe_Projet (id_employe, id_projet) VALUES
   (3,1),(9,1),(13,1),(19,1),
   (3,2),(9,2),(14,2),(20,2),
   (5,3),(11,3),(15,3),
   (5,4),(11,4),(16,4),
   (6,5),(12,5),(17,5),
   (6,6),(12,6),(18,6),
   (4,7),(10,7),(15,7),
   (4,8),(10,8),(14,8),
   (7,9),(13,9),(17,9),
   (7,10),(13,10),(18,10),
   (8,11),(14,11),(20,11),
   (8,12),(14,12),(19,12);


-- fiche de paie 2 mois pour quelques employes
INSERT INTO Fiche_de_paie (id_employe, date_fiche, salaire_base, cotisation_salariale, cotisation_patronale, prime, nombre_absences) VALUES
     (1,'2025-01-31',4500,620,810,500,0),
     (1,'2025-02-28',4500,620,810,300,1),

     (3,'2025-01-31',5200,700,900,800,0),
     (3,'2025-02-28',5200,700,900,600,0),

     (9,'2025-01-31',3800,540,700,200,0),
     (9,'2025-02-28',3800,540,700,150,2),

     (13,'2025-01-31',2800,390,500,100,0),
     (14,'2025-01-31',2700,380,490,80,0),

     (4,'2025-01-31',4800,660,850,600,1),
     (10,'2025-01-31',4000,560,720,200,0);

