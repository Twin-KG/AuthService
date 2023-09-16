package hackathon.dev.authservice.exception;

import hackathon.dev.authservice.domain.CustomHttpResponse;
import hackathon.dev.authservice.exception.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

import static hackathon.dev.authservice.constant.UserConstant.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<CustomHttpResponse> usernameAlreadyExistException() {
        return createHttpResponse(BAD_REQUEST, USERNAME_ALREADY_EXIST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<CustomHttpResponse> emailAlreadyExistException() {
        return createHttpResponse(BAD_REQUEST, EMAIL_ALREADY_EXIST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> userNotFoundException() {
        return createHttpResponse(BAD_REQUEST, USER_NOT_FOUND_BY_USERNAME);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> usernameNotFoundException() {
        return createHttpResponse(BAD_REQUEST, USER_NOT_FOUND_BY_USERNAME);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<CustomHttpResponse> invalidPasswordException() {
        return createHttpResponse(BAD_REQUEST, INVALID_PASSWORD);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomHttpResponse> noHandlerFoundException() {
        return createHttpResponse(BAD_REQUEST, "No such path found");
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> dataNotFoundException(Exception e) {
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<CustomHttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        CustomHttpResponse httpResponse = new CustomHttpResponse(
                LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message
        );
        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
