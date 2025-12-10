package mercado_municipal.mercado_municipal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.cnae.CnaeCreateDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeListDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeUpdateDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeViewDTO;
import mercado_municipal.mercado_municipal.service.CnaeService;

@RestController
@RequestMapping("/api/cnae")
public class CnaeController {
    

    private final CnaeService cnaeService;

    public CnaeController(CnaeService cnaeService) {
        this.cnaeService = cnaeService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CnaeSimpleViewDTO> cadastrarCnae (@RequestBody CnaeCreateDTO dto){
        return ResponseEntity.ok(cnaeService.criarCnae(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CnaeListDTO>> listarCnaes(){
        return ResponseEntity.ok(cnaeService.listarCnaes());
    }

    @GetMapping("/consultar")
    public ResponseEntity<Page<CnaeSimpleViewDTO>> buscarCnae(
        @RequestParam(required = false) String termo,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam (defaultValue = "10", name = "size")int size
    ){
        return ResponseEntity.ok(cnaeService.buscarAutomizada(termo, size, page));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CnaeViewDTO> atualizarCnae(@RequestBody CnaeUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(cnaeService.atualizarCnae(dto, id));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> deletarCnae(@PathVariable Long id){
        cnaeService.deletarCnae(id);
        return ResponseEntity.noContent().build();
    }
}
