package hackathon.dev.authservice.controller;

import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.dto.ResponseBuilder;
import hackathon.dev.authservice.dto.ResponseUser;
import hackathon.dev.authservice.exception.ExceptionHandling;
import hackathon.dev.authservice.model.User;
import hackathon.dev.authservice.security.services.SecurityService;
import hackathon.dev.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static hackathon.dev.authservice.constant.SecurityConstant.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@AllArgsConstructor
public class SecurityController extends ExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService securityService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ZResponse> getCurrentUser() {

        logger.info("Getting current user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> me = userService.findUserByUsername((String) authentication.getPrincipal());

        if(me.isPresent()){
            return ResponseEntity.ok(
                    ResponseBuilder.build(true, HttpStatus.OK, "Successfully fetched", me));
        }

        return new ResponseEntity<>(
                ResponseBuilder.build(true, HttpStatus.NO_CONTENT, "Who are you", me), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<ZResponse> login(@RequestBody LoginUserDto loginDto){

        logger.info("Logging in");

        String accessToken = securityService.login(loginDto);
        if (StringUtils.hasText(accessToken)){
            return new ResponseEntity<>(
                    ResponseBuilder.build(true, HttpStatus.OK, "Successfully Login", null),
                    getJwtHeader(accessToken), HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body(
                ResponseBuilder.build(false, HttpStatus.FORBIDDEN, "Failed Login", null));
    }

    @PostMapping("/register")
    public ResponseEntity<ZResponse> addNewUser(@RequestBody RegisterUserDto user){

        logger.info("Registering user");

        final ResponseUser responseUser = securityService.register(user);
        return ResponseEntity.ok(
                ResponseBuilder.build(true, HttpStatus.OK, "Successfully Registered", responseUser));
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<ZResponse> resetPassword(@PathVariable("email") String email) {

        logger.info("Reseting password by email");

        User user = userService.resetPasswordByEmail(email);
        return ResponseEntity.ok(
                ResponseBuilder.build(true, HttpStatus.OK, "Successfully reset password", user));
    }

    private HttpHeaders getJwtHeader(String token) {
        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.add(JWT_TOKEN_HEADER, token);
        return jwtHeader;
    }
}
