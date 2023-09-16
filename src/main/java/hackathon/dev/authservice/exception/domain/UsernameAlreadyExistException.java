package hackathon.dev.authservice.exception.domain;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String message){
        super(message);
    }
}
