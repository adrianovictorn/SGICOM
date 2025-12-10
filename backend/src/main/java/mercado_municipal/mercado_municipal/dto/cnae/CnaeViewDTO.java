package mercado_municipal.mercado_municipal.dto.cnae;

import mercado_municipal.mercado_municipal.entity.Cnae;

public record CnaeViewDTO(
    Long id,
    String codigo,
    String descricao
    
) {
    
    public static CnaeViewDTO fromEntity(Cnae cnae){

        
        return new CnaeViewDTO(
            cnae.getId(), 
            cnae.getCodigo(),
            cnae.getDescricao()
        );
    }
}
