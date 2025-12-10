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

import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaCreateDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaUpdateDTO;
import mercado_municipal.mercado_municipal.service.TipoEmpresaService;

@RestController
@RequestMapping("/api/tipo/empresa")
public class TipoEmpresaController {

    private final TipoEmpresaService tipoEmpresaService;

    public TipoEmpresaController(TipoEmpresaService tipoEmpresaService){
        this.tipoEmpresaService = tipoEmpresaService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<TipoEmpresaSimpleViewDTO> cadastrarTipo(@RequestBody TipoEmpresaCreateDTO dto){
        return ResponseEntity.ok(tipoEmpresaService.cadastrarTipoEmpresa(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TipoEmpresaListDTO>> listarTipos(){
        return ResponseEntity.ok(tipoEmpresaService.listarTipoEmpresa());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TipoEmpresaSimpleViewDTO> atualizarEmpresa(@RequestBody TipoEmpresaUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(tipoEmpresaService.atualizarTipoEmpresa(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id){
        tipoEmpresaService.deletarTipoEmpresa(id);
        return ResponseEntity.noContent().build();
    }


    
}
