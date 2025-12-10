CREATE TABLE tipo(
  id BIGSERIAL PRIMARY KEY,
  tipo varchar(255) NOT NULL UNIQUE
);

CREATE TABLE categoria(
  id BIGSERIAL PRIMARY KEY,
  categoria varchar(255) NOT NULL UNIQUE
);

CREATE TABLE cidade(
  id BIGSERIAL PRIMARY KEY,
  nome varchar(255) NOT NULL,
  codigo_ibge varchar(10) NOT NULL UNIQUE
  
);

CREATE TABLE empresa(
  id BIGSERIAL PRIMARY KEY,
  razao_social varchar(100) NOT NULL,
  cnae varchar(14) NOT NULL,
  cnpj varchar(18) NOT NULL UNIQUE,
  ativo boolean NOT NULL,
  email varchar(255),
  atividade varchar(255),
  numero_de_colaboradores INTEGER NOT NULL,
  tipo_empresa BIGINT NOT NULL REFERENCES tipo(id) ON DELETE RESTRICT,
  categoria_empresa BIGINT NOT NULL REFERENCES categoria(id) ON DELETE RESTRICT
);

CREATE TABLE endereco(
  id BIGSERIAL PRIMARY KEY,
  nome_do_local varchar(155) NOT NULL,
  numero varchar(15) NOT NULL,
  cep varchar (25) NOT NULL,
  ponto_referencia varchar (100),
  
  logradouro varchar(50),
  cidade_id BIGINT NOT NULL REFERENCES cidade(id) ON delete RESTRICT,
  empresa_id BIGINT NOT NULL REFERENCES empresa(id) ON delete CASCADE
);


CREATE INDEX idx_empresa_tipo on empresa(tipo_empresa);
CREATE INDEX idx_empresa_categoria on empresa(categoria_empresa);
CREATE INDEX idx_empresa_num_colab ON empresa(numero_de_colaboradores);
CREATE INDEX idx_endereco_cidade ON endereco(cidade_id);



