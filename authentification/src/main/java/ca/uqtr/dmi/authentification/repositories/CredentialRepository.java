package ca.uqtr.dmi.authentification.repositories;

import ca.uqtr.dmi.authentification.models.UserCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends CrudRepository<UserCredential, Long> {
    //@Query("SELECT u FROM UserInfo u WHERE u.username = ?1" )
    UserCredential findByUsername(String username);

}
