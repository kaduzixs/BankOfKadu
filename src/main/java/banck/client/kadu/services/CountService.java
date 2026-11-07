package banck.client.kadu.services;

import banck.client.kadu.Repository.Accounts;
import banck.client.kadu.Repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CountService {

    private final AccountRepository accountRepository;

    public CountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BigDecimal getSaldo(Long userId) {

        Accounts conta = accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        return conta.getBalance();
    }

    public void depositar(Long userId, BigDecimal valor) {

        Accounts conta = accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setBalance(conta.getBalance().add(valor));

        accountRepository.save(conta);
    }

    public boolean sacar(Long userId, BigDecimal valor) {

        Accounts conta = accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor.compareTo(conta.getBalance()) > 0) {
            return false;
        }

        conta.setBalance(conta.getBalance().subtract(valor));

        accountRepository.save(conta);

        return true;
    }
}