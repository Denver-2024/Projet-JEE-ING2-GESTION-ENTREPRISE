package fr.cytech.projetjeejakarta.model;


import fr.cytech.projetjeejakarta.enumeration.*;
import jakarta.persistence.*;

@Entity
@Table(name="Employe")
public class Employe {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_employe;

    private String nom;
    private String prenom;
    private String adresse;
    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;
    private String numero;
    private String email;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
    private float salaire;

    @Column(name="mot_de_passe_hashe")
    private String password;

    public Employe() {

    }

    public Employe(String nom, String prenom,String adresse, Departement departement, String numero, String email, Sexe sexe, Grade grade, Role role ) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.departement = departement;
        this.numero = numero;
        this.email = email;
        this.sexe = sexe;
        this.grade = grade;
        this.role = role;

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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public float getSalaire() {return salaire;}
    public void setSalaire(float salaire) {this.salaire = salaire;}

    public String getPassword() {return password;}
    public void setPassword(String password) {}

    @Override
    public String toString() {
        return "Employe{" +
                "id_employe=" + id_employe +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", id_departement=" + departement +
                ", numero='" + numero + '\'' +
                ", email='" + email + '\'' +
                ", sexe=" + sexe +
                ", grade=" + grade +
                ", id_role=" + role +
                '}';
    }
}
