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

import mercado_municipal.mercado_municipal.dto.categoria.CategoriaCreateDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaEmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.categoria.CategoriaUpdateDTO;
import mercado_municipal.mercado_municipal.service.CategoriaEmpresaService;

@RestController
@RequestMapping("/api/categoria/empresa")
public class CategoriaEmpresaController {
    
    
    private final CategoriaEmpresaService categoriaEmpresaService;

    public CategoriaEmpresaController(CategoriaEmpresaService categoriaEmpresaService) {
        this.categoriaEmpresaService = categoriaEmpresaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaEmpresaSimpleViewDTO> cadastrarCategoria (@RequestBody CategoriaCreateDTO dto){
        return ResponseEntity.ok(categoriaEmpresaService.cadastrarCategoriaEmpresa(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaEmpresaListDTO>> listarCategorias(){
        return ResponseEntity.ok(categoriaEmpresaService.listarCategoriaEmpresas());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaEmpresaSimpleViewDTO> atualizarCategoria(@RequestBody CategoriaUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(categoriaEmpresaService.atualizarCategoriaEmpresa(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id){
        categoriaEmpresaService.deletarCategoriaEmpresa(id);
        return ResponseEntity.noContent().build();
    }

}
