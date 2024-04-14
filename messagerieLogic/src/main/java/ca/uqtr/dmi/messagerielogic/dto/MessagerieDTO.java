package ca.uqtr.dmi.messagerielogic.dto;

import ca.uqtr.dmi.messagerielogic.modele.Messagerie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessagerieDTO {
    private Long msgID;
    private Long senderId;
    private Long receiverId;
    private Long appId;
    private String message;
    private String timestamp;

    public static MessagerieDTO from(Messagerie messagerie) {
        return new MessagerieDTO(
                messagerie.getMsgID(),
                messagerie.getSenderId(),
                messagerie.getReceiverId(),
                messagerie.getAppId(),
                messagerie.getMessage(),
                messagerie.getTimestamp()
        );
    }

    public static Messagerie toEntity(MessagerieDTO messageDTO) {
        Messagerie message = new Messagerie();
        //jai enleve le msgID car il est genere automatiquement
        message.setSenderId(messageDTO.getSenderId());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setAppId(messageDTO.getAppId());
        message.setMessage(messageDTO.getMessage());
        message.setTimestamp(messageDTO.getTimestamp());
        return message;
    }
}
