package hackathon.dev.authservice.service.project;

import hackathon.dev.authservice.model.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Project saveNewProject(Project project);

    Optional<Project> findProjectById(Long id);

    void deleteProjectById(Long id);
}
