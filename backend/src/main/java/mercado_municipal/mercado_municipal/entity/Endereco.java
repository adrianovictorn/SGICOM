package mercado_municipal.mercado_municipal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import mercado_municipal.mercado_municipal.entity.enums.LogradouroEnum;

@Entity
@Data
@Table(name = "endereco")
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_do_local", length = 155, nullable = false)
    private String nomeDoLocal;

    @Column(name = "numero", nullable = false, length = 15)
    private String numero;

    @Column(name = "cep", nullable = false, length = 25)
    private String cep; 

    @Column(name = "ponto_referencia", length = 100)
    private String pontoReferencia;


    @Enumerated(EnumType.STRING)
    @Column(name = "logradouro", length = 50)
    private LogradouroEnum logradouro;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "empresa_id"
    )
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "cidade_id"
    )
    private Cidade cidade;

    

}



