package mercado_municipal.mercado_municipal.dto.tipo_empresa;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.empresa.EmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.TipoEmpresa;

public record TipoEmpresaViewDTO(
    Long id,
    String tipo,
    List<EmpresaSimpleViewDTO> empresas
) {
    
    public static TipoEmpresaViewDTO fromEntity(TipoEmpresa tipoEmpresa){

        var empresasView = tipoEmpresa.getEmpresas().stream().map(EmpresaSimpleViewDTO::fromEntity).toList();

        return new TipoEmpresaViewDTO(tipoEmpresa.getId(), tipoEmpresa.getTipo(), empresasView);
    }
}
