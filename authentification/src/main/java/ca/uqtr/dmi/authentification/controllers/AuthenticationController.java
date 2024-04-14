package ca.uqtr.dmi.authentification.controllers;

import ca.uqtr.dmi.authentification.beans.JwtConfigBean;
import ca.uqtr.dmi.authentification.dto.ConnectedUserDTO;
import ca.uqtr.dmi.authentification.dto.SignInDTO;
import ca.uqtr.dmi.authentification.dto.SignUpDTO;
import ca.uqtr.dmi.authentification.models.UserCredential;
import ca.uqtr.dmi.authentification.services.JwtService;
import ca.uqtr.dmi.authentification.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtService jwtService;

    private final JwtConfigBean jwtConfigBean;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService,
                                    JwtService jwtService,
                                    JwtConfigBean jwtConfigBean,
                                    AuthenticationManager authenticationManager){

        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtConfigBean = jwtConfigBean;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/sign-in", produces="application/json")
    public ResponseEntity<ConnectedUserDTO> signIn(@RequestBody SignInDTO authDTO){
        ConnectedUserDTO userDetailsDTO = (ConnectedUserDTO)  authenticate(authDTO).getPrincipal();
        String prefix = jwtConfigBean.getTokenPrefix();
        String token = jwtService.generateToken(userDetailsDTO);

        return ResponseEntity.ok()
                .header(jwtConfigBean.getTokenHeader(), prefix+" "+token)
                .body(userDetailsDTO);

    }

    @PostMapping(path = "/sign-up", produces="application/json")
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO){

        UserCredential credential = signUpDTO.toCredential();
        boolean success = userService.createNewUser(credential);
        return success?  ResponseEntity
                .ok("Success") :
                ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Error");

    }


    private Authentication authenticate(SignInDTO loginRequestDto)  throws BadCredentialsException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername().toLowerCase().trim(),
                        loginRequestDto.getPassword().trim()));

    }

}
