package mercado_municipal.mercado_municipal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cnae")
@Data
public class Cnae {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", length = 20, nullable = false, unique = true )
    private String codigo;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

  

}
