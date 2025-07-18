CREATE TABLE profissionais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    experiencia_anos INT,
    formacao VARCHAR(255),
    especializacoes VARCHAR(255),
    valor_hora DECIMAL,
    valor_diaria DECIMAL,
    disponibilidade24h BOOLEAN,
    raio_atendimento_km DECIMAL,
    biografia VARCHAR(255),
    curriculo_url VARCHAR(255),
    nota_media DOUBLE,
    total_avaliacoes INT,
    total_atendimentos INT,
    status_aprovacao VARCHAR(50)
);

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    celular VARCHAR(20),
    tipo_usuario ENUM('cuidador', 'responsavel') NOT NULL,
    foto_perfil VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(14) UNIQUE,
    ativo BOOLEAN DEFAULT TRUE,
    verificado BOOLEAN DEFAULT FALSE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE usuarios
ALTER COLUMN tipo_usuario SET NULL;

