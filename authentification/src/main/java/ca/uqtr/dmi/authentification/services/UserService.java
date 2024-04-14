package ca.uqtr.dmi.authentification.services;

import ca.uqtr.dmi.authentification.models.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean createNewUser(UserCredential credential);
}
