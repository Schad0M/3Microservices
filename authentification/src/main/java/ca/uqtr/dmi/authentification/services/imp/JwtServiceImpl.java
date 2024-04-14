package ca.uqtr.dmi.authentification.services.imp;

import ca.uqtr.dmi.authentification.beans.JwtConfigBean;
import ca.uqtr.dmi.authentification.dto.ConnectedUserDTO;
import ca.uqtr.dmi.authentification.services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Component
public class JwtServiceImpl implements JwtService {
    private final JwtConfigBean jwtConstants;

    public JwtServiceImpl(JwtConfigBean jwtConstants){
        this.jwtConstants = jwtConstants;

    }

    @Override
    public String getJwtToken(HttpServletRequest request) {

        String headerAuth = request.getHeader(jwtConstants.getTokenHeader());
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtConstants.getTokenPrefix())){
            return headerAuth.replace(jwtConstants.getTokenPrefix(), "").trim();
        }
        return null;
    }

    @Override
    public String generateToken(ConnectedUserDTO userDetailsDTO) {
        Calendar exp = Calendar.getInstance();
        Calendar issDate = (Calendar) exp.clone();
        exp.add(Calendar.HOUR, jwtConstants.getTokenExpiration());

        List<String> roles = userDetailsDTO.getAuthorities()
                .stream()
                .map(Objects::toString)
                .toList();
        String json = new Gson().toJson(roles);


        Map<String, Object> payload = new HashMap<>();
        payload.put("id", userDetailsDTO.getId());
        payload.put("username", userDetailsDTO.getUsername());
        payload.put("roles", json);

        return JWT.create()
                .withSubject((userDetailsDTO.getUsername()))
                .withIssuedAt(issDate.getTime())
                .withExpiresAt(exp.getTime())
                .withIssuer(jwtConstants.getTokenIssuer())
                .withClaim("roles", json)
                .withPayload(payload)
                .sign(Algorithm.HMAC256(jwtConstants.getSecret()));

    }
}
