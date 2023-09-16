package hackathon.dev.authservice.service.project.impl;

import hackathon.dev.authservice.constant.MessageFormatConstant;
import hackathon.dev.authservice.exception.domain.DataNotFoundException;
import hackathon.dev.authservice.model.project.ProjectCategory;
import hackathon.dev.authservice.repo.project.ProjectCategoryRepository;
import hackathon.dev.authservice.service.project.ProjectCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectCategoryServiceImpl implements ProjectCategoryService {

    private final ProjectCategoryRepository projectCategoryRepository;

    @Override
    public List<ProjectCategory> getAllProjectCategories() {
        return projectCategoryRepository.findAll();
    }

    @Override
    public ProjectCategory saveNewProjectCategory(ProjectCategory projectCategory) {
        return projectCategoryRepository.save(projectCategory);
    }

    @Override
    public Optional<ProjectCategory> findProjectCategoryById(Long id) {
        return projectCategoryRepository.findById(id);
    }

    @Override
    public void deleteProjectCategoryById(Long id) {
        Optional<ProjectCategory> projectCategoryOptional = findProjectCategoryById(id);
        if(projectCategoryOptional.isPresent()){
            ProjectCategory projectCategory = projectCategoryOptional.get();
            projectCategory.removeAllProjects();
            projectCategoryRepository.delete(projectCategory);
        } else {
            throw new DataNotFoundException(String.format(MessageFormatConstant.DATA_NOT_FOUND, "PROJECT-CATEGORY", id));
        }
    }
}
