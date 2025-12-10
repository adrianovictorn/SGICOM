package mercado_municipal.mercado_municipal.dto.empresa;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.categoria.CategoriaEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.cnae.CnaeListDTO;
import mercado_municipal.mercado_municipal.dto.endereco.EnderecoSimpleViewDTO;
import mercado_municipal.mercado_municipal.dto.tipo_empresa.TipoEmpresaSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.Cnae;
import mercado_municipal.mercado_municipal.entity.Empresa;

public record EmpresaViewDTO(
    Long id,
    String razaoSocial,
    List<CnaeListDTO> cnae,
    String cnpj,
    Boolean ativo,
    String email,
    String atividade,
    Integer numeroColaboradores,
    List<EnderecoSimpleViewDTO> enderecos,
    TipoEmpresaSimpleViewDTO tipoEmpresa,
    CategoriaEmpresaSimpleViewDTO categoriaEmpresa
) {
   
    public static EmpresaViewDTO fromEntity(Empresa empresa){
        
        
        var listarEnderecos = empresa.getEnderecos().stream().map(EnderecoSimpleViewDTO::fromEntity).toList();

        var categoriaEmpresa = empresa.getCategoria();
        var viewCategoria = categoriaEmpresa != null ? CategoriaEmpresaSimpleViewDTO.fromEntity(categoriaEmpresa): null;

        var tipoEmpresa = empresa.getTipo();
        var viewTipo =  tipoEmpresa != null ? TipoEmpresaSimpleViewDTO.fromEntity(tipoEmpresa) : null;

        List<Cnae> cnaes = empresa.getCnaes();
        List<CnaeListDTO> listaCnae = cnaes.stream().map(CnaeListDTO::fromEntity).toList();
        return new EmpresaViewDTO(
        empresa.getId(),
        empresa.getRazaoSocial(),
        listaCnae, 
        empresa.getCnpj(),
        empresa.getAtivo(), 
        empresa.getEmail(),
        empresa.getAtividade(), 
        empresa.getNumeroDeColaboradores(),
        listarEnderecos, 
        viewTipo, 
        viewCategoria
        );
    }
}
