package ca.uqtr.dmi.messagerielogic.modele;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messagerie_info")
public class Messagerie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long msgID;

    private Long senderId;
    private Long receiverId;
    private Long appId;
    private String message;
    private String timestamp;
}
