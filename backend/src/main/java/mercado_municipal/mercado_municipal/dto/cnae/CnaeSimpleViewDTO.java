package mercado_municipal.mercado_municipal.dto.cnae;

import mercado_municipal.mercado_municipal.entity.Cnae;

public record CnaeSimpleViewDTO(
    Long id,
    String codigo,
    String descricao
    
) {
    
    public static CnaeSimpleViewDTO fromEntity(Cnae cnae){

        return new CnaeSimpleViewDTO(
            cnae.getId(), 
            cnae.getCodigo(),
            cnae.getDescricao()
        );
    }
}
