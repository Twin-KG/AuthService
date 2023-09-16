package hackathon.dev.authservice.service;

import hackathon.dev.authservice.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    User saveUser(User user);
    User validateNewUserAndEmail(String currentUsername, String newUsername, String newEmail);
}
