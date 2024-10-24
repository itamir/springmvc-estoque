package br.ufrn.imd.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufrn.imd.dominio.Item;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    private List<Item> estoque = new ArrayList<>();
    private Long proximoId = 1L;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", estoque);
        return "estoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Item());
        return "estoque/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Item item) {
        item.setId(proximoId++);
        estoque.add(item);
        return "redirect:/estoque";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Item item = estoque.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("item", item);
        return "estoque/formulario";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, @ModelAttribute Item item) {
        Item itemExistente = estoque.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
        if (itemExistente != null) {
            itemExistente.setNome(item.getNome());
            itemExistente.setDescricao(item.getDescricao());
            itemExistente.setQuantidade(item.getQuantidade());
        }
        return "redirect:/estoque";
    }

    @GetMapping("/{id}/remover")
    public String remover(@PathVariable Long id) {
        estoque.removeIf(i -> i.getId().equals(id));
        return "redirect:/estoque";
    }
}