package hackathon.dev.authservice.security.services;

import hackathon.dev.authservice.model.User;
import hackathon.dev.authservice.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Invalid Username ... !"));

        return new SecurityUser(user);
    }
}