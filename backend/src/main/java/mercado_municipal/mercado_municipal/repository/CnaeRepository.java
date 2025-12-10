package mercado_municipal.mercado_municipal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.Cnae;

public interface CnaeRepository extends JpaRepository<Cnae, Long>{
    

    Page<Cnae> findByCodigoContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String codigo, String termo,  Pageable page);

}
