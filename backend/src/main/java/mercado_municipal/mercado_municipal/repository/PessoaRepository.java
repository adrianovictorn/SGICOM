package mercado_municipal.mercado_municipal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    Optional<Pessoa> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    Page<Pessoa> findAll(Pageable pageable);

    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Pessoa> findByNomeContainingIgnoreCaseOrCpfContaining(String nome, String cpf, Pageable pageable);
}
