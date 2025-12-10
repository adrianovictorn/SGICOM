package mercado_municipal.mercado_municipal.dto.endereco;

import mercado_municipal.mercado_municipal.dto.cidade.CidadeSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.entity.enums.LogradouroEnum;

public record EnderecoSimpleViewDTO(
    Long id,
    String nomeLocal,
    String numero,
    String cep,
    LogradouroEnum logradouroEnum,
    String pontoReferencia,
    CidadeSimpleViewDTO cidade
) {



    public static EnderecoSimpleViewDTO fromEntity(Endereco endereco){
       Cidade cidadeExistente = endereco.getCidade();
       
        return new EnderecoSimpleViewDTO(endereco.getId(), endereco.getNomeDoLocal(), endereco.getNumero(), endereco.getCep(), endereco.getLogradouro(),endereco.getPontoReferencia(), CidadeSimpleViewDTO.fromEntity(cidadeExistente));
    }
} 
