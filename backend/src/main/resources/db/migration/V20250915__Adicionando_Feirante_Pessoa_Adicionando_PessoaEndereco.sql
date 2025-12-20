
CREATE table pessoa(
  	id BIGSERIAL PRIMARY KEY,
  	nome VARCHAR(255) NOT NULL,
  	cpf VARCHAR(20) NOT NULL,
  	data_de_nascimento date NOT NULL,
  	horario_de_criacao timestamp,
  	telefone VARCHAR(20) NOT NULL
  );

  CREATE TABLE feirante(
  	id BIGSERIAL PRIMARY KEY,
  	pessoa_id BIGINT NOT NULL,
  	matricula VARCHAR(10) NOT NULL unique,
  	banca VARCHAR(10) NOT NULL,
  	box boolean NOT NULL,
  	categoria_id BIGINT NOT NULL,
  
  	FOREIGN KEY (categoria_id) REFERENCES categoria(id),
  	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
  );

  
ALTER TABLE endereco
    ADD COLUMN 
        pessoa_id BIGINT;

ALTER TABLE endereco
    ADD CONSTRAINT fk_endereco_pessoa
        FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);

ALTER TABLE endereco
    ADD CONSTRAINT chk_endereco_dono
    CHECK (
        (pessoa_id IS NOT NULL AND empresa_id IS NULL)
        OR
        (pessoa_id IS NULL AND empresa_id IS NOT NULL)
    );