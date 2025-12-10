package mercado_municipal.mercado_municipal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.cidade.CidadeCreateDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeListDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.cidade.CidadeUpdateDTO;
import mercado_municipal.mercado_municipal.service.CidadeService;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
    

    private final CidadeService cidadeService;
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CidadeSimpleViewDTO> cadastrarCidade(@RequestBody CidadeCreateDTO dto){
        return ResponseEntity.ok( cidadeService.cadastrarCidade(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CidadeListDTO>> listarCidades(){
        return ResponseEntity.ok(cidadeService.listarCidades());
    }

    @PutMapping("{id}")
    public ResponseEntity<CidadeSimpleViewDTO> atualizarCidade(@RequestBody CidadeUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(cidadeService.atualizarCidade(id, dto));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarCidade(@PathVariable Long id){
        cidadeService.deletarCidade(id);
        return ResponseEntity.noContent().build();
    }
}
