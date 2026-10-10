
package br.com.mercadoexpress.app.service;

import br.com.mercadoexpress.app.entity.Produto;
import br.com.mercadoexpress.app.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Listar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Buscar produto pelo ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Produto não encontrado: " + id));
    }

    // Cadastrar novo produto
    public Produto cadastrar(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    // Atualizar produto existente
    public Produto atualizar(Long id, Produto dados) {
        Produto produto = buscarPorId(id);

        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setQuantidade(dados.getQuantidade());

        return produtoRepository.save(produto);
    }

    // Excluir produto
    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}
