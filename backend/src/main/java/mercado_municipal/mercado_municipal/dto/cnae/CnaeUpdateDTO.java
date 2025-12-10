package mercado_municipal.mercado_municipal.dto.cnae;



public record CnaeUpdateDTO(
    String codigo,
    String descricao,
    Long empresaId
) {
    
}
