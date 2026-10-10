
package br.com.mercadoexpress.app.repository;

import br.com.mercadoexpress.app.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
