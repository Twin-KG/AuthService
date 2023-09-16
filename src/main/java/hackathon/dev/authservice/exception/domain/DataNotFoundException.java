package hackathon.dev.authservice.exception.domain;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message){
        super(message);
    }
}
