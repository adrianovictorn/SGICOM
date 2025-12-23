package mercado_municipal.mercado_municipal.dto.feirante;

import mercado_municipal.mercado_municipal.entity.Feirante;

public record FeiranteSimpleViewDTO(
    Long id,
    String matricula,
    String banca,
    Boolean haveBox,
    String categoria
){
    public static FeiranteSimpleViewDTO fromEntity(Feirante feirante){
            return new FeiranteSimpleViewDTO(
            feirante.getId(),
            feirante.getMatricula(),
            feirante.getBanca(),
            feirante.getBox(),
            feirante.getCategoriaEmpresa().getCategoria()
        );
    }
    
}
