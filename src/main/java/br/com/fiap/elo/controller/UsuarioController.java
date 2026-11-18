package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.UsuarioBo;
import br.com.fiap.elo.model.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

    private final UsuarioBo bo;

    public UsuarioController(UsuarioBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@Valid @RequestBody Usuario corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @Valid @RequestBody Usuario corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
