package mercado_municipal.mercado_municipal.dto.cidade;

import mercado_municipal.mercado_municipal.entity.Cidade;

public record CidadeSimpleViewDTO(
    Long id,
    String nome,
    String codigoIbge
) {
 
    
    public static CidadeSimpleViewDTO fromEntity(Cidade cidade){
        return new CidadeSimpleViewDTO(cidade.getId(),
        cidade.getNome(),
        cidade.getCodigoIbge());
    }
}
