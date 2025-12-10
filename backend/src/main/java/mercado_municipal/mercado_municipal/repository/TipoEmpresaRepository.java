package mercado_municipal.mercado_municipal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.TipoEmpresa;

public interface TipoEmpresaRepository extends JpaRepository<TipoEmpresa, Long> {
    
}
