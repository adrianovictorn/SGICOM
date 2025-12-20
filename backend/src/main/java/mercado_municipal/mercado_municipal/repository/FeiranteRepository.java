package mercado_municipal.mercado_municipal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.Feirante;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {
    
}
