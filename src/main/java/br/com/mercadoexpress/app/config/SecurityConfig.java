
package br.com.mercadoexpress.app.config;

import br.com.mercadoexpress.app.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserRepository repository) {

        return email -> {
            var user = repository.findByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "Usuário não encontrado"));

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getSenha(),
                    List.of(new SimpleGrantedAuthority(
                            "ROLE_" + user.getRole()))
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/login", "/signup",
                    "/css/**", "/js/**"
                ).permitAll()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            
.formLogin(form -> form
    .loginPage("/login")
    .successHandler((request, response, authentication) -> {

        boolean isAdmin = authentication.getAuthorities()
            .stream()
            .anyMatch(authority ->
                authority.getAuthority().equals("ROLE_ADMIN")
            );

        if (isAdmin) {
            response.sendRedirect(
                request.getContextPath() + "/admin/home"
            );
        } else {
            response.sendRedirect(
                request.getContextPath() + "/user/home"
            );
        }
    })
    .permitAll()
)

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
