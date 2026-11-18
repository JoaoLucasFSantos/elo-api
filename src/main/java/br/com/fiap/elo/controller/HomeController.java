package br.com.fiap.elo.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Hidden
@RestController
public class HomeController {
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of("projeto", "Elo - Cuidando de Quem Cuida",
                "status", "online",
                "documentacao", "/swagger-ui.html");
    }
}
