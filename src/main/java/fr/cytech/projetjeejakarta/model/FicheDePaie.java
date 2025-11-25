/*package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Fiche_de_Paie")
public class FicheDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_fiche_de_paie;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    private LocalDate date;

    private double salaire_base;

    @Column(name="prime")
    private double primes;

    private int nombre_absences;
    private double cotisation_salariale;
    private double cotisation_patronale;


    private double deductions;

    private double netAPayer;


    public int getId_ficheDePaie() {return id_fiche_de_paie;}
    public void setId_ficheDePaie(int id_ficheDePaie) {this.id_fiche_de_paie = id_ficheDePaie;}

    public Employe getEmploye() {return employe;}
    public void setEmploye(Employe employe) {this.employe = employe;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public double getSalaireBase() {return salaire_base;}
    public void setSalaireBase(double salaire_base) {this.salaire_base = salaire_base;}

    public double getPrimes() {return primes;}
    public void setPrimes(double primes) {this.primes = primes;}

    public double getNombreAbsences() {return nombre_absences;}
    public void setNombreAbsences(int nombre_absences) {this.nombre_absences = nombre_absences;}

    public double getCotisationSalariale() {return cotisation_salariale;}
    public void setCotisationSalariale(double cotisation_salariale) {this.cotisation_salariale = cotisation_salariale;}

    public double getCotisationPatronale() {return cotisation_patronale;}
    public void setCotisationPatronale(double cotisation_patronale) {this.cotisation_patronale = cotisation_patronale;}


    //A retravailler
    public double getDeductions() {return deductions;}
    public void setDeductions(double deductions) {this.deductions = deductions;}
}
*/