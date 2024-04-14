package ca.uqtr.dmi.authentification.services;

import ca.uqtr.dmi.authentification.dto.ConnectedUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getJwtToken(HttpServletRequest request);
    String generateToken(ConnectedUserDTO userDetailsDTO);

}