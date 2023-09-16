package hackathon.dev.authservice.model.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter @Setter
public class ProjectCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectCategory")
    private Set<Project> projects;

    public void removeAllProjects(){
        projects.forEach(project -> project.setProjectCategory(null));
        projects.clear();
    }
}
