package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="Departement")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_departement;

    private String nom;
    private String description;


    @OneToOne
    @JoinColumn(name = "id_chef_departement")
    private Employe chefDepartement;

    //Relation inverse: liste tous les employés dans un département donné
    @OneToMany(mappedBy = "departement")
    private List<Employe> employes;

    @OneToMany(mappedBy = "departement")
    private List<Projet> projets;

    public Departement() {}

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

    public Employe getChefDepartement() {
        return chefDepartement;
    }

    public void setChefDepartement(Employe chefDepartement) {
        this.chefDepartement = chefDepartement;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

}
