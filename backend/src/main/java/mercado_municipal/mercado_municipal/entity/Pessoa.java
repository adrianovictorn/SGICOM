package mercado_municipal.mercado_municipal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pessoa")
@Data
public class Pessoa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "cpf", length = 20, nullable = false)
    private String cpf;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @CreationTimestamp
    @Column(name = "horario_de_criacao", updatable = false)
    private LocalDateTime horarioDeCriacao;

    @OneToMany(
        mappedBy = "pessoa",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Endereco> enderecos = new ArrayList<>();

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;

    public void addEndereco(Endereco endereco){
        enderecos.add(endereco);
        endereco.setPessoa(this);
    }
}
