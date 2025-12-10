package mercado_municipal.mercado_municipal.dto.cidade;

import mercado_municipal.mercado_municipal.entity.Cidade;

public record CidadeListDTO(
    Long id,
    String nome,
    String codigoIbge
) {

    public static CidadeListDTO fromEntity(Cidade cidade){
        return new CidadeListDTO(cidade.getId(), cidade.getNome(),cidade.getCodigoIbge());
    }
} 
