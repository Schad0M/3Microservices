package ca.uqtr.dmi.messagerielogic.services;

import ca.uqtr.dmi.messagerielogic.modele.Messagerie;

import java.util.List;
import java.util.Optional;


public interface MessagerieService {
    //Methode to save new messages
    boolean sendingMessage(Messagerie message);
    /**
     * Get tous les messages qui conserne ce user en particulier
     * C'est a dire qu'il px etre le sender ou le receiver
     */
    Optional<List<Messagerie>> getMessageByUserID(Long id);
}
