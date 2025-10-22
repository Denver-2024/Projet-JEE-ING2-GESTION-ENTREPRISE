package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatProjet etat; // EN_COURS, TERMINE, ANNULE

    private String chefDeProjet;
    private String departement;


    public Projet() {}

    public Projet(String nom, String description, EtatProjet etat) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
    }

    // Getters et Setters
    // ...
}
