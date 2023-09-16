package hackathon.dev.authservice.repo;

import hackathon.dev.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Short> {
    Optional<Role> findRoleByName(String roleName);
}
