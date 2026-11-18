package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.RedeApoioBo;
import br.com.fiap.elo.model.RedeApoio;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rede-apoio")
@Tag(name = "Rede de apoio")
public class RedeApoioController {

    private final RedeApoioBo bo;

    public RedeApoioController(RedeApoioBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<RedeApoio> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public RedeApoio buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RedeApoio criar(@Valid @RequestBody RedeApoio corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public RedeApoio atualizar(@PathVariable Long id, @Valid @RequestBody RedeApoio corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
