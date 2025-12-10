package mercado_municipal.mercado_municipal.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "empresa")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", length = 100, nullable = false)
    private String razaoSocial;


    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "email")
    private String email;

    @Column(name = "atividade")
    private String atividade;

    @Column(name = "numero_de_colaboradores", nullable = false)
    private Integer numeroDeColaboradores;


    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

     @ManyToMany
     @JoinTable(
          name = "empresa_cnae",
          joinColumns = @JoinColumn(name = "empresa_id"),
          inverseJoinColumns = @JoinColumn(name = "cnae_id")
     )
    private List<Cnae> cnaes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_empresa")
    private TipoEmpresa tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_empresa")
    private CategoriaEmpresa categoria;





   public void adicionarEndereco(Endereco endereco){
        enderecos.add(endereco);
        endereco.setEmpresa(this);
   }

    
    



    

}
