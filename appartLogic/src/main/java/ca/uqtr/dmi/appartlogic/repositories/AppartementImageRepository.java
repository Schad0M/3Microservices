package ca.uqtr.dmi.appartlogic.repositories;

import ca.uqtr.dmi.appartlogic.modele.AppartementImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppartementImageRepository extends CrudRepository<AppartementImage, Long> {
    Optional<List<AppartementImage>> getAllAppartementImagesByAppartementId(Long appartementId);
}
