package ca.uqtr.dmi.appartlogic.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getJwtToken(HttpServletRequest request);
    UserDetails extractUser(String token);

}