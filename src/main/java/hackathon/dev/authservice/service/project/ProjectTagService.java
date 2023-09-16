package hackathon.dev.authservice.service.project;

import hackathon.dev.authservice.model.project.ProjectTag;

import java.util.List;
import java.util.Optional;

public interface ProjectTagService {
    List<ProjectTag> getAllProjectTags();
    ProjectTag saveNewProjectTag(ProjectTag projectTag);
    Optional<ProjectTag> findProjectTagById(Long id);
    void deleteProjectTagById(Long id);
}
