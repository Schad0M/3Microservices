package ca.uqtr.dmi.appartlogic.dto;

import ca.uqtr.dmi.appartlogic.modele.Appartement;
import ca.uqtr.dmi.appartlogic.modele.AppartementImage;
import ca.uqtr.dmi.appartlogic.services.impl.ImagesAndRessourcesServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *Une DTO (Data Transfer Object) est un objet qui transporte des données entre les processus.
 * Dans le contexte de Spring, une DTO transporte des données entre le client et le serveur.
 * Elle est utilisée pour encapsuler les données et les envoyer via le réseau.
 */
@Data
@AllArgsConstructor
public class AppartementDTO {

    private static ImagesAndRessourcesServiceImpl mesImagesDappart;
    private AppartementDTO (ImagesAndRessourcesServiceImpl impl) {
    }

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
    private List<Resource> images;

    public static AppartementDTO from(Appartement appartement){
        List<Resource> images;
        try {
            images =mesImagesDappart.getAllFilesByAppartementId(appartement.getId());
        } catch (IOException e) {
            images = null;
        }

        return new AppartementDTO(
                appartement.getId(),
                appartement.getTitre(),
                appartement.getDescriptionCourte(),
                appartement.getDescriptionLongue(),
                appartement.getMontantMensualites(),
                appartement.getDateDisponibilite(),
                appartement.getAdresseAppart(),
                appartement.getNombrevisite(),
                appartement.isActif(),
                appartement.getOwnerID(),
                images
        );
    }

    public static Appartement toEntity(AppartementDTO appartementDTO) {

        Appartement appartement = new Appartement();
        appartement.setTitre(appartementDTO.getTitre());
        appartement.setDescriptionCourte(appartementDTO.getDescriptionCourte());
        appartement.setDescriptionLongue(appartementDTO.getDescriptionLongue());
        appartement.setMontantMensualites(appartementDTO.getMontantMensualites());
        appartement.setDateDisponibilite(appartementDTO.getDateDisponibilite());
        appartement.setAdresseAppart(appartementDTO.getAdresseAppart());
        appartement.setNombrevisite(appartementDTO.getNombrevisite());
        appartement.setActif(appartementDTO.isActif());
        appartement.setOwnerID(appartementDTO.getOwnerID());

        return appartement;
    }
}
