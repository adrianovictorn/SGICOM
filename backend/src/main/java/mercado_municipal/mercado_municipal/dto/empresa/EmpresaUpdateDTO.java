package mercado_municipal.mercado_municipal.dto.empresa;

import java.util.List;

public record EmpresaUpdateDTO(
    String razaoSocial,
    String cnpj,
    Boolean ativo,
    String email,
    String atividade,
    Integer numeroColaboradores,
    List<Long> cnae,
    List<Long> enderecosIds,
    Long tipoEmpresa,
    Long categoriaEmpresa
) {
    
}
