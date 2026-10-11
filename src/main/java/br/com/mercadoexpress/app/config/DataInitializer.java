package br.com.mercadoexpress.app.config;

import br.com.mercadoexpress.app.entity.User;
import br.com.mercadoexpress.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria um usuário ADMIN na primeira inicialização, caso ele ainda não exista.
 * E-mail e senha vêm das variáveis de ambiente ADMIN_EMAIL e ADMIN_PASSWORD.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public DataInitializer(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String email = adminEmail.trim().toLowerCase();
        if (repository.findByEmail(email).isPresent()) {
            return;
        }
        User admin = new User();
        admin.setNome("Administrador");
        admin.setEmail(email);
        admin.setSenha(passwordEncoder.encode(adminPassword));
        admin.setRole("ADMIN");
        repository.save(admin);
        System.out.println(">>> Usuário ADMIN criado: " + email);
    }
}