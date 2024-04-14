package ca.uqtr.dmi.appartlogic.modele;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/***
 * Un modele represente la structure des données telles qu'elles sont stockees dans la base de donnees.
 * C'est une representation directe des tables de la base de donnees en code.
 * Le modele inclut non seulement les proprietes qui correspondent aux colonnes de la base de donnees,
 * mais aussi des annotations qui aident à gerer les relations entre les tables, les strategies de generation
 * d'identifiants, les validations, etc.
 */

@Entity // sert a specifier que cette classe est  une table dans la base de donnees
@Data//lombok, ici sert a generer les getters et setters
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Appartement_info") // sert a specifier le nom de la table dans la base de donnees
public class Appartement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titre;
    private String descriptionCourte;
    private String descriptionLongue;
    private double montantMensualites;
    private String dateDisponibilite;
    private String adresseAppart;
    private int nombrevisite;
    private boolean isActif;
    private int ownerID;
}