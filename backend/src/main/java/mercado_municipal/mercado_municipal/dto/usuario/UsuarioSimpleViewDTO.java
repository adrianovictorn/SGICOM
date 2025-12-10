package mercado_municipal.mercado_municipal.dto.usuario;

import mercado_municipal.mercado_municipal.entity.Usuario;

public record UsuarioSimpleViewDTO(
    Long id,
    String cpf,
    String nome,
    String funcao,
    Boolean ativo
) {

    public static UsuarioSimpleViewDTO fromEntity(Usuario usuario){
        return new UsuarioSimpleViewDTO(usuario.getId(), usuario.getCpf(), usuario.getNome(), usuario.getFuncao().toString(), usuario.getAtivo());
    }
}
