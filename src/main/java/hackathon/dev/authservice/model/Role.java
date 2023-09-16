package hackathon.dev.authservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
