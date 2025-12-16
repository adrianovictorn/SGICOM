package mercado_municipal.mercado_municipal.dto.usuario;

import mercado_municipal.mercado_municipal.entity.Usuario;

public record UsuarioListDTO(
    Long id,
    String cpf,
    String nome,
    String funcao,
    Boolean ativo,
    String senha,
    String dataHorarioCriacao
) {

    public static UsuarioListDTO fromEntity(Usuario usuario){
        return new UsuarioListDTO(usuario.getId(), usuario.getCpf(), usuario.getNome(), usuario.getFuncao().toString(), usuario.getAtivo(), usuario.getSenha(), usuario.getDataHoraCriacao().toString());
    }
}
