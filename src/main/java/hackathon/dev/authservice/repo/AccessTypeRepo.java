package hackathon.dev.authservice.repo;

import hackathon.dev.authservice.model.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessTypeRepo extends JpaRepository<AccessType, Long> {
    Optional<AccessType> findById(Long id);
}
