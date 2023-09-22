package hackathon.dev.authservice.dto;

import hackathon.dev.authservice.constant.ActiveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professions {

    private Long id;

    private String username;
    private String password;
    private String email;

    private String phoneNumber;
    private String address;
    private String profileImg;
    private String coverPhoto;
    private String bio;
    private String profileLink;

    private ActiveStatus activeStatus;

}
