package banck.client.kadu.Controller;

import banck.client.kadu.Repository.User;
import banck.client.kadu.services.UserService;

import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpSession session) {

        Optional<User> usuario = userService.login(email, password);

        if (usuario.isPresent()) {
            // Guarda o ID do usuário na sessão
            session.setAttribute("userId", usuario.get().getId());
            return ResponseEntity.ok("Login realizado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){

        boolean registered = userService.register(request.email(), request.password());

        if (registered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
}