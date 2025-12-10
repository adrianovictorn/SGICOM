package mercado_municipal.mercado_municipal.dto.tipo_empresa;

import mercado_municipal.mercado_municipal.entity.TipoEmpresa;

public record TipoEmpresaListDTO(
    Long id,
    String tipo
) {
    
    public static TipoEmpresaListDTO fromEntity(TipoEmpresa tipoEmpresa){
        return new TipoEmpresaListDTO(tipoEmpresa.getId(), tipoEmpresa.getTipo());
    }
}
