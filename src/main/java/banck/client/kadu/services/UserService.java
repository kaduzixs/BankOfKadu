package banck.client.kadu.services;

import banck.client.kadu.Repository.AccountRepository;
import banck.client.kadu.Repository.Accounts;
import banck.client.kadu.Repository.User;
import banck.client.kadu.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository,
    private AccountRepository accountRepository,
    private PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public boolean register(String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        // Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(password);

        User usuarioSalvo = userRepository.save(new User(email, senhaCriptografada));

        String accountNumber = String.valueOf(usuarioSalvo.getId());

        Accounts conta = new Accounts(accountNumber, 0.0);
        conta.setUser(usuarioSalvo);

        accountRepository.save(conta);

        return true;
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email).filter(usuario -> passwordEncoder.matches(password, usuario.getPassword()));
    }
}