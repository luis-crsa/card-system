CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS cartoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cpf VARCHAR(11) NOT NULL,
    nome_completo VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    renda_mensal NUMERIC(10,2) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    bandeira VARCHAR(20) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    senha VARCHAR(60),
    motivo_bloqueio VARCHAR(255),
    tipo_de_ocorrencia VARCHAR(5)
    );

CREATE TABLE IF NOT EXISTS lancamentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    numero_cartao VARCHAR(16) NOT NULL,
    data DATE NOT NULL,
    descricao TEXT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    pago BOOLEAN NOT NULL DEFAULT FALSE,
    tipo VARCHAR(20) NOT NULL DEFAULT 'DEBITO'
    );

