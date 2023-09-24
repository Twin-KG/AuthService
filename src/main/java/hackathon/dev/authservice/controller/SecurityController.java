package hackathon.dev.authservice.controller;

import hackathon.dev.authservice.constant.CustomMessage;
import hackathon.dev.authservice.constant.QueueConfig;
import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.*;
import hackathon.dev.authservice.exception.ExceptionHandling;
import hackathon.dev.authservice.security.services.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static hackathon.dev.authservice.constant.SecurityConstant.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@AllArgsConstructor
public class SecurityController extends ExceptionHandling {

    private final SecurityService securityService;

//    @GetMapping("/me")
//    public ResponseEntity<ZResponse> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Optional<User> me = userService.findUserByUsername((String) authentication.getPrincipal());
//
//        if(me.isPresent()){
//            return ResponseEntity.ok(
//                    ResponseBuilder.build(true, HttpStatus.OK, "Successfully fetched", me));
//        }
//
//        return new ResponseEntity<>(
//                ResponseBuilder.build(true, HttpStatus.NO_CONTENT, "Who are you", me), HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginDto){
        LoginResponseDto response = securityService.login(loginDto);
        if (!Objects.isNull(response) && StringUtils.hasText(response.getToken())){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<ZResponse<AuthProfessionDto>> addNewUser(@RequestBody RegisterUserDto user){

        final AuthProfessionDto professions = securityService.register(user);

        return ResponseEntity.ok( ZResponse.<AuthProfessionDto>builder()
                .success(true)
                .message("Successfully registered...")
                .data(professions)
                .build());
    }

    private HttpHeaders getJwtHeader(String token) {
        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.add(JWT_TOKEN_HEADER, token);
        return jwtHeader;
    }
}
