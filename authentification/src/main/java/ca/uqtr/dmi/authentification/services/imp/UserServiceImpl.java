package ca.uqtr.dmi.authentification.services.imp;

import ca.uqtr.dmi.authentification.dto.ConnectedUserDTO;
import ca.uqtr.dmi.authentification.models.UserCredential;
import ca.uqtr.dmi.authentification.repositories.CredentialRepository;
import ca.uqtr.dmi.authentification.repositories.UserRepository;
import ca.uqtr.dmi.authentification.repositories.UserRoleRepository;
import ca.uqtr.dmi.authentification.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service("main")
public class UserServiceImpl implements UserService {


    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder encoder;


    public UserServiceImpl(CredentialRepository repository,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder encoder) {
        this.credentialRepository = repository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential credential = credentialRepository.findByUsername(username);
        if(credential == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated with Success!!!");
        return new ConnectedUserDTO(credential);
    }

    @Override
    public boolean createNewUser(UserCredential credential) {
        credential.setPassword(encoder.encode(credential.getPassword().trim()));
        credential.setUsername(credential.getUsername().toLowerCase().trim());
        userRoleRepository.saveAll(credential.getRole());
        userRepository.save(credential.getUser());
        credentialRepository.save(credential);
        return true;
    }
}
