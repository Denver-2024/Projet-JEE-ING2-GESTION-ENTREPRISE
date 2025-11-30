package fr.cytech.projetjeejakarta.model;


import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.persistence.*;

@Embeddable
public class AbsenceId implements Serializable {


    private int id_employe;


    private Date date;

    public AbsenceId() {}

    public AbsenceId(int id_employe, Date date) {
        this.id_employe = id_employe;
        this.date = date;
    }

    // Getters & Setters
    public int getId_employe() { return id_employe; }
    public void setId_employe(int id_employe) { this.id_employe = id_employe; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    // Required for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbsenceId)) return false;
        AbsenceId that = (AbsenceId) o;
        return id_employe == (that.id_employe) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return id_employe + date.hashCode();
    }
}
