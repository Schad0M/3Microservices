package ca.uqtr.dmi.authentification.repositories;

import ca.uqtr.dmi.authentification.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
