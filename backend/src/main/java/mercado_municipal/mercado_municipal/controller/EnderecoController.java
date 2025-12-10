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

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoListDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoUpdateDTO;
import mercado_municipal.mercado_municipal.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {
    

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EnderecoSimpleViewDTO> cadastrarEndereco(@RequestBody EnderecoCreateDTO dto){
        return ResponseEntity.ok(enderecoService.cadastrarEndereco(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EnderecoListDTO>> listarEnderecos(){
        return ResponseEntity.ok(enderecoService.listarEnderecos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoSimpleViewDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EnderecoSimpleViewDTO> atualizarEndereco(@RequestBody EnderecoUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(enderecoService.atualizarEndereco(dto, id));
    }



    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id){
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
