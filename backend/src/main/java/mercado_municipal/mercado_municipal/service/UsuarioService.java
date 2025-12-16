package mercado_municipal.mercado_municipal.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioCreateDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioListDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioUpdateDTO;
import mercado_municipal.mercado_municipal.dto.usuario.UsuarioViewDTO;
import mercado_municipal.mercado_municipal.entity.Usuario;
import mercado_municipal.mercado_municipal.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder){
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public UsuarioViewDTO criarUsuario(UsuarioCreateDTO usuarioCreateDTO){
        Usuario usuario = new Usuario();
        usuario.setCpf(usuarioCreateDTO.cpf());
        usuario.setNome(usuarioCreateDTO.nome());
        usuario.setSenha(encoder.encode(usuarioCreateDTO.senha()));
        usuario.setAtivo(true);
        usuario.setFuncao(usuarioCreateDTO.funcao());
        
        usuarioRepository.save(usuario);

        return UsuarioViewDTO.fromEntity(usuario);
    }

    public List<UsuarioListDTO> listarUsuariosCadastrados(){
        List<Usuario> listaDeUsuarios = usuarioRepository.findAll();
        List<UsuarioListDTO> listarDTOs = listaDeUsuarios.stream().map(UsuarioListDTO::fromEntity).toList();

        return listarDTOs;
    }


    public UsuarioSimpleViewDTO buscarPorID(Long id){
        Usuario usuarioCadastrado = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("ID não encontrado"));
        return UsuarioSimpleViewDTO.fromEntity(usuarioCadastrado);
    }
    

    public UsuarioSimpleViewDTO buscarPorCPF(String cpf){
        Usuario usuarioCadastrado = usuarioRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Usuário não cadastrado com esse CPF"));
        return UsuarioSimpleViewDTO.fromEntity(usuarioCadastrado);
    }

    public UsuarioSimpleViewDTO atualizarUsuario(UsuarioUpdateDTO dto, Long id){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setFuncao(dto.funcao());
        usuarioExistente.setAtivo(dto.ativo());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuarioExistente.setSenha(encoder.encode(dto.senha()));
        }

        usuarioRepository.save(usuarioExistente);

        return UsuarioSimpleViewDTO.fromEntity(usuarioExistente);
    }

    public UsuarioSimpleViewDTO ativarInvativarUsuario(Long id){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado !"));

        if (usuarioExistente.getAtivo() == true) {
            usuarioExistente.setAtivo(false);
        }
        else{
            usuarioExistente.setAtivo(true);
        }

        usuarioRepository.save(usuarioExistente);
        return UsuarioSimpleViewDTO.fromEntity(usuarioExistente);
    }

    public void deletarUsuario(Long id){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuarioExistente);
    }
}
