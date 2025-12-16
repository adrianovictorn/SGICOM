package mercado_municipal.mercado_municipal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mercado_municipal.mercado_municipal.dto.usuario.UsuarioCreateDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioListDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioUpdateDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioViewDTO;
import mercado_municipal.mercado_municipal.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioViewDTO> cadastrarUsuario(@RequestBody UsuarioCreateDTO dto){
        return ResponseEntity.ok(usuarioService.criarUsuario(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioListDTO>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuariosCadastrados());
    }
    
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<UsuarioSimpleViewDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarPorID(id));
    }

    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<UsuarioSimpleViewDTO> buscarPorCpf(@PathVariable String cpf){
        return ResponseEntity.ok(usuarioService.buscarPorCPF(cpf));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioSimpleViewDTO> atualizarUsuario(@RequestBody UsuarioUpdateDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(dto, id));
    }

    @PatchMapping("/ativo/{id}")
    public ResponseEntity<UsuarioSimpleViewDTO> ativarOrInvativar(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.ativarInvativarUsuario(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
