package mercado_municipal.mercado_municipal.dto.empresa;

import java.util.List;

public record EmpresaCreateDTO(
    String razaoSocial,
    String cnpj,
    String email,
    String atividade,
    Integer numeroColaboradores,
    List<Long> cnae,
    Long tipoEmpresa,
    Long categoriaEmpresa
) {
    
}
