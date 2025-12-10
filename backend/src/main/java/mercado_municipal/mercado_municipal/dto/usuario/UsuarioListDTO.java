package mercado_municipal.mercado_municipal.dto.usuario;

import mercado_municipal.mercado_municipal.entity.Usuario;

public record UsuarioListDTO(
    String cpf,
    String nome,
    String funcao,
    Boolean ativo
) {

    public static UsuarioListDTO fromEntity(Usuario usuario){
        return new UsuarioListDTO(usuario.getCpf(), usuario.getNome(), usuario.getFuncao().toString(), usuario.getAtivo());
    }
}
