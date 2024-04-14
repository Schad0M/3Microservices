package ca.uqtr.dmi.messagerielogic.services.impl;

import ca.uqtr.dmi.messagerielogic.modele.Messagerie;
import ca.uqtr.dmi.messagerielogic.repositories.MessagerieRepository;
import ca.uqtr.dmi.messagerielogic.services.MessagerieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MessagerieServiceImpl implements MessagerieService {
    private final MessagerieRepository messagerieRepository;

    public MessagerieServiceImpl(MessagerieRepository messagerieRepository) {
        this.messagerieRepository = messagerieRepository;
    }
    @Override
    public boolean sendingMessage(Messagerie message) {
        try {
            messagerieRepository.save(message);
            return true;
        } catch (Exception e) {
            log.error("Error while saving message: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<List<Messagerie>> getMessageByUserID(Long userId) {
        List<Messagerie> messages = messagerieRepository.findAllBySenderIdOrReceiverId(userId, userId);
        if (messages.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(messages);
        }
    }


}
