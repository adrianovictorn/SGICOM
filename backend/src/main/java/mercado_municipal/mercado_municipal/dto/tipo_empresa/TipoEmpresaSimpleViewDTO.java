package mercado_municipal.mercado_municipal.dto.tipo_empresa;

import mercado_municipal.mercado_municipal.entity.TipoEmpresa;

public record TipoEmpresaSimpleViewDTO(
    Long id,
    String tipo
) {
    
    public static TipoEmpresaSimpleViewDTO fromEntity(TipoEmpresa tipoEmpresa){
        return new TipoEmpresaSimpleViewDTO(tipoEmpresa.getId(), tipoEmpresa.getTipo());
    }
}
