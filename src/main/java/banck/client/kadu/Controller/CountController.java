package banck.client.kadu.Controller;

import banck.client.kadu.Request.ValorRequest;
import banck.client.kadu.services.CountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conta")
public class CountController {

    private final CountService conta;

    public CountController(CountService conta) {
        this.conta = conta;
    }

    @GetMapping("/saldo/{userId}")
    public double consultarSaldo(@PathVariable Long userId) {
        return conta.getSaldo(userId);
    }

    @PostMapping("/depositar/{userId}")
    public double depositar(@PathVariable Long userId, @RequestBody ValorRequest body) {

        conta.depositar(userId, body.valor);

        return conta.getSaldo(userId);
    }

    @PostMapping("/sacar/{userId}")
    public ResponseEntity<?> sacar(@PathVariable Long userId, @RequestBody ValorRequest body) {

        boolean ok = conta.sacar(userId, body.valor);

        if (!ok) {
            return ResponseEntity.badRequest().body("Saldo insuficiente");
        }
        return ResponseEntity.ok(conta.getSaldo(userId));
    }
}
