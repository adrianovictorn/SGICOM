CREATE TABLE cnae(
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE empresa_cnae(
    empresa_id BIGINT NOT NULL ,
    cnae_id BIGINT NOT NULL,

    PRIMARY KEY(cnae_id, empresa_id),

    CONSTRAINT fk_empresa_cnae_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,
    CONSTRAINT fk_empresa_cnae_cnae FOREIGN KEY (cnae_id) REFERENCES cnae(id) ON DELETE CASCADE
);

ALTER TABLE empresa 
    DROP COLUMN cnae;