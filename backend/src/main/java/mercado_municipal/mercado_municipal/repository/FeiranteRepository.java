package mercado_municipal.mercado_municipal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.Feirante;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {
    
    Page<Feirante> findAll(Pageable pageable);
    Page<Feirante> findByMatriculaContainingIgnoreCase(Pageable pageable, String matricula);
    Page<Feirante> findByBox(Pageable pageable, Boolean box);
    Page<Feirante> findByCategoriaEmpresa(Pageable pageable, String categoria);
    Page<Feirante> findByBancaContainingIgnoreCase(Pageable pageable, String banca);
    Page<Feirante> findByMatriculaContainingIgnoreCaseOrCategoriaEmpresa_CategoriaOrBancaContainingIgnoreCase(String matricula, String categoria, String Banca, Pageable pageable);

}
