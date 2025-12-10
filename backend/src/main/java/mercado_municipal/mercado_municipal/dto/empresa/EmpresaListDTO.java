package mercado_municipal.mercado_municipal.dto.empresa;


import java.util.List;

import mercado_municipal.mercado_municipal.dto.cnae.CnaeListDTO;
import mercado_municipal.mercado_municipal.entity.Cnae;
import mercado_municipal.mercado_municipal.entity.Empresa;

public record EmpresaListDTO(
    String razaoSocial,
    List<CnaeListDTO> cnae,
    String cnpj,
    Boolean ativo,
    Integer enderecos
) {
    
    public static EmpresaListDTO fromEntity(Empresa empresa){

        List<Cnae> cnae = empresa.getCnaes();
        List<CnaeListDTO> listaCNAES = cnae.stream().map(CnaeListDTO::fromEntity).toList();

        return new EmpresaListDTO(
        empresa.getRazaoSocial(),
        listaCNAES,
        empresa.getCnpj(),
        empresa.getAtivo(),
        empresa.getEnderecos().size());
    }
}
