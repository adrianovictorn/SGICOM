package mercado_municipal.mercado_municipal.dto.feirante;


import mercado_municipal.mercado_municipal.dto.pessoa.PessoaEnderecoCreateDTO;

public record FeiranteCreateDTO(
    String matricula,
    String banca,
    Boolean box,
    Long categoriaId,

    PessoaEnderecoCreateDTO pessoaEndereco
) {
    
}
