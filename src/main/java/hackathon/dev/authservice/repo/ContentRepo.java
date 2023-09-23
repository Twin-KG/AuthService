package hackathon.dev.authservice.repo;

import hackathon.dev.authservice.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepo extends JpaRepository<Content, Long> {
    @Query("SELECT c FROM Content c " +
            "LEFT JOIN AccessType a ON a.id = :accessTypeId " +
            "LEFT JOIN Tier t ON t.id = :tierId " +
            "WHERE c.accessType.rank > a.rank OR c.tier.rank > t.rank")
    Page<Content> findContentByAccessTypeOrTierId(PageRequest pageRequest, Long accessTypeId, Long tierId);
}
