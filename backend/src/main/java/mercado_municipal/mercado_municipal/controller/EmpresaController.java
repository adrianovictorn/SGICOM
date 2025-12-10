package mercado_municipal.mercado_municipal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.empresa.EmpresaEnderecoCreateDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaEnderecoUpdateDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaListDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.empresa.EmpresaViewDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.CategoriaPercentualDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.EmpresaColaboradoresSUMDTO;
import mercado_municipal.mercado_municipal.dto.empresa.indicadores.EmpresaCountDTO;
import mercado_municipal.mercado_municipal.service.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EmpresaSimpleViewDTO> cadastrarEmpresa(@RequestBody EmpresaEnderecoCreateDTO dto){
        var payload = dto;
        return ResponseEntity.ok(empresaService.cadastrarEmpresa(payload.empresaCreateDTO(), payload.enderecoCreateDTO()));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EmpresaListDTO>> listarEmpresas(){
        return ResponseEntity.ok(empresaService.listarEmpresa());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EmpresaViewDTO> atualizarEmpresa(@RequestBody EmpresaEnderecoUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(empresaService.atualizarEmpresa(id, dto.empresaUpdateDTO(), dto.enderecoCreateDTO()));

    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id){
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }


    // Dados 

    @GetMapping("/contagem")
    public ResponseEntity<EmpresaCountDTO> totalEmpresas(){
        return ResponseEntity.ok(empresaService.qtdEmpresas());
    }

    @GetMapping("/contagem/colaboradores")
    public ResponseEntity<EmpresaColaboradoresSUMDTO> totalColaboradores(){
        return ResponseEntity.ok(empresaService.qtdColaboradores());
    }

    @GetMapping("/porcentagem/categoria")
    public ResponseEntity<List<CategoriaPercentualDTO>> categoriaPercentual(){
        return ResponseEntity.ok(empresaService.porcentagemCategorias());
    }

    @GetMapping("/consultar")
    public ResponseEntity<Page<EmpresaSimpleViewDTO>> buscarAutomatizada(
        @RequestParam(required =  false) String cnpj,
        @RequestParam(required = false) String razaoSocial,
        @RequestParam(required = false) Long cnae,
        @RequestParam(required = false) Long cidadeId, 
        @RequestParam(required = false) Long categoria, 
        @RequestParam(defaultValue = "0", name = "page") int page, 
        @RequestParam(defaultValue = "10", name = "size") int size){

        Page <EmpresaSimpleViewDTO> resultado = empresaService.buscarAutomatizada(razaoSocial, cnae, cnpj, cidadeId, page, size, categoria);


        return ResponseEntity.ok(resultado);
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<EmpresaViewDTO> buscarEmpresaPorId(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.buscarEmpresaPorId(id));
    }
    @PatchMapping("/desativar/{id}")
    public ResponseEntity<EmpresaSimpleViewDTO> inativarEmpresa(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.desativarEmpresa(id));
    }

}
