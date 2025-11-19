package fr.cytech.projetjeejakarta.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import fr.cytech.projetjeejakarta.model.Autorisation;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    private int id_role;
    private String nom;
    private String description;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    @JoinTable(
            name = "Role_Autorisation",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_autorisation")
    )
    private HashSet<Autorisation> autorisations = new HashSet<>();

    public int getId_role() {return id_role;}
    public void setId_role(int id_role) {this.id_role = id_role;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public HashSet<Autorisation> getAutorisations() {return autorisations;}
    public void setAutorisations(HashSet<Autorisation> autorisations) {this.autorisations = autorisations;}
}
