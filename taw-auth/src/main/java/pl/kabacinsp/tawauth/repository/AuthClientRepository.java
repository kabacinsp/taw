package pl.kabacinsp.tawauth.repository;

import pl.kabacinsp.tawauth.model.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthClientRepository extends JpaRepository<AuthClient, String> {
  Optional<AuthClient> findByClientId(String clientId);
}
