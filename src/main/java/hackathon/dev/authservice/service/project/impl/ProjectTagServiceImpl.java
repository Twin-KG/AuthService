package hackathon.dev.authservice.service.project.impl;

import hackathon.dev.authservice.constant.MessageFormatConstant;
import hackathon.dev.authservice.exception.domain.DataNotFoundException;
import hackathon.dev.authservice.model.project.ProjectTag;
import hackathon.dev.authservice.repo.project.ProjectTagRepository;
import hackathon.dev.authservice.service.project.ProjectTagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectTagServiceImpl implements ProjectTagService {

    private final ProjectTagRepository projectTagRepository;

    @Override
    public List<ProjectTag> getAllProjectTags() {
        return projectTagRepository.findAll();
    }

    @Override
    public ProjectTag saveNewProjectTag(ProjectTag projectTag) {
        return projectTagRepository.save(projectTag);
    }

    @Override
    public Optional<ProjectTag> findProjectTagById(Long id) {
        return projectTagRepository.findById(id);
    }

    @Override
    public void deleteProjectTagById(Long id) {

        Optional<ProjectTag> projectTagOptional = findProjectTagById(id);
        if(projectTagOptional.isPresent()){
            ProjectTag projectTag = projectTagOptional.get();
            projectTag.removeAllProjects();
            projectTagRepository.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(MessageFormatConstant.DATA_NOT_FOUND, "PROJECT-TAG", id));
        }
    }
}
