package mercado_municipal.mercado_municipal.dto.endereco;

import mercado_municipal.mercado_municipal.dto.cidade.CidadeSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.entity.enums.LogradouroEnum;

public record EnderecoListDTO(
    Long id,
    String nomeLocal,
    String numero,
    String cep,
    String pontoReferencia,
    LogradouroEnum logradouroEnum,
    CidadeSimpleViewDTO cidade
) {
    public static EnderecoListDTO fromEntity(Endereco endereco){
            Cidade enderecoCidade = endereco.getCidade();
            CidadeSimpleViewDTO cidadeView =  CidadeSimpleViewDTO.fromEntity(enderecoCidade);
            return new EnderecoListDTO(endereco.getId(), endereco.getNomeDoLocal(), endereco.getNumero(), endereco.getCep(), endereco.getPontoReferencia(), endereco.getLogradouro(), cidadeView);
     }

}