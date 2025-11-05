package fr.cytech.projetjeejakarta.model;

import fr.cytech.projetjeejakarta.enumeration.EtatProjet;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_projet;

    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatProjet etat; // EN_COURS, TERMINE, ANNULE

    private String chefDeProjet;
    private String departement;



    public int  getId_projet() {return id_projet;}
    public void setId_projet(int id_projet) {this.id_projet = id_projet;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public EtatProjet getEtat() {return etat;}
    public void setEtat(EtatProjet etat) {this.etat = etat;}

    public String getChefDeProjet() {return chefDeProjet;}
    public void setChefDeProjet(String chefDeProjet) {this.chefDeProjet = chefDeProjet;}
}
