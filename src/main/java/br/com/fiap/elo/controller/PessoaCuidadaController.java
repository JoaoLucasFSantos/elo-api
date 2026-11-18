package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.PessoaCuidadaBo;
import br.com.fiap.elo.model.PessoaCuidada;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas-cuidadas")
@Tag(name = "Pessoas cuidadas")
public class PessoaCuidadaController {

    private final PessoaCuidadaBo bo;

    public PessoaCuidadaController(PessoaCuidadaBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<PessoaCuidada> listar(@RequestParam(required = false) Long idNucleo) {
        return bo.listarPor("ID_NUCLEO", idNucleo);
    }

    @GetMapping("/{id}")
    public PessoaCuidada buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaCuidada criar(@Valid @RequestBody PessoaCuidada corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public PessoaCuidada atualizar(@PathVariable Long id, @Valid @RequestBody PessoaCuidada corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
