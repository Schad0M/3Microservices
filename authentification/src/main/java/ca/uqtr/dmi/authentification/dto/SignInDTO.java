package ca.uqtr.dmi.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SignInDTO {
    private String username;
    private String password;
}
