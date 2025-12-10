package mercado_municipal.mercado_municipal.dto.categoria;

import mercado_municipal.mercado_municipal.entity.CategoriaEmpresa;

public record CategoriaEmpresaListDTO(
    Long id,
    String categoria
) {
    

    public static CategoriaEmpresaListDTO fromEntity(CategoriaEmpresa categoriaEmpresa){
        return new CategoriaEmpresaListDTO(categoriaEmpresa.getId(),categoriaEmpresa.getCategoria());
    }
}
