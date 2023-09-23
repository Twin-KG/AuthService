package hackathon.dev.authservice.repo;

import hackathon.dev.authservice.model.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TierRepo extends JpaRepository<Tier, Long> {
    Optional<Tier> findById(Long id);
}
