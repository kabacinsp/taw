package pl.kabacinsp.tawauth.repository;

import pl.kabacinsp.tawauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByUsername(String username);

  Optional<User> findById(long id);

}
