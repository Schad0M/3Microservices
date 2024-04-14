package ca.uqtr.dmi.appartlogic.modele;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appImages_info")
public class AppartementImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//Id de l'image

    @Column(name = "image_url")
    private String imageUrl;//adresse de l'image dans le serveur

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Appartement.class)
    private Set<Appartement> appartement = new HashSet<>();
}
