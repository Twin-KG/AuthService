package hackathon.dev.authservice.repo.project;

import hackathon.dev.authservice.model.project.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long> {
}
