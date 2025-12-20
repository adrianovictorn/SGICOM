package mercado_municipal.mercado_municipal.dto.pessoa;

import mercado_municipal.mercado_municipal.entity.Pessoa;

public record PessoaSimpleViewDTO(
    Long id,
    String nome,
    String cpf,
    String dataDeNascimento,
    String horarioDeCriacao,
    String telefone
) {

    public static PessoaSimpleViewDTO fromEntity(Pessoa pessoa){
        return new PessoaSimpleViewDTO(pessoa.getId(),
        pessoa.getNome(),
        pessoa.getCpf(),
        pessoa.getDataDeNascimento().toString(),
        pessoa.getHorarioDeCriacao().toString(),
        pessoa.getTelefone());
    }
}
