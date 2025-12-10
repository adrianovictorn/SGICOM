package mercado_municipal.mercado_municipal.dto.empresa.indicadores;

public record CategoriaPercentualDTO(
    String categoria,
    Long quantidade,
    Double percentual
) {
}