package mercado_municipal.mercado_municipal.dto.categoria;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.empresa.EmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.CategoriaEmpresa;

public record CategoriaEmpresaViewDTO(
    Long id,
    String categoria,
    List<EmpresaSimpleViewDTO> empresas
) {

    public static CategoriaEmpresaViewDTO fromEntity(CategoriaEmpresa categoriaEmpresa){

        var empresaView = categoriaEmpresa.getEmpresas().stream().map(EmpresaSimpleViewDTO::fromEntity).toList();
        return new CategoriaEmpresaViewDTO(categoriaEmpresa.getId(),categoriaEmpresa.getCategoria(),empresaView);
    }
}