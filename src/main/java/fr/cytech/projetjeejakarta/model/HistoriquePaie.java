package fr.cytech.projetjeejakarta.model;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "historique_paie")
public class HistoriquePaie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_execution", nullable = false)
    private Date dateExecution;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // SUCCESS or FAILED

    @Column(name = "message")
    private String message; // optional

    // --------------------------
    // Constructors
    // --------------------------

    public HistoriquePaie() {
    }

    public HistoriquePaie(Date dateExecution, String status, String message) {
        this.dateExecution = dateExecution;
        this.status = status;
        this.message = message;
    }

    // --------------------------
    // Getters and setters
    // --------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}