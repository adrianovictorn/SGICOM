package mercado_municipal.mercado_municipal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.feirante.FeiranteCreateDTO;
import mercado_municipal.mercado_municipal.dto.feirante.FeiranteViewDTO;
import mercado_municipal.mercado_municipal.service.FeiranteService;

@RestController
@RequestMapping("/api/feirante")
public class FeiranteController {
    
    private final FeiranteService feiranteService;

    public FeiranteController(FeiranteService feiranteService) {
        this.feiranteService = feiranteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FeiranteViewDTO> cadastrarFeirante(@RequestBody FeiranteCreateDTO feiranteCreateDTO){
        return ResponseEntity.ok(feiranteService.cadastrarFeirante(feiranteCreateDTO));
    }
}
