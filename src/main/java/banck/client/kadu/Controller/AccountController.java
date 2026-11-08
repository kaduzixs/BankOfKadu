package banck.client.kadu.Controller;

import banck.client.kadu.DTO.AccountResponse;
import banck.client.kadu.Repository.AccountRepository;
import banck.client.kadu.Repository.Accounts;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> minhaConta(HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (!(userId instanceof Long)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está autenticado");
        }

        Long id = (Long) userId;

        return accountRepository.findByUserId(id).<ResponseEntity<?>>map(conta -> {AccountResponse response = new AccountResponse(conta.getId(), conta.getAccountNumber(), conta.getBalance());return ResponseEntity.ok(response);}).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada"));
    }
}