package hackathon.dev.authservice.security.services;

import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.dto.ResponseUser;

public interface SecurityService {
    String login(LoginUserDto loginDto);
    ResponseUser register(RegisterUserDto registerUserDto);
}
