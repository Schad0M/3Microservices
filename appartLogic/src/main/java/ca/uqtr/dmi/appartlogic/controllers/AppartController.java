package ca.uqtr.dmi.appartlogic.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ca.uqtr.dmi.appartlogic.dto.AppartementDTO;
import ca.uqtr.dmi.appartlogic.modele.Appartement;
import ca.uqtr.dmi.appartlogic.services.AppartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Cette classe est un controlleur qui gere les requetes relatives aux appartements.
 * On y fait de l'injection de dependance avec la classe AppartmentService pour acceder
 * aux methodes de gestion des appartements.
 */
@RestController
@RequestMapping("/appartements")
@AllArgsConstructor//Remplace l'injection de dependance
public class AppartController {
    //TODO assurer que l'implementation de service est good avec authentification
    private final AppartmentService appartmentService;

    /*TEST METHOD*/
    @GetMapping("/fruitsTestAppart")//http://localhost:8181/appartements/fruitsTest
    public ResponseEntity<List<String>> getFruits() {
        List<String> fruits = new ArrayList<>();
        fruits.add("Banane");
        fruits.add("Pomme");
        fruits.add("Orange");
        fruits.add("Fraise");
        fruits.add("Ananas");

        return ResponseEntity.status(HttpStatus.OK).body(fruits);
    }

    @GetMapping("/retrieveAllAppart")
    public ResponseEntity<List<AppartementDTO>> retrieveAllAppart() {
        Optional<List<Appartement>> appartements = appartmentService.getAllAppartement();

        if (appartements.isEmpty()) {
            return ResponseEntity.noContent().build(); // Pas de contenu à renvoyer
        }

        List<AppartementDTO> appartementDTOs = appartements.get().stream()
                .map(AppartementDTO::from)
                .collect(Collectors.toList());

        return new ResponseEntity<>(appartementDTOs, HttpStatus.OK);
    }

    @GetMapping("/retrieveAppartement/{id}")
    public ResponseEntity<AppartementDTO> retrieveAppartement(@PathVariable Long id) {
        return appartmentService.getAppart(id)
                .map(AppartementDTO::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @PostMapping("/addAppartement")
    public ResponseEntity<AppartementDTO> addAppartement(@RequestBody AppartementDTO appartementDTO) {
        Appartement appartement = AppartementDTO.toEntity(appartementDTO);
        Optional<Appartement> savedAppartementOptional = appartmentService.saveAppart(appartement);


        return savedAppartementOptional.map(savedAppartement -> {
            AppartementDTO savedAppartementDTO = AppartementDTO.from(savedAppartement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAppartementDTO);
        }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @PutMapping("/updateAppartement/{id}")
    public ResponseEntity<AppartementDTO> updateAppartement(@PathVariable Long id, @RequestBody AppartementDTO appartementDTO) {
        Appartement appartement = AppartementDTO.toEntity(appartementDTO);
        appartement.setId(id);

        boolean updateSuccess = appartmentService.updateAppart(appartement);

        if (updateSuccess) {
            Optional<Appartement> updatedAppartementOptional = appartmentService.getAppart(id);
            return updatedAppartementOptional.map(updatedAppartement -> {
                AppartementDTO updatedAppartementDTO = AppartementDTO.from(updatedAppartement);
                return ResponseEntity.ok(updatedAppartementDTO);
            }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @DeleteMapping("/deleteAppartement/{id}")
    public ResponseEntity<Void> deleteAppartement(@PathVariable Long id) {
        if (appartmentService.getAppart(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        appartmentService.deleteAppart(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('AGENT')or hasAnyAuthority('ADMIN')")
    @GetMapping("/retrieveAllAppartByOwner/{ownerId}")
    public ResponseEntity<List<AppartementDTO>> retrieveAllAppartByOwner(@PathVariable int ownerId) {
        //TODO : Il faut etre sur que seul l'user proprietaire OU un admin peut voir ses apparts
        Optional<List<Appartement>> appartementsOptional = appartmentService.getAllAppartByOwnerID(ownerId);

        if (appartementsOptional.isEmpty()) {
            return ResponseEntity.noContent().build(); // Pas de contenu à renvoyer
        }

        List<AppartementDTO> appartementDTOs = appartementsOptional.get().stream()
                .map(AppartementDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(appartementDTOs);
    }

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @GetMapping("/retrieveOwnerID/{appartId}")
    public ResponseEntity<Integer> retrieveOwnerID(@PathVariable Long appartId) {
        Optional<Appartement> appartementOptional = appartmentService.getAppart(appartId);

        if (appartementOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Appartement non trouvé
        }

        Integer ownerId = appartementOptional.get().getOwnerID();
        return ResponseEntity.ok(ownerId);
    }

    //TODO : Ajouter une methode pour recuperer les appartements actifs seulement?
    //TODO : Ajouter une methode pour mettre un appartement en actif ou inactif?? --> juste utiliser updateAppartement
}