package mercado_municipal.mercado_municipal.dto.feirante;

import mercado_municipal.mercado_municipal.dto.pessoa.PessoaViewDTO;
import mercado_municipal.mercado_municipal.entity.Feirante;

public record FeiranteViewDTO(
    Long id,
    PessoaViewDTO pessoa,
    String matricula,
    String banca,
    Boolean haveBox,
    String categoria
) {
    
    public static FeiranteViewDTO fromEntity(Feirante feirante){

        PessoaViewDTO pessoa = PessoaViewDTO.fromEntity(feirante.getPessoa());
        return new FeiranteViewDTO(
            feirante.getId(),
            pessoa,
            feirante.getMatricula(),
            feirante.getBanca(),
            feirante.getBox(),
            feirante.getCategoriaEmpresa().getCategoria()
        );
    }
}
