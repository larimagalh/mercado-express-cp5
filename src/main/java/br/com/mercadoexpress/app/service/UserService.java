
package br.com.mercadoexpress.app.service;

import br.com.mercadoexpress.app.entity.User;
import br.com.mercadoexpress.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void cadastrar(User user) {

        String email = user.getEmail().trim().toLowerCase();

        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                "Este e-mail já está cadastrado."
            );
        }

        user.setEmail(email);
        user.setSenha(
            passwordEncoder.encode(user.getSenha())
        );
        user.setRole("USER");

        repository.save(user);
    }
}
