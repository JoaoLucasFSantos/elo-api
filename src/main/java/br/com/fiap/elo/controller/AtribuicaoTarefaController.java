package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.AtribuicaoTarefaBo;
import br.com.fiap.elo.model.AtribuicaoTarefa;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atribuicoes")
@Tag(name = "Atribuições de tarefas")
public class AtribuicaoTarefaController {

    private final AtribuicaoTarefaBo bo;

    public AtribuicaoTarefaController(AtribuicaoTarefaBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<AtribuicaoTarefa> listar(@RequestParam(required = false) Long idNucleo) {
        return bo.listarPor("ID_NUCLEO", idNucleo);
    }

    @GetMapping("/{id}")
    public AtribuicaoTarefa buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtribuicaoTarefa criar(@Valid @RequestBody AtribuicaoTarefa corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public AtribuicaoTarefa atualizar(@PathVariable Long id, @Valid @RequestBody AtribuicaoTarefa corpo) {
        return bo.atualizar(id, corpo);
    }

    @PutMapping("/{id}/reatribuir/{idParticipanteDestino}")
    public AtribuicaoTarefa reatribuir(@PathVariable Long id, @PathVariable Long idParticipanteDestino) {
        return bo.reatribuir(id, idParticipanteDestino);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
