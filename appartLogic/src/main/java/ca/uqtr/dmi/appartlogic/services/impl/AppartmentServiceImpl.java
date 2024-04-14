package ca.uqtr.dmi.appartlogic.services.impl;



import ca.uqtr.dmi.appartlogic.modele.Appartement;
import ca.uqtr.dmi.appartlogic.repositories.AppartmentRepository;
import ca.uqtr.dmi.appartlogic.services.AppartmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * Cette classe implemente les methodes definies dans l'interface AppatmentServices.
 * Elle est responsable de la gestion des donnees relatives aux appartements.
 * Elle fait de l'injection de dependance avec la classe AppartmentRepository pour acceder a la base de donnees.
 */
@Slf4j
@Service
public class AppartmentServiceImpl implements AppartmentService {
    private final AppartmentRepository appartRepo;

    public AppartmentServiceImpl(AppartmentRepository appartRepo) {
        this.appartRepo = appartRepo;
    }

    @Override
    public Optional<Appartement> getAppart(Long id) {
        return appartRepo.findById(id);
    }

    @Override
    public Optional<List<Appartement>> getApparts(Long... id) {
        List<Appartement> appartements = Arrays.stream(id)
                .map(appartRepo::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return Optional.of(appartements);
    }

    public Optional<List<Appartement>> getAllAppartement() {
        Iterable<Appartement> allAppartIterable = appartRepo.findAll();
        return Optional.of(
                StreamSupport.stream(allAppartIterable.spliterator(), true)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<Appartement> saveAppart(Appartement appart) {
        Appartement temp;
        try {
            /*//Recuperer l'utilisateur connecte
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            appart.setOwnerID(Integer.parseInt(user.getUsername()));*/
            temp = appartRepo.save(appart);
            log.info("Appartement saved successfully.");
        } catch (Exception e) {
            log.error("Error while saving appartement: " + e.getMessage());
            temp = null;
        }
        return Optional.ofNullable(temp);
    }


    @Override
    public boolean deleteAppart(Long id) {
        try {
            appartRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error while deleting appartement: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAppart(Appartement updatedAppartement) {
        try {
            Long appartementId = updatedAppartement.getId();
            if (appartementId == null || !appartRepo.existsById(appartementId)) {
                log.error("Appartement with ID " + updatedAppartement.getId() + " does not exist in the database.");
                return false;
            }
            // Mise a jour de l'appartement
            updatedAppartement.setId(appartementId);//Juste etre sur que l'id est bien initialise
            appartRepo.save(updatedAppartement);
            log.info("Appartement updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("Error while updating appartement: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<List<Appartement>> getAllAppartByOwnerID(int ownerID) {
        List<Appartement> appartements = appartRepo.findByOwnerID(ownerID);
        return Optional.ofNullable(appartements);
    }

}
