package mercado_municipal.mercado_municipal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mercado_municipal.mercado_municipal.dto.empresa.indicadores.CategoriaQtdProjectionDTO;
import mercado_municipal.mercado_municipal.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

    @Query("SELECT SUM(e.numeroDeColaboradores) FROM Empresa e")
    Long totalColaboradores();

    @Query("SELECT COUNT(e) FROM Empresa e WHERE e.categoria IS NOT NULL")
    Long qtdEmpresasDaCategoria();

    @Query("""
        SELECT c.categoria AS categoria, COUNT(e) AS count
        FROM Empresa e 
        JOIN e.categoria c 
        GROUP BY c.categoria   
        """)
    List<CategoriaQtdProjectionDTO> totalEmpresasComCategoria();
    

    Optional<Empresa> findByCnpj(String cnpj);

    Page<Empresa> findByRazaoSocialContainingIgnoreCase(String razaoSocial, Pageable page);

    Page<Empresa> findDistinctByEnderecosCidadeId(Long id, Pageable page);

    Page<Empresa> findByCnaesId(Long id, Pageable page);

    Page<Empresa> findByCategoriaId(Long categoriaEmpresaId, Pageable page);

    Page<Empresa> findByAtivo(Boolean ativo, Pageable page);
} 
