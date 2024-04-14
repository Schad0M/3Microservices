package ca.uqtr.dmi.authentification.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credential_info")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;


    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UserRole.class)
    private Set<UserRole> role = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User user;


}
