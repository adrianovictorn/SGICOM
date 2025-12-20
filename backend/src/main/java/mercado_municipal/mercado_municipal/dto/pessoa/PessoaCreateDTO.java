package mercado_municipal.mercado_municipal.dto.pessoa;

import java.time.LocalDate;

public record PessoaCreateDTO(
    String nome, 
    String cpf, 
    LocalDate dataDeNascimento,
    String telefone
) {
}
