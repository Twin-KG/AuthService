package hackathon.dev.authservice.repo.project;

import hackathon.dev.authservice.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
