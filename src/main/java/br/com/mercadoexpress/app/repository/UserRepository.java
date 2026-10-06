package br.com.mercadoexpress.app.repository;

import br.com.mercadoexpress.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}