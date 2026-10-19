package br.com.mercadoexpress.app.controller;

import br.com.mercadoexpress.app.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class HomeController {

    private final ProdutoService produtoService;

    public HomeController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/user/home")
    public String home() {
        return "home";
    }

    // Catálogo somente leitura para o perfil USERjkdsg
    @GetMapping("/user/produtos")
    public String catalogo(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos/catalogo";
    }

    // Consulta de um produto pelo código
    @GetMapping("/user/produtos/buscar")
    public String buscar(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("produtos", List.of(produtoService.buscarPorId(id)));
        } catch (NoSuchElementException e) {
            model.addAttribute("produtos", List.of());
            model.addAttribute("erro", "Produto " + id + " não encontrado.");
        }
        return "produtos/catalogo";
    }
}
