package hackathon.dev.authservice.converter;

import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.dto.ResponseUser;
import hackathon.dev.authservice.model.User;

public class UserConverter {

    public static ResponseUser entityToDto(User user){
        return ResponseUser
                .builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isEnabled(user.isEnabled())
                .build();
    }

    public static User dtoToEntity(RegisterUserDto user){
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isEnabled(true)
                .build();
    }
}
