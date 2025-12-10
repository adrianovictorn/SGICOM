package mercado_municipal.mercado_municipal.dto.usuario;

import mercado_municipal.mercado_municipal.entity.enums.RolesEnum;

public record UsuarioCreateDTO(
    String cpf,
    String nome,
    String senha,
    RolesEnum funcao

) {
    
}
