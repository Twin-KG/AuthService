package hackathon.dev.authservice.security.services.impl;

import hackathon.dev.authservice.converter.UserConverter;
import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.dto.ResponseUser;
import hackathon.dev.authservice.exception.domain.EmailAlreadyExistException;
import hackathon.dev.authservice.exception.domain.UserNotFoundException;
import hackathon.dev.authservice.exception.domain.UsernameAlreadyExistException;
import hackathon.dev.authservice.model.Role;
import hackathon.dev.authservice.model.User;
import hackathon.dev.authservice.security.services.SecurityService;
import hackathon.dev.authservice.security.utils.JwtUtilities;
import hackathon.dev.authservice.service.RoleService;
import hackathon.dev.authservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    @Transactional
    public String login(LoginUserDto loginDto) {
        final Optional<User> userByUsername = userService.findUserByUsernameOrEmail(loginDto.getUsername());
        if(userByUsername.isPresent()){
            User loginUser = userByUsername.get();
            boolean isMatchPwd = passwordEncoder.matches(loginDto.getPassword(), loginUser.getPassword());
            if(isMatchPwd){
                return generateAccessToken(loginUser);
            }
        }else{
            throw new UserNotFoundException("Username or email is not found");
        }
        return null;
    }

    @Override
    @Transactional
    public ResponseUser register(RegisterUserDto registerUserDto) {

        User user = null;

        try{
            userService.validateNewUserAndEmail("", registerUserDto.getUsername(), registerUserDto.getEmail());

            String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(encodedPassword);

            User entity = UserConverter.dtoToEntity(registerUserDto);

            Optional<Role> defaultRole = roleService.findRoleByName("ROLE_USER");
            if(defaultRole.isPresent()){
                entity.setRoles(Set.of(defaultRole.get()));
            }

            user = userService.saveUser(entity);
        } catch (UsernameAlreadyExistException e){
            throw e;
        } catch (EmailAlreadyExistException e) {
            throw  e;
        }catch (Exception e){
            e.printStackTrace();
        }

        return UserConverter.entityToDto(user);
    }

    private String generateAccessToken(User loginUser) {
        List<String> roleNameList = loginUser.getRoles().stream().map(Role::getName).toList();
        return jwtUtilities.generateToken(loginUser.getUsername(), roleNameList);
    }
}
