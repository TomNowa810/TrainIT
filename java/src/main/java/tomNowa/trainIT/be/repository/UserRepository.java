package tomNowa.trainIT.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tomNowa.trainIT.be.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

        User findByUserName(final String username);
}
