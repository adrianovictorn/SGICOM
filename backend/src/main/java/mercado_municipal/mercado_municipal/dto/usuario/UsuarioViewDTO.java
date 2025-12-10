package mercado_municipal.mercado_municipal.dto.usuario;

import java.time.LocalDateTime;

import mercado_municipal.mercado_municipal.entity.Usuario;

public record UsuarioViewDTO(
    String cpf,
    String nome,
    String senha,
    Boolean ativo,
    String cargo,
    LocalDateTime dataHoraCriacao
) {
    
    public static UsuarioViewDTO fromEntity(Usuario usuario){
        return new UsuarioViewDTO( usuario.getCpf(),
        usuario.getNome(),
        usuario.getSenha(),
        usuario.getAtivo(), 
        usuario.getFuncao().toString(),
        usuario.getDataHoraCriacao());
    }
}
