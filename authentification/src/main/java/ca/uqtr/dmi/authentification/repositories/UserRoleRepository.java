package ca.uqtr.dmi.authentification.repositories;

import ca.uqtr.dmi.authentification.models.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    //@Query("SELECT u FROM UserInfo u WHERE u.username = ?1" )
}