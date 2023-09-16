package hackathon.dev.authservice.service.project.impl;

import hackathon.dev.authservice.constant.MessageFormatConstant;
import hackathon.dev.authservice.exception.domain.DataNotFoundException;
import hackathon.dev.authservice.model.project.Project;
import hackathon.dev.authservice.repo.project.ProjectRepository;
import hackathon.dev.authservice.service.project.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project saveNewProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void deleteProjectById(Long id) {
        Optional<Project> project = findProjectById(id);
        if(project.isPresent()){
            projectRepository.deleteById(id);
        }else{
            throw new DataNotFoundException(String.format(MessageFormatConstant.DATA_NOT_FOUND, "PROJECT", id));
        }
    }
}
