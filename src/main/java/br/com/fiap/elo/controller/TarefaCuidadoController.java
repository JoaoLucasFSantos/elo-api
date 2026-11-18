package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.TarefaCuidadoBo;
import br.com.fiap.elo.model.TarefaCuidado;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@Tag(name = "Tarefas de cuidado")
public class TarefaCuidadoController {

    private final TarefaCuidadoBo bo;

    public TarefaCuidadoController(TarefaCuidadoBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<TarefaCuidado> listar(@RequestParam(required = false) Long idNucleo) {
        return bo.listarPor("ID_NUCLEO", idNucleo);
    }

    @GetMapping("/{id}")
    public TarefaCuidado buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaCuidado criar(@Valid @RequestBody TarefaCuidado corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public TarefaCuidado atualizar(@PathVariable Long id, @Valid @RequestBody TarefaCuidado corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
