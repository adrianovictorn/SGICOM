create table Usuarios(
    id BIGSERIAL PRIMARY KEY,
    cpf varchar(15) not null unique,
    nome varchar(70),
    senha varchar(255),
    ativo boolean not null default true,
    data_hora_criacao timestamp default now(),
    funcao varchar(50)
)