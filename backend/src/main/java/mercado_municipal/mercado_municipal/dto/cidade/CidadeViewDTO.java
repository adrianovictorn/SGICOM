package mercado_municipal.mercado_municipal.dto.cidade;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.Cidade;
import mercado_municipal.mercado_municipal.entity.Endereco;

public record CidadeViewDTO (
    Long id,
    String nome,
    String codigoIbge,
    List<EnderecoSimpleViewDTO> enderecos

){
    public static CidadeViewDTO fromEntity(Cidade cidade){

        List<Endereco> endereco = cidade.getEnderecos();
        List<EnderecoSimpleViewDTO> listaEnderecos = endereco.stream().map(EnderecoSimpleViewDTO::fromEntity).toList(); 

        return new CidadeViewDTO(
            cidade.getId(),
            cidade.getNome(),
            cidade.getCodigoIbge(),
            listaEnderecos
        );
    
    }
}
