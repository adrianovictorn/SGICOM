package mercado_municipal.mercado_municipal.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class CategoriaEmpresa {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "categoria", length = 255, unique = true, nullable = false)
    private String categoria;

    @OneToMany(mappedBy = "categoria")
    private List<Empresa> empresas = new ArrayList<>();
}
