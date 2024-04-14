package ca.uqtr.dmi.messagerielogic.controllers;


import ca.uqtr.dmi.messagerielogic.dto.MessagerieDTO;
import ca.uqtr.dmi.messagerielogic.modele.Messagerie;
import ca.uqtr.dmi.messagerielogic.services.MessagerieService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor//Remplace l'injection de dependance
public class MessagerieController {
    private final MessagerieService messagerieService;

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @GetMapping("/getMessage/{id}")
    public ResponseEntity<List<MessagerieDTO>> getMessage(@PathVariable long id) {
        //TODO : Il faut etre sur que seul l'user proprietaire OU un admin peut voir ses apparts
        Optional<List<Messagerie>> messages = messagerieService.getMessageByUserID(id);
        return messages.map(list -> list.stream()
                        .map(MessagerieDTO::from)
                        .collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('AGENT')")
    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessagerieDTO messageDTO) {
        Messagerie message = MessagerieDTO.toEntity(messageDTO);
        boolean messageSent = messagerieService.sendingMessage(message);
        if (messageSent) {
            return ResponseEntity.ok("Message sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending message.");
        }
    }
}
