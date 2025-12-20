package mercado_municipal.mercado_municipal.dto.pessoa;

import java.util.List;

import mercado_municipal.mercado_municipal.dto.endereco.EnderecoSimpleViewDTO;
import mercado_municipal.mercado_municipal.entity.Endereco;
import mercado_municipal.mercado_municipal.entity.Pessoa;

public record PessoaViewDTO(
    Long id,
    String nome,
    String cpf,
    String dataDeNascimento,
    String horarioDeCriacao,
    List<EnderecoSimpleViewDTO> enderecos,
    String telefone
) {
    
    public static PessoaViewDTO fromEntity(Pessoa pessoa){
        //List<Long> enderecosId = pessoa.getEnderecos().stream().map(e -> e.getId()).toList();
        List<Endereco> enderecos = pessoa.getEnderecos();
        List<EnderecoSimpleViewDTO> listaViewDTOs = enderecos.stream().map(EnderecoSimpleViewDTO::fromEntity).toList();
        return new PessoaViewDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getCpf(),
            pessoa.getDataDeNascimento().toString(),
            pessoa.getHorarioDeCriacao().toString(),
            //enderecosId,
            listaViewDTOs,
            pessoa.getTelefone()
        );
    }
}
