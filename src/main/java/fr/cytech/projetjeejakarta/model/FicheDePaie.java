/*package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Fiche_De_Paie")
public class FicheDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_fiche_de_paie;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    private LocalDate date;
    private double salaireBase;
    private double primes;
    private double absences;
    private double cotisations;
    private double deductions;

    private double netAPayer;


    public int getId_ficheDePaie() {return id_fiche_de_paie;}
    public void setId_ficheDePaie(int id_ficheDePaie) {this.id_fiche_de_paie = id_ficheDePaie;}

    public Employe getEmploye() {return employe;}
    public void setEmploye(Employe employe) {this.employe = employe;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public double getSalaireBase() {return salaireBase;}
    public void setSalaireBase(double salaireBase) {this.salaireBase = salaireBase;}

    public double getPrimes() {return primes;}
    public void setPrimes(double primes) {this.primes = primes;}

    public double getAbsences() {return absences;}
    public void setAbsences(double absences) {this.absences = absences;}

    public double getCotisations() {return cotisations;}
    public void setCotisations(double cotisations) {this.cotisations = cotisations;}

    public double getDeductions() {return deductions;}
    public void setDeductions(double deductions) {this.deductions = deductions;}
}
*/