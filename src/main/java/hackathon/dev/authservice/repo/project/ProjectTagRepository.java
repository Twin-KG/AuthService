package hackathon.dev.authservice.repo.project;

import hackathon.dev.authservice.model.project.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {
}
