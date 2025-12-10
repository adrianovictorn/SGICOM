package mercado_municipal.mercado_municipal.dto.empresa;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.cnae.CnaeListDTO;
import mercado_municipal.mercado_municipal.entity.Cnae;
import mercado_municipal.mercado_municipal.entity.Empresa;

public record EmpresaSimpleViewDTO(
    Long id,
    String razaoSocial,
    List<CnaeListDTO> cnae,
    String cnpj,
    Boolean ativo
) {
 
    
    public static EmpresaSimpleViewDTO fromEntity(Empresa empresa){
        List<Cnae> cnaes = empresa.getCnaes();
        List<CnaeListDTO> listaCnae = cnaes.stream().map(CnaeListDTO::fromEntity).toList();

        return new EmpresaSimpleViewDTO(
        empresa.getId(),
        empresa.getRazaoSocial(),
        listaCnae,
        empresa.getCnpj(),
        empresa.getAtivo());
    }
    
    
}
