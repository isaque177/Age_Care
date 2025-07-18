CREATE DATABASE IF NOT EXISTS AgeCare;
USE AgeCare;

-- Criação da tabela 'usuarios'
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(11) NOT NULL,
    telefone VARCHAR(11),
    tipo_usuario ENUM('CUIDADOR', 'RESPONSAVEL') NOT NULL, -- Corrigido sem acento para bater com INSERT
    foto_perfil VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(14) UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Criação da tabela 'profissionais'
CREATE TABLE IF NOT EXISTS profissionais (
    id BIGINT PRIMARY KEY,                        
    formacao TEXT,                                 
    biografia TEXT,                                
    curriculo_url VARCHAR(255),                    
    nota_media DECIMAL(3,2) DEFAULT 0.00,         
    total_avaliacoes INT DEFAULT 0,                
    total_atendimentos INT DEFAULT 0,              
    status_disponibilidade ENUM('ABERTO', 'AUSENTE', 'OCUPADO') DEFAULT 'ABERTO',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Criação da tabela 'especializacoes'
CREATE TABLE IF NOT EXISTS especializacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    profissional_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(40) NOT NULL,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);

-- Tabela intermediária de especializações
CREATE TABLE IF NOT EXISTS profissional_especializacoes (
    profissional_id BIGINT,
    especializacao_id BIGINT,
    PRIMARY KEY (profissional_id, especializacao_id),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id),
    FOREIGN KEY (especializacao_id) REFERENCES especializacoes(id)
);

-- Criação da tabela 'idosos'
CREATE TABLE IF NOT EXISTS idosos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    responsavel_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo ENUM('MASCULINO', 'FEMININO'),
    arquivo_pdf_url VARCHAR(255),
    condicoes_medicas TEXT,
    medicamentos TEXT,
    mobilidade ENUM('INDEPEDENTE', 'ASSISTIDA', 'CADEIRA_RODAS', 'ACAMADO'),
    nivel_dependencia ENUM('BAIXO', 'MEDIO', 'ALTO'),
    observacoes_especiais TEXT,
    contato_emergencia VARCHAR(100),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id) ON DELETE CASCADE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Criação da tabela 'enderecos'
CREATE TABLE IF NOT EXISTS enderecos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    idoso_id BIGINT,
    profissional_id BIGINT,
    tipo_endereco ENUM('RESIDENCIAL', 'COMERCIAL', 'ATENDIMENTO') DEFAULT 'RESIDENCIAL',
    numero VARCHAR(10),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    referencia TEXT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES idosos(id) ON DELETE CASCADE,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    CHECK ((usuario_id IS NOT NULL AND idoso_id IS NULL) OR (usuario_id IS NULL AND idoso_id IS NOT NULL)),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Criação da tabela 'servicos'
CREATE TABLE IF NOT EXISTS servicos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    duracao_minutos INT,
    preco DECIMAL(10,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Criação da tabela 'agendamentos'
CREATE TABLE IF NOT EXISTS agendamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profissional_id BIGINT NOT NULL,
    idoso_id BIGINT NOT NULL,
    responsavel_id BIGINT NOT NULL,
    endereco_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    data_inicio DATETIME NOT NULL,
    data_fim DATETIME NOT NULL,
    status ENUM('AGENDADO', 'ESPERA', 'CANCELADO') DEFAULT 'ESPERA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento TIMESTAMP NULL,
    motivo_cancelamento TEXT,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id),
    FOREIGN KEY (idoso_id) REFERENCES idosos(id),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id),
    FOREIGN KEY (endereco_id) REFERENCES enderecos(id),
    FOREIGN KEY (servico_id) REFERENCES servicos(id)
);

-- Criação da tabela 'pagamentos'
CREATE TABLE IF NOT EXISTS pagamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    metodo_pagamento ENUM('DINHEIRO', 'PIX', 'CARTAO_CREDITO', 'CARTAO_DEBITO', 'TRANSFERENCIA') NOT NULL,
    status_pagamento ENUM('PENDENTE', 'PROCESSANDO', 'CONFIRMADO', 'ESTORNADO') DEFAULT 'PENDENTE',
    data_pagamento TIMESTAMP NULL,
    comprovante_url VARCHAR(255),
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
);

-- Criação da tabela 'relatorios'
CREATE TABLE IF NOT EXISTS relatorios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id BIGINT NOT NULL,
    profissional_id BIGINT NOT NULL,
    relatorio TEXT NOT NULL,
    medicamentos_administrados TEXT,
    intercorrencias TEXT,
    observacoes_comportamento TEXT,
    data_relatorio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id)
);

-- Criação da tabela 'medicamentos' (completada para evitar erro)
CREATE TABLE IF NOT EXISTS medicamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

-- =============================================
-- ============ INSERÇÕES DE TESTE =============
-- =============================================

INSERT INTO usuarios (nome, email, senha, telefone, tipo_usuario, foto_perfil, data_nascimento, cpf, created_by)
VALUES 
('João Silva', 'joao@email.com', '12345678901', '11999998888', 'cuidador', NULL, '1990-04-15', '123.456.789-00', 'admin'),
('Maria Oliveira', 'maria@email.com', 'senha123456', '11988887777', 'responsavel', NULL, '1985-10-05', '987.654.321-00', 'admin');

INSERT INTO profissionais (id, formacao, biografia, curriculo_url, created_by)
VALUES 
(1, 'Enfermagem', 'Cuidador com 10 anos de experiência.', NULL, 'admin');

INSERT INTO especializacoes (profissional_id, nome, descricao)
VALUES 
(1, 'Geriatria', 'Especialista em cuidado com idosos');

INSERT INTO profissional_especializacoes (profissional_id, especializacao_id)
VALUES (1, 1);

INSERT INTO idosos (responsavel_id, nome, data_nascimento, sexo, mobilidade, nivel_dependencia, created_by)
VALUES 
(2, 'Dona Clotilde', '1942-03-20', 'feminino', 'assistida', 'medio', 'admin');

INSERT INTO enderecos (usuario_id, tipo_endereco, numero, bairro, cidade, estado, cep, referencia, created_by)
VALUES 
(2, 'residencial', '123', 'Centro', 'São Paulo', 'SP', '01000-000', 'Próximo ao mercado', 'admin');

INSERT INTO servicos (nome, descricao, duracao_minutos, preco, created_by)
VALUES 
('Acompanhamento diário', 'Visita para auxiliar nas atividades diárias', 60, 120.00, 'admin');

INSERT INTO agendamentos (profissional_id, idoso_id, responsavel_id, endereco_id, servico_id, data_inicio, data_fim)
VALUES 
(1, 1, 2, 1, 1, '2025-07-21 09:00:00', '2025-07-21 10:00:00');

INSERT INTO pagamentos (agendamento_id, servico_id, valor, metodo_pagamento, status_pagamento, data_pagamento, comprovante_url)
VALUES 
(1, 1, 120.00, 'pix', 'confirmado', CURRENT_TIMESTAMP, NULL);

INSERT INTO relatorios (agendamento_id, profissional_id, relatorio, medicamentos_administrados, observacoes_comportamento)
VALUES 
(1, 1, 'Atendimento realizado com sucesso.', 'Dipirona 500mg', 'Paciente tranquila durante todo o atendimento');

INSERT INTO medicamentos (nome)
VALUES 
('Dipirona'), ('Losartana'), ('Omeprazol');

