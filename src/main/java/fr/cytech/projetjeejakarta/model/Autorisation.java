package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Autorisation")
public class Autorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // cohérent avec AUTO_INCREMENT
    @Column(name = "id_autorisation")
    private Integer id_autorisation;

    @Column(nullable = false)
    private String nom;

    private String description;

    @ManyToMany(mappedBy = "autorisations") // relation inverse vers Role
    private Set<Role> roles;

    // --- Getters / Setters ---
    public Integer getId_autorisation() { return id_autorisation; }
    public void setId_autorisation(Integer id_autorisation) { this.id_autorisation = id_autorisation; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
