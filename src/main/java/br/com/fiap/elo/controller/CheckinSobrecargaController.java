package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.CheckinSobrecargaBo;
import br.com.fiap.elo.model.CheckinSobrecarga;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
@Tag(name = "Check-ins de sobrecarga")
public class CheckinSobrecargaController {

    private final CheckinSobrecargaBo bo;

    public CheckinSobrecargaController(CheckinSobrecargaBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<CheckinSobrecarga> listar(@RequestParam(required = false) Long idParticipante) {
        return bo.listarPor("ID_PARTICIPANTE", idParticipante);
    }

    @GetMapping("/{id}")
    public CheckinSobrecarga buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinSobrecarga criar(@Valid @RequestBody CheckinSobrecarga corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public CheckinSobrecarga atualizar(@PathVariable Long id, @Valid @RequestBody CheckinSobrecarga corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }
}
