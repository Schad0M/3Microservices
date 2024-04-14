package ca.uqtr.dmi.appartlogic.services;

import ca.uqtr.dmi.appartlogic.modele.Appartement;

import java.util.List;
import java.util.Optional;

public interface AppartmentService {
    //TODO: Modifier le code pour garder seulement les methodes necessaires
    /**
     * @return  l'appartement dont l'id est passe en parametre
     * */
    Optional<Appartement> getAppart(Long id);
    /***
     * Cette methode retourne une liste d'appartements dont les id sont passes en parametre.
     * @param id la liste des id des appartements a retourner
     * @return une liste d'appartements
     */
    Optional<List<Appartement>> getApparts(Long... id);

    Optional<Appartement> saveAppart(Appartement appart);

    boolean deleteAppart(Long id);
    boolean updateAppart(Appartement appart);

    /**
     * @return  la liste de tous les appartements (pour la bibliotheque)
     * */
    Optional<List<Appartement>> getAllAppartement();

    /**
     *
     * @param ownerID
     * @return
     */
    Optional<List<Appartement>> getAllAppartByOwnerID(int ownerID);

    }
