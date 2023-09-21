package hackathon.dev.authservice.service.impl;

import hackathon.dev.authservice.exception.domain.EmailAlreadyExistException;
import hackathon.dev.authservice.exception.domain.UserNotFoundException;
import hackathon.dev.authservice.exception.domain.UsernameAlreadyExistException;
import hackathon.dev.authservice.model.User;
import hackathon.dev.authservice.repo.UserRepository;
import hackathon.dev.authservice.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static hackathon.dev.authservice.constant.UserConstant.USER_NOT_FOUND_BY_EMAIL;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserByUsernameOrEmail(String username){
        return userRepository.findUserByUsernameOrEmail(username, username);
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

    @Override
    public User resetPasswordByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL + " " + email);
        }

        User user = userOptional.get();
        String passwordInPlainText = generatePassword();
        user.setPassword(encodePassword(passwordInPlainText));
        userRepository.save(user);

//        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
        return user;
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
