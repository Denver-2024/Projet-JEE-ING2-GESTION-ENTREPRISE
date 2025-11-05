package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    private int id_role;
    private String nom;
    private String description;

    public int getId_role() {return id_role;}
    public void setId_role(int id_role) {this.id_role = id_role;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
