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
