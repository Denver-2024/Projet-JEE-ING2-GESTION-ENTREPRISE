package fr.cytech.projetmodel.model;

import jakarta.persistence.*;

@Entity
@Table(name="Departement")
public class Departement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id_departement;

    private String nom;
    private String description;


    @OneToOne
    @JoinColumn(name = "id_employe")
    private Employe directeur;

    public Departement() {

    }
    public Departement(int id_departement, String nom, String description) {
        this.id_departement = id_departement;
        this.nom = nom;
        this.description = description;
    }


    public int getId_departement() {
        return id_departement;
    }

    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employe getDirecteur() {
        return directeur;
    }

    public void setDirecteur(Employe directeur) {
        this.directeur = directeur;
    }
}
