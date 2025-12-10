package mercado_municipal.mercado_municipal.dto.empresa;

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;

public record EmpresaEnderecoCreateDTO(
    EmpresaCreateDTO empresaCreateDTO,
    EnderecoCreateDTO enderecoCreateDTO
) {
    
}
