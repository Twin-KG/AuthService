package hackathon.dev.authservice.service.impl;

import hackathon.dev.authservice.model.Role;
import hackathon.dev.authservice.repo.RoleRepository;
import hackathon.dev.authservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
