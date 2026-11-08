
package banck.client.kadu.Controller;

import banck.client.kadu.Request.ValorRequest;
import banck.client.kadu.services.CountService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/conta")
public class CountController {

    private final CountService conta;

    public CountController(CountService conta) {
        this.conta = conta;
    }

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }

        return ResponseEntity.ok(conta.getSaldo((Long) userId));
    }

    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@Valid @RequestBody ValorRequest request, HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não está logado");
        }

        if (request == null || !valorValido(request.valor())) {
            return ResponseEntity.badRequest()
                    .body("Informe um valor maior ou igual a 0,01");
        }

        conta.depositar((Long) userId, request.valor());

        return ResponseEntity.ok(conta.getSaldo((Long) userId));
    }

    @PostMapping("/sacar")
    public ResponseEntity<?> sacar(@Valid @RequestBody(required = false) ValorRequest request, HttpSession session) {

        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado");
        }

        if (request == null || !valorValido(request.valor())) {
            return ResponseEntity.badRequest().body("Informe um valor maior ou igual a 0,01");
        }

        boolean sucesso = conta.sacar((Long) userId, request.valor());

        if (!sucesso) {
            return ResponseEntity.badRequest().body("Saldo insuficiente");
        }

        return ResponseEntity.ok(conta.getSaldo((Long) userId));
    }

    private boolean valorValido(BigDecimal valor) {
        return valor != null && valor.compareTo(new BigDecimal("0.01")) >= 0;
    }
}
