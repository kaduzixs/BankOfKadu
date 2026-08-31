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

    public double getSaldo(Long userId) {

        Accounts conta = accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        return conta.getBalance();
    }

    public void depositar(Long userId, double valor) {

        Accounts conta = accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setBalance(conta.getBalance() + valor);

        accountRepository.save(conta);
    }

    public boolean sacar(Long userId, double valor) {

        Accounts conta = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor > conta.getBalance()) {
            return false;
        }

        conta.setBalance(conta.getBalance() - valor);

        accountRepository.save(conta);

        return true;
    }
}