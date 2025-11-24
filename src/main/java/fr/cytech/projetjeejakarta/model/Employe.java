package fr.cytech.projetjeejakarta.model;


import fr.cytech.projetjeejakarta.enumeration.*;
import jakarta.persistence.*;

@Entity
@Table(name="Employe")
public class Employe {

    @Id
    private int id_employe;

    private String nom;
    private String prenom;
    private String adresse;
    private int id_departement;
    private String numero;
    private String email;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private int id_role;
    private int salaire;
    private String password;

    public Employe() {

    }

    public Employe(String nom, String prenom,String adresse, int id_departement, String numero, String email, Sexe sexe, Grade grade, int id_role ) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.id_departement = id_departement;
        this.numero = numero;
        this.email = email;
        this.sexe = sexe;
        this.grade = grade;
        this.id_role = id_role;

    }

    public int getId_employe() {
        return id_employe;
    }

    public void setId_employe(int id_employe) {
        this.id_employe = id_employe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId_departement() {
        return id_departement;
    }

    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public int getSalaire() {return salaire;}
    public void setSalaire(int salaire) {this.salaire = salaire;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Override
    public String toString() {
        return "Employe{" +
                "id_employe=" + id_employe +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", id_departement=" + id_departement +
                ", numero='" + numero + '\'' +
                ", email='" + email + '\'' +
                ", sexe=" + sexe +
                ", grade=" + grade +
                ", id_role=" + id_role +
                '}';
    }
}
