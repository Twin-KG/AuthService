package hackathon.dev.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private String type;
    private AuthProfessionDto user;
}
