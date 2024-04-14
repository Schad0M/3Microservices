package ca.uqtr.dmi.appartlogic.services.impl;

import ca.uqtr.dmi.appartlogic.beans.JwtConfigBean;
import ca.uqtr.dmi.appartlogic.services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Component
//@Service() //Le prof ne l'a pas mis
public class JwtServiceImpl implements JwtService {
    private final JwtConfigBean jwtContants;

    public JwtServiceImpl(JwtConfigBean jwtContants){

        this.jwtContants = jwtContants;

    }

    @Override
    public String getJwtToken(HttpServletRequest request) {

        String headerAuth = request.getHeader(jwtContants.getTokenHeader());
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtContants.getTokenPrefix())){
            return headerAuth.replace(jwtContants.getTokenPrefix(), "").trim();
        }
        return null;
    }

    @Override
    public UserDetails extractUser(String token) {
        DecodedJWT jwtToken = decodeToken(token);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {

                String json = jwtToken.getClaim("roles").asString();
                String[] roles =  new Gson().fromJson(json, String[].class);
                List<SimpleGrantedAuthority> auths = new ArrayList<>();
                for(String role:roles)
                    auths.add(new SimpleGrantedAuthority(role));
                return auths;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return jwtToken.getSubject();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }


    private DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtContants.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtContants.getTokenIssuer())
                .build();
        return verifier.verify(token);
    }

}
