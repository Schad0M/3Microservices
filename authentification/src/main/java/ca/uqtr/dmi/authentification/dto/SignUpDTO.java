package ca.uqtr.dmi.authentification.dto;

import ca.uqtr.dmi.authentification.constants.Role;
import ca.uqtr.dmi.authentification.models.User;
import ca.uqtr.dmi.authentification.models.UserCredential;
import ca.uqtr.dmi.authentification.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class SignUpDTO {
    private String username;
    private String password;
    private String passwordConfirmm;
    private String firstname;
    private String lastname;
    private String email;
    private int role;
    private String address;
    private String mobile;

    private User toUserInfo() {
        User userInfo = new User();
        userInfo.setFirstname(firstname);
        userInfo.setLastname(lastname);
        userInfo.setMobile(mobile);
        userInfo.setAddress(address);
        userInfo.setEmail(email);
        return userInfo;
    }

    public UserCredential toCredential() {
        UserCredential credentialInfo = new UserCredential();
        credentialInfo.setUsername(username);
        credentialInfo.setPassword(password);
        credentialInfo.setUser(toUserInfo());
        credentialInfo.setRole(Set.of(toRole()));
        return  credentialInfo;
    }

    private UserRole toRole() {
        UserRole userRole = new UserRole();
        userRole.setName(Role.of(role).name());
        return userRole;
    }
}
