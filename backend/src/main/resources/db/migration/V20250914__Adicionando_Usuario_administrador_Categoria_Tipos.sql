
-- Usuário administrador padrão SGICOM, se ainda não existir
INSERT INTO usuarios (cpf, nome, senha, funcao, ativo)
SELECT
    'sgicom_adm'                                        AS cpf,
    'Programador SGICOM'                               AS nome,
    '$2b$12$ETZBVVCKBmDfC0t8se5UEuwsSr26ESY48hMP9H2yVKYS9IE5BfM5O' AS senha, -- SGICOMMASTER (BCrypt)
    'ROLE_ADMIN'                                       AS funcao,
    TRUE                                               AS ativo
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE cpf = 'sgicom_adm'
);


INSERT INTO categoria (categoria)
SELECT 'Comércio'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Comércio');

INSERT INTO categoria (categoria)
SELECT 'Serviços'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Serviços');

INSERT INTO categoria (categoria)
SELECT 'Indústria'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Indústria');

INSERT INTO categoria (categoria)
SELECT 'Agropecuária'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Agropecuária');

INSERT INTO categoria (categoria)
SELECT 'Comércio e Serviços'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Comércio e Serviços');

INSERT INTO categoria (categoria)
SELECT 'Outros'
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE categoria = 'Outros');

-- Tipos jurídicos mais comuns
INSERT INTO tipo (tipo)
SELECT 'MEI - Microempreendedor Individual'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'MEI - Microempreendedor Individual');

INSERT INTO tipo (tipo)
SELECT 'ME - Microempresa'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'ME - Microempresa');

INSERT INTO tipo (tipo)
SELECT 'EPP - Empresa de Pequeno Porte'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'EPP - Empresa de Pequeno Porte');

INSERT INTO tipo (tipo)
SELECT 'LTDA - Sociedade Limitada'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'LTDA - Sociedade Limitada');

INSERT INTO tipo (tipo)
SELECT 'EI - Empresário Individual'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'EI - Empresário Individual');

INSERT INTO tipo (tipo)
SELECT 'SA - Sociedade Anônima'
WHERE NOT EXISTS (SELECT 1 FROM tipo WHERE tipo = 'SA - Sociedade Anônima');
