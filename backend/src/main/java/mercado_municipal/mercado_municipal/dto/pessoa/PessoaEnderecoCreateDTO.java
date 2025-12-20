package mercado_municipal.mercado_municipal.dto.pessoa;

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoCreateDTO;

public record PessoaEnderecoCreateDTO(
    PessoaCreateDTO pessoaCreateDTO, EnderecoCreateDTO enderecoCreateDTO
) {
    
}
