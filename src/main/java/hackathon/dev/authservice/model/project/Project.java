package hackathon.dev.authservice.model.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter @Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String coverPhotoUrl;
    private String gitLink;
    private String websiteLink;
    private String description;

    @ManyToOne
    private ProjectCategory projectCategory;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_tag_link",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<ProjectTag> tags = new HashSet<>();

}
