package hackathon.dev.authservice.service.impl;

import hackathon.dev.authservice.exception.domain.EmailAlreadyExistException;
import hackathon.dev.authservice.exception.domain.UserNotFoundException;
import hackathon.dev.authservice.exception.domain.UsernameAlreadyExistException;
import hackathon.dev.authservice.model.User;
import hackathon.dev.authservice.repo.UserRepository;
import hackathon.dev.authservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User validateNewUserAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameAlreadyExistException, EmailAlreadyExistException {
        Optional<User> userByUsername = userRepository.findUserByUsername(newUsername);
        Optional<User> userByEmail = userRepository.findUserByEmail(newEmail);

        if(StringUtils.isNotBlank(currentUsername)){
            Optional<User> currentUser = userRepository.findUserByUsername(currentUsername);
            if (!currentUser.isPresent()){
                throw new UserNotFoundException("User is not found");
            }
            if(userByUsername.isPresent() && !currentUser.get().getId().equals(userByUsername.get().getId())){
                throw new UsernameAlreadyExistException("Username already exists");
            }
            if(userByEmail.isPresent() && !currentUser.get().getId().equals(userByEmail.get().getId())){
                throw new EmailAlreadyExistException("Email already exists");
            }
            return currentUser.get();
        }else{
            if(userByUsername.isPresent()){
                throw new UsernameAlreadyExistException("Username already exists");
            }
            if(userByEmail.isPresent()){
                throw new EmailAlreadyExistException("Email already exists");
            }
            return null;
        }
    }

}
