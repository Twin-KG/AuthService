package hackathon.dev.authservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String audio;
    private String video;
    private String image;
    private String link;
    private String embedUrl;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accessType_id", referencedColumnName = "id")
    private AccessType accessType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tier_id", referencedColumnName = "id")
    private Tier tier;

    private Long professionalId;
}
