package hackathon.dev.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("HELLO");
    }
}
