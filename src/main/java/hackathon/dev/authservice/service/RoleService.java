package hackathon.dev.authservice.service;

import hackathon.dev.authservice.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRoleByName(String roleName);
}
