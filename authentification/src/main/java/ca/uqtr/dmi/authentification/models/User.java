package ca.uqtr.dmi.authentification.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long numeroID;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String mobile;
    //private String username; //Sera dans le UserCredential
    //private String motDePasse; //Sera dans le UserCredential
}
