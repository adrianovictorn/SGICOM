package mercado_municipal.mercado_municipal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mercado_municipal.mercado_municipal.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCpf(String cpf);
}
