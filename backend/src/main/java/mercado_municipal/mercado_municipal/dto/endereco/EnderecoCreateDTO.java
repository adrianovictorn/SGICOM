package mercado_municipal.mercado_municipal.dto.endereco;

import mercado_municipal.mercado_municipal.entity.enums.LogradouroEnum;

public record EnderecoCreateDTO(
    
    String nomeLocal,
    String numero,
    String cep,
    String pontoReferencia,
    LogradouroEnum logradouroEnum,
    Long cidadeId
) {
    
}
