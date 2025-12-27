package mercado_municipal.mercado_municipal.dto.feirante;

import mercado_municipal.mercado_municipal.entity.Feirante;
import mercado_municipal.mercado_municipal.entity.Pessoa;

public record FeiranteSimpleViewDTO(
    Long id,
    String matricula,
    String responsavel,
    String banca,
    Boolean haveBox,
    String categoria
){
    public static FeiranteSimpleViewDTO fromEntity(Feirante feirante){
        Pessoa pessoa = feirante.getPessoa();

            return new FeiranteSimpleViewDTO(
            feirante.getId(),
            feirante.getMatricula(),
            pessoa.getNome(),
            feirante.getBanca(),
            feirante.getBox(),
            feirante.getCategoriaEmpresa().getCategoria()
        );
    }
    
}
