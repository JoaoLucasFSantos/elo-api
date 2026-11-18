package br.com.fiap.elo.controller;

import br.com.fiap.elo.bo.IndicadorBo;
import br.com.fiap.elo.bo.NucleoCuidadoBo;
import br.com.fiap.elo.bo.RedeApoioBo;
import br.com.fiap.elo.dto.IndicadoresNucleo;
import br.com.fiap.elo.dto.SugestaoRedistribuicao;
import br.com.fiap.elo.model.NucleoCuidado;
import br.com.fiap.elo.model.RedeApoio;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nucleos")
@Tag(name = "Núcleos de cuidado")
public class NucleoCuidadoController {

    private final NucleoCuidadoBo bo;
    private final IndicadorBo indicadorBo;
    private final RedeApoioBo redeApoioBo;

    public NucleoCuidadoController(NucleoCuidadoBo bo, IndicadorBo indicadorBo, RedeApoioBo redeApoioBo) {
        this.bo = bo;
        this.indicadorBo = indicadorBo;
        this.redeApoioBo = redeApoioBo;
    }

    @GetMapping
    public List<NucleoCuidado> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public NucleoCuidado buscar(@PathVariable Long id) {
        return bo.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NucleoCuidado criar(@Valid @RequestBody NucleoCuidado corpo) {
        return bo.criar(corpo);
    }

    @PutMapping("/{id}")
    public NucleoCuidado atualizar(@PathVariable Long id, @Valid @RequestBody NucleoCuidado corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        bo.excluir(id);
    }

    @GetMapping("/{id}/indicadores")
    public IndicadoresNucleo indicadores(@PathVariable Long id) {
        return indicadorBo.indicadores(id);
    }

    @GetMapping("/{id}/sugestoes-redistribuicao")
    public List<SugestaoRedistribuicao> sugestoesRedistribuicao(@PathVariable Long id) {
        return indicadorBo.sugerirRedistribuicao(id);
    }

    @GetMapping("/{id}/rede-apoio-sugerida")
    public List<RedeApoio> redeApoioSugerida(@PathVariable Long id) {
        return redeApoioBo.sugerirParaNucleo(id);
    }
}
