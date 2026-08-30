package banck.client.kadu.Controller;

import banck.client.kadu.Repository.User;
import banck.client.kadu.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        Optional<User> usuario = userService.login(email, password);

        if (usuario.isPresent()) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password) {

        boolean registered = userService.register(email, password);
        if (registered) {
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
    }
}