package mercado_municipal.mercado_municipal.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import mercado_municipal.mercado_municipal.entity.enums.RolesEnum;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", length = 15)
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "senha")
    private String senha;

    @Column(name = "funcao")
    @Enumerated(EnumType.STRING)
    private RolesEnum funcao;

    @Column(name = "ativo" )
    private Boolean ativo;

    @Column(name = "data_hora_criacao")
    @CreationTimestamp
    private LocalDateTime dataHoraCriacao;

}
