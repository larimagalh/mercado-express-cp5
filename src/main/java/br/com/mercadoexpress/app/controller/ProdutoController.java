
package br.com.mercadoexpress.app.controller;

import br.com.mercadoexpress.app.entity.Produto;
import br.com.mercadoexpress.app.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Exibir lista de produtos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos/lista";
    }

    // Exibir formulário de cadastro
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario";
    }

    // Salvar novo produto
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        produtoService.cadastrar(produto);
        return "redirect:/admin/produtos";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        return "produtos/formulario";
    }

    // Atualizar produto existente
    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @ModelAttribute Produto produto) {

        produtoService.atualizar(id, produto);
        return "redirect:/admin/produtos";
    }

    // Excluir produto
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return "redirect:/admin/produtos";
    }
}
