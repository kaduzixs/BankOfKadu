package banck.client.kadu.Controller;

import banck.client.kadu.Repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/me")
    public ResponseEntity<?> minhaConta(HttpSession session) {
        Object userId = session.getAttribute("userId");
        // Usuário não está logado
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }

        Long userIdLong = (Long) userId;

        return accountRepository.findByUserId(userId).<ResponseEntity<?>>map(conta -> ResponseEntity.ok(conta)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada"));
    }
}