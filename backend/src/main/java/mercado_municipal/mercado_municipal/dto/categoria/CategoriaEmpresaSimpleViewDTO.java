package mercado_municipal.mercado_municipal.dto.categoria;

import mercado_municipal.mercado_municipal.entity.CategoriaEmpresa;

public record CategoriaEmpresaSimpleViewDTO(
    Long id,
    String categoria
) {

    public static CategoriaEmpresaSimpleViewDTO fromEntity(CategoriaEmpresa categoriaEmpresa){
        return new CategoriaEmpresaSimpleViewDTO(categoriaEmpresa.getId(),categoriaEmpresa.getCategoria());
    }
}