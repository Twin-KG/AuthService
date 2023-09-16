package hackathon.dev.authservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean isEnabled;
}
