package hackathon.dev.authservice.security.services;

import hackathon.dev.authservice.dto.*;

public interface SecurityService {
    LoginResponseDto login(LoginUserDto loginDto);
    AuthProfessionDto register(RegisterUserDto registerUserDto);
}
