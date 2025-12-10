package mercado_municipal.mercado_municipal.dto.cnae;

import mercado_municipal.mercado_municipal.entity.Cnae;

public record CnaeListDTO(
    Long id,
    String codigo,
    String descricao
    
) {
    
    public static CnaeListDTO fromEntity(Cnae cnae){

        return new CnaeListDTO(
            cnae.getId(), 
            cnae.getCodigo(),
            cnae.getDescricao()
        );
    }
}
