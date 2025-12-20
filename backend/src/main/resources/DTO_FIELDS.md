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
    private List<Endereco> enderecos;

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;
