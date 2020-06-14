package pl.kabacinsp.tawauth.repository;

import pl.kabacinsp.tawauth.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserClient, String> {

  Optional<UserClient> findByUsername(String username);

  Optional<UserClient> findById(long id);

}
