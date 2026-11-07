
package banck.client.kadu.services;

import banck.client.kadu.Repository.AccountRepository;
import banck.client.kadu.Repository.Accounts;
import banck.client.kadu.Repository.User;
import banck.client.kadu.Repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean register(String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        // Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(password);

        // Salva o usuário no banco
        User usuarioSalvo = userRepository.save(new User(email, senhaCriptografada));

        // Cria uma conta vinculada ao usuário
        String accountNumber = String.valueOf(usuarioSalvo.getId());

        Accounts conta = new Accounts(accountNumber, BigDecimal.ZERO);

        conta.setUser(usuarioSalvo);

        accountRepository.save(conta);

        return true;
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email).filter(usuario -> passwordEncoder.matches(password, usuario.getPassword()));
    }
}
