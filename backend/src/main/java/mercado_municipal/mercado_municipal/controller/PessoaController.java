package mercado_municipal.mercado_municipal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.pessoa.PessoaCreateDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaEnderecoCreateDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.pessoa.PessoaViewDTO;
import mercado_municipal.mercado_municipal.service.PessoaService;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PessoaSimpleViewDTO> cadastrarPessoa(@RequestBody PessoaCreateDTO pessoaCreateDTO){
        return ResponseEntity.ok(pessoaService.cadastrarPessoa(pessoaCreateDTO));
    }

    @PostMapping("/cadastrar/completo")
    public ResponseEntity<PessoaViewDTO> cadastrarPessoaCompleto(@RequestBody PessoaEnderecoCreateDTO pessoaEnderecoCreateDTO){
        return ResponseEntity.ok(pessoaService.cadastrarCompleto(pessoaEnderecoCreateDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PessoaSimpleViewDTO>> listarPessoas(
        @RequestParam(defaultValue = "0", name = "page") int page, 
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(pessoaService.listarPessoas(page, size));
    }


    @GetMapping("/buscar/por/cpf")
    public ResponseEntity<PessoaViewDTO> buscarPorCPF(
        @RequestParam String cpf){
        return ResponseEntity.ok(pessoaService.buscarPorCPF(cpf));
    }


    @GetMapping("/buscar/por/nome")
    public ResponseEntity<Page<PessoaSimpleViewDTO>> buscarPorNome(
        @RequestParam(required = false, name = "cachorro") String nome,
        @RequestParam(defaultValue = "0", name = "page") int page, 
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(pessoaService.buscarPorNome(nome, size, page));
    }

    @GetMapping("/buscar/por/nome/cpf")
    public ResponseEntity<Page<PessoaSimpleViewDTO>> buscarPorOuCPF(
        @RequestParam(required = false) String termo,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(pessoaService.buscarPorNomeouCPF(page, size, termo));
    }

    @GetMapping("/buscar/por/cidade")
    public ResponseEntity<Page<PessoaSimpleViewDTO>> buscarPorCidade(
        @RequestParam Long id,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "10", name = "size") int size
    ){
        return ResponseEntity.ok(pessoaService.buscarPorCidadeId(page, size, id));
    }
}
