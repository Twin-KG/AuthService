package hackathon.dev.authservice.controller;

import hackathon.dev.authservice.domain.CustomHttpResponse;
import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.dto.ResponseUser;
import hackathon.dev.authservice.security.services.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static hackathon.dev.authservice.constant.SecurityConstant.*;

@Controller
@RequestMapping("/auth")
@CrossOrigin
@AllArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<CustomHttpResponse> login(@RequestBody LoginUserDto loginDto){
        String accessToken = securityService.login(loginDto);
        if (StringUtils.hasText(accessToken)){
            return new ResponseEntity<>(
                    createHttpResponse(HttpStatus.OK, "Successfully Login"),
                getJwtHeader(accessToken), HttpStatus.OK
            );
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUser> addNewUser(@RequestBody RegisterUserDto user){
        final ResponseUser responseUser = securityService.register(user);
        return ResponseEntity.ok(responseUser);
    }

    private HttpHeaders getJwtHeader(String token) {
        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.add(JWT_TOKEN_HEADER, token);
        return jwtHeader;
    }

    private CustomHttpResponse createHttpResponse(HttpStatus httpStatus, String message){
        return new CustomHttpResponse(
                LocalDateTime.now(ZoneId.of("Asia/Yangon")), httpStatus.value(), httpStatus.getReasonPhrase(), message
        );
    }
}
