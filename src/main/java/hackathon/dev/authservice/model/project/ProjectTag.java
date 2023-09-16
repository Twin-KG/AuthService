package hackathon.dev.authservice.model.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@Getter @Setter
public class ProjectTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy="tags")
    private Set<Project> projects;

    public void removeAllProjects(){
        projects.forEach(p -> p.setTags(null));
        projects.clear();
    }
}
