package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;

import java.util.Set;
import fr.cytech.projetjeejakarta.model.Autorisation;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_role;

    @Column(nullable = false)
    private String nom;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_autorisation",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_autorisation")
    )
    private Set<Autorisation> autorisations;

    // --- Getters / Setters ---
    public Integer getId_role() { return id_role; }
    public void setId_role(Integer id_role) { this.id_role = id_role; }

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Set<Autorisation> getAutorisations() { return autorisations; }
    public void setAutorisations(Set<Autorisation> autorisations) { this.autorisations = autorisations; }
}
