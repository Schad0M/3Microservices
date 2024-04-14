package ca.uqtr.dmi.authentification.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "role_info")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;


    @Override
    public boolean equals(Object obj) {
        return obj instanceof  UserRole && Objects.equals(name, ((UserRole)obj).name);
    }

    @Override
    public String toString() {
        return name;
    }
}
