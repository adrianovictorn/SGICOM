package mercado_municipal.mercado_municipal.dto.empresa;

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;

public record EmpresaEnderecoUpdateDTO(
    EmpresaUpdateDTO empresaUpdateDTO,
    EnderecoCreateDTO enderecoCreateDTO
) {
    
}
