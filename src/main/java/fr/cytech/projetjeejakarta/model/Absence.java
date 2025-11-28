package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "employe_absence")
public class Absence {

    @EmbeddedId
    private AbsenceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_employe")
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;

    public Absence() {}

    public Absence(Employe employe, Date date) {
        this.employe = employe;
        this.id = new AbsenceId(employe.getId_employe(), date);

    }

    // Getters & Setters
    public AbsenceId getId() { return id; }
    public void setId(AbsenceId id) { this.id = id; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employee) { this.employe = employee; }


}
