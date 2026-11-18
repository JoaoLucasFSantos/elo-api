package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.EventoAgendaBo;
import br.com.fiap.elo.model.EventoAgenda;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Agenda de eventos")
public class EventoAgendaController {

    private final EventoAgendaBo bo;

    public EventoAgendaController(EventoAgendaBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<EventoAgenda> listar(@RequestParam(required = false) Long idNucleo) {
        return bo.listarPor("ID_NUCLEO", idNucleo);
    }

    @GetMapping("/{id}")
    public EventoAgenda buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoAgenda criar(@Valid @RequestBody EventoAgenda corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public EventoAgenda atualizar(@PathVariable Long id, @Valid @RequestBody EventoAgenda corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
