package hackathon.dev.authservice.security.services;

import hackathon.dev.authservice.dto.LoginResponseDto;
import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.Professions;
import hackathon.dev.authservice.dto.RegisterUserDto;

public interface SecurityService {
    LoginResponseDto login(LoginUserDto loginDto);
    Professions register(RegisterUserDto registerUserDto);
}
