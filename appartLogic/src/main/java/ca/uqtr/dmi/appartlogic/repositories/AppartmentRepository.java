package ca.uqtr.dmi.appartlogic.repositories;


import ca.uqtr.dmi.appartlogic.modele.Appartement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cette interface herite de CrudRepository.
 * Elle definit les operations CRUD (Creer, Lire, Mettre a jour, Supprimer) sur les objets de type Appartement.
 * On spécifie que l'id des objets de type Appartement est de type Long.
 */
@Repository
public interface AppartmentRepository extends CrudRepository<Appartement, Long> {
    List<Appartement> findByOwnerID(int ownerID);
}
