package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.ParticipanteNucleoBo;
import br.com.fiap.elo.model.ParticipanteNucleo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participantes")
@Tag(name = "Participantes do núcleo")
public class ParticipanteNucleoController {

    private final ParticipanteNucleoBo bo;

    public ParticipanteNucleoController(ParticipanteNucleoBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<ParticipanteNucleo> listar(@RequestParam(required = false) Long idNucleo) {
        return bo.listarPor("ID_NUCLEO", idNucleo);
    }

    @GetMapping("/{id}")
    public ParticipanteNucleo buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipanteNucleo criar(@Valid @RequestBody ParticipanteNucleo corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public ParticipanteNucleo atualizar(@PathVariable Long id, @Valid @RequestBody ParticipanteNucleo corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
