package ca.uqtr.dmi.messagerielogic.repositories;

import ca.uqtr.dmi.messagerielogic.modele.Messagerie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagerieRepository extends CrudRepository<Messagerie, Long>{
        List<Messagerie> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
