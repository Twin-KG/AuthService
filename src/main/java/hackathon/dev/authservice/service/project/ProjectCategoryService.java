package hackathon.dev.authservice.service.project;

import hackathon.dev.authservice.model.project.ProjectCategory;

import java.util.List;
import java.util.Optional;

public interface ProjectCategoryService {
    List<ProjectCategory> getAllProjectCategories();
    ProjectCategory saveNewProjectCategory(ProjectCategory projectCategory);
    Optional<ProjectCategory> findProjectCategoryById(Long id);
    void deleteProjectCategoryById(Long id);
}
