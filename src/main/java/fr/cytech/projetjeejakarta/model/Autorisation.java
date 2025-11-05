package fr.cytech.projetjeejakarta.model;

public class Autorisation {
    private int id_autorisation;
    private String nom;
    private String description;

    public int getId_autorisation() {return id_autorisation;}
    public void setId_autorisation(int id_autorisation) {this.id_autorisation = id_autorisation;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
