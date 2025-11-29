package fr.cytech.projetjeejakarta.model;

import fr.cytech.projetjeejakarta.enumeration.EtatProjet;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Projet")
public class Projet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_projet")
    private int idProjet;

    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private EtatProjet etat; // EN_COURS, TERMINE, ANNULE

    @ManyToOne
    @JoinColumn(name = "id_chef_projet")
    private Employe chefDeProjet;

    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    @ManyToMany(mappedBy = "projets", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Employe> employes;


    public Projet() {}


    public int  getIdProjet() {return idProjet;}
    public void setIdProjet(int idProjet) {this.idProjet = idProjet;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public EtatProjet getEtat() {return etat;}
    public void setEtat(EtatProjet etat) {this.etat = etat;}

    public Employe getChefDeProjet() {return chefDeProjet;}
    public void setChefDeProjet(Employe chefDeProjet) {this.chefDeProjet = chefDeProjet;}

    public Departement getDepartement() {
        return departement;
    }
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Employe> getEmployes() {return employes;}
    public void setEmployes(List<Employe> employes) {this.employes = employes;}

    public void affecterEmploye(Employe employe){
        this.getEmployes().add(employe);
    }
    public void enleverEmploye(Employe employe){
        this.getEmployes().remove(employe);
    }

}




