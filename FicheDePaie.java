package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "Fiche_de_Paie")
public class FicheDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_fiche_de_paie;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    @Column(name="date_fiche")
    private Date dateFiche;

    private int salaire_base;

    @Column(name = "prime")
    private double prime;

    private int nombre_absences;
    private double cotisation_salariale;
    private double cotisation_patronale;


    public int getId_fiche_de_paie() {
        return id_fiche_de_paie;
    }

    public void setId_fiche_de_paie(int id_fiche_de_paie) {
        this.id_fiche_de_paie = id_fiche_de_paie;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDateFiche() {
        return dateFiche;
    }

    public void setDateFiche(Date date_fiche) {
        this.dateFiche = date_fiche;
    }

    public int getSalaire_base() {
        return salaire_base;
    }

    public void setSalaire_base(int salaire_base) {
        this.salaire_base = salaire_base;
    }

    public double getPrime() {
        return prime;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }

    public int getNombre_absences() {
        return nombre_absences;
    }

    public void setNombre_absences(int nombre_absences) {
        this.nombre_absences = nombre_absences;
    }

    public double getCotisation_salariale() {
        return cotisation_salariale;
    }

    public void setCotisation_salariale(double cotisation_salariale) {
        this.cotisation_salariale = cotisation_salariale;
    }

    public double getCotisation_patronale() {
        return cotisation_patronale;
    }

    public void setCotisation_patronale(double cotisation_patronale) {
        this.cotisation_patronale = cotisation_patronale;
    }

    @Override
    public String toString() {
        return "FicheDePaie{" +
                "id_fiche_de_paie=" + id_fiche_de_paie +
                ", employe=" + employe.getId_employe() +
                ", date_fiche=" + dateFiche +
                ", salaire_base=" + salaire_base +
                ", prime=" + prime +
                ", nombre_absences=" + nombre_absences +
                ", cotisation_salariale=" + cotisation_salariale +
                ", cotisation_patronale=" + cotisation_patronale +
                '}';
    }
}






