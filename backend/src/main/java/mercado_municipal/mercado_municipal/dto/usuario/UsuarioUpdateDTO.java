package mercado_municipal.mercado_municipal.dto.usuario;

import mercado_municipal.mercado_municipal.entity.enums.RolesEnum;

public record UsuarioUpdateDTO(
    String nome,
    String senha,
    Boolean ativo,
    RolesEnum funcao
) {
} 
