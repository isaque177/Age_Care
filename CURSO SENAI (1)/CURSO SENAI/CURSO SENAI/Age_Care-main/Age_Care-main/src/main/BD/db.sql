CREATE DATABASE IF NOT EXISTS AgeCare;
USE AgeCare;

-- 1. Tabela 'usuarios'
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(11),
    tipo_usuario ENUM('CUIDADOR','RESPONSAVEL') NOT NULL,
    foto_perfil VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(14) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- 2. Tabela 'servicos'
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

-- 3. Tabela 'profissionais' (fix de vírgula)
CREATE TABLE IF NOT EXISTS profissionais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    servico_id BIGINT,
    formacao TEXT,
    biografia TEXT,
    curriculo_url VARCHAR(255),
    nota_media DECIMAL(3,2) DEFAULT 0.00,
    total_avaliacoes INT DEFAULT 0,
    total_atendimentos INT DEFAULT 0,
    status_disponibilidade ENUM('ABERTO','AUSENTE','OCUPADO') DEFAULT 'ABERTO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
);

-- 4. Tabela 'especializacoes' (modelagem N–N via tabela intermediária)
CREATE TABLE IF NOT EXISTS especializacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL
);

-- 5. Tabela intermediária 'profissional_especializacoes'
CREATE TABLE IF NOT EXISTS profissional_especializacoes (
    profissional_id BIGINT NOT NULL,
    especializacao_id BIGINT NOT NULL,
    PRIMARY KEY (profissional_id, especializacao_id),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (especializacao_id) REFERENCES especializacoes(id) ON DELETE CASCADE
);

-- 6. Tabela 'medicamentos'
CREATE TABLE IF NOT EXISTS medicamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS paciente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    responsavel_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo ENUM('MASCULINO','FEMININO'),
    arquivo_pdf_url VARCHAR(255),
    condicoes_medicas TEXT,
    mobilidade ENUM('INDEPENDENTE','ASSISTIDA','CADEIRA_RODAS','ACAMADO'),
    nivel_dependencia ENUM('BAIXO','MEDIO','ALTO'),
    observacoes_especiais TEXT,
    contato_emergencia VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS enderecos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT,
    idoso_id BIGINT,
    profissional_id BIGINT,
    tipo_endereco ENUM('RESIDENCIAL','COMERCIAL','ATENDIMENTO') DEFAULT 'RESIDENCIAL',
    numero VARCHAR(10),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    referencia TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES paciente(id) ON DELETE CASCADE,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    CHECK (
      (usuario_id IS NOT NULL AND idoso_id IS NULL AND profissional_id IS NULL)
      OR (usuario_id IS NULL AND idoso_id IS NOT NULL AND profissional_id IS NULL)
      OR (profissional_id IS NOT NULL AND usuario_id IS NULL AND idoso_id IS NULL)
    )
);

-- 10. Tabela 'agendamentos' (check data_fim > data_inicio)
CREATE TABLE IF NOT EXISTS agendamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    profissional_id BIGINT NOT NULL,
    idoso_id BIGINT NOT NULL,
    responsavel_id BIGINT NOT NULL,
    endereco_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    data_inicio DATETIME NOT NULL,
    data_fim DATETIME NOT NULL,
    status ENUM('AGENDADO','ESPERA','CANCELADO') DEFAULT 'ESPERA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento TIMESTAMP NULL,
    motivo_cancelamento TEXT,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES paciente(id) ON DELETE CASCADE,
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (endereco_id) REFERENCES enderecos(id) ON DELETE CASCADE,
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE,
    CHECK (data_fim > data_inicio)
);

-- 11. Tabela 'pagamentos' (BIGINT + ON DELETE CASCADE)
CREATE TABLE IF NOT EXISTS pagamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    metodo_pagamento ENUM('DINHEIRO','PIX','CARTAO_CREDITO','CARTAO_DEBITO','TRANSFERENCIA') NOT NULL,
    status_pagamento ENUM('PENDENTE','PROCESSANDO','CONFIRMADO','ESTORNADO') DEFAULT 'PENDENTE',
    data_pagamento TIMESTAMP NULL,
    comprovante_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id) ON DELETE CASCADE,
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
);

-- 12. Tabela 'relatorios' (BIGINT + ON DELETE CASCADE)
CREATE TABLE IF NOT EXISTS relatorios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id BIGINT NOT NULL,
    profissional_id BIGINT NOT NULL,
    relatorio TEXT NOT NULL,
    medicamentos_administrados TEXT,
    intercorrencias TEXT,
    observacoes_comportamento TEXT,
    data_relatorio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id) ON DELETE CASCADE,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);

-- Inserts de exemplo para popular o esquema completo



INSERT INTO usuarios (nome, email, senha, telefone, tipo_usuario, foto_perfil, data_nascimento, cpf, created_by, updated_by)
VALUES
('José Silva', 'jose.silva@example.com', 'senha123', '11999990000', 'RESPONSAVEL', 'https://pics.example.com/jose.jpg', '1970-04-15', '123.456.789-00', 'system', 'system'),
('Maria Souza', 'maria.souza@example.com', 'senha456', '11988881111', 'CUIDADOR', 'https://pics.example.com/maria.jpg', '1985-09-20', '987.654.321-00', 'system', 'system');

-- 2. Serviços oferecidos
INSERT INTO servicos (nome, descricao, duracao_minutos, preco, created_by, updated_by)
VALUES
('Cuidados Básicos', 'Higiene pessoal, medição de sinais vitais', 60, 150.00, 'system', 'system'),
('Fisioterapia', 'Sessão de fisioterapia domiciliar', 45, 200.00, 'system', 'system');

-- 3. Profissionais (ligados a um serviço)
INSERT INTO profissionais (servico_id, formacao, biografia, curriculo_url, nota_media, total_avaliacoes, total_atendimentos, status_disponibilidade, created_by, updated_by)
VALUES
(1, 'Enfermeiro(a)', 'Atuante em cuidados geriátricos há 10 anos.', 'https://cv.example.com/enfermeiro.pdf', 4.8, 25, 100, 'ABERTO', 'system', 'system');

-- 4. Especializações
INSERT INTO especializacoes (nome, descricao)
VALUES
('Enfermagem Geriátrica', 'Cuidados '),
('Fisioterapia Neurológica', 'Reabilitação ');

-- 5. Mapeamento N–N entre profissionais e especializações
INSERT INTO profissional_especializacoes (profissional_id, especializacao_id)
VALUES
(1, 1);

-- 6. Medicamentos disponíveis
INSERT INTO medicamentos (nome)
VALUES
('Paracetamol'),
('Dipirona');

-- 7. Pacientes (idosos vinculados a um responsável)
INSERT INTO paciente (responsavel_id, nome, data_nascimento, sexo, arquivo_pdf_url, condicoes_medicas, mobilidade, nivel_dependencia, observacoes_especiais, contato_emergencia, created_by, updated_by)
VALUES
(1, 'Ana Silva', '1945-05-10', 'FEMININO', 'https://docs.example.com/ana.pdf', 'Hipertensão', 'ASSISTIDA', 'MEDIO', 'Uso de andador parcial', '11977772222', 'system', 'system');

-- 8. Endereços (de usuário, paciente e profissional)
INSERT INTO enderecos (usuario_id, bairro, cidade, estado, cep, referencia, created_by, updated_by)
VALUES
(1, 'Centro', 'São Paulo', 'SP', '01001-000', 'Próximo ao metrô', 'system', 'system');

INSERT INTO enderecos (idoso_id, bairro, cidade, estado, cep, referencia, created_by, updated_by)
VALUES
(1, 'Jardim das Flores', 'Guarulhos', 'SP', '07090-000', 'Casa azul', 'system', 'system');

INSERT INTO enderecos (profissional_id, bairro, cidade, estado, cep, referencia, created_by, updated_by)
VALUES
(1, 'Vila Mariana', 'São Paulo', 'SP', '04101-000', 'Prédio A, sala 5', 'system', 'system');

-- 9. Agendamento de consulta
INSERT INTO agendamentos (profissional_id, idoso_id, responsavel_id, endereco_id, servico_id, data_inicio, data_fim, status)
VALUES
(1, 1, 1, 2, 1, '2025-08-01 09:00:00', '2025-08-01 10:00:00', 'AGENDADO');

-- 10. Pagamento da consulta
INSERT INTO pagamentos (agendamento_id, servico_id, valor, metodo_pagamento, status_pagamento, data_pagamento, comprovante_url)
VALUES
(1, 1, 150.00, 'PIX', 'CONFIRMADO', '2025-08-01 10:05:00', 'https://pix.example.com/comprovante/1.png');

-- 11. Relatório pós-consulta
INSERT INTO relatorios (agendamento_id, profissional_id, relatorio, medicamentos_administrados, intercorrencias, observacoes_comportamento)
VALUES
(1, 1, 'Consulta realizada sem intercorrências.', 'Paracetamol, Dipirona', '', '');

INSERT INTO servicos (nome, descricao,  preco) 
VALUES ('Atendimento Padrão', 'Serviço padrão para novos profissionais', 100.00);
