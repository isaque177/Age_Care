DROP DATABASE IF EXISTS AgeCare;
CREATE DATABASE AgeCare;
USE AgeCare;

-- 1. usuários
CREATE TABLE usuarios (
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

-- 2. profissionais
CREATE TABLE profissionais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    updated_by VARCHAR(100)
);

-- 3. servicos
CREATE TABLE servicos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    duracao_minutos INT,
    preco DOUBLE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    profissional_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    CONSTRAINT fk_servico_profissional FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);

-- 4. especializacoes
CREATE TABLE especializacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL
);

-- 5. profissional_especializacoes
CREATE TABLE profissional_especializacoes (
    profissional_id BIGINT NOT NULL,
    especializacao_id BIGINT NOT NULL,
    PRIMARY KEY (profissional_id, especializacao_id),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (especializacao_id) REFERENCES especializacoes(id) ON DELETE CASCADE
);

-- 6. medicamentos
CREATE TABLE medicamentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

-- 7. paciente
CREATE TABLE paciente (
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

-- 8. enderecos
CREATE TABLE enderecos (
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

-- 9. agendamentos
CREATE TABLE agendamentos (
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

-- 10. pagamentos
CREATE TABLE pagamentos (
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

-- 11. relatorios
CREATE TABLE relatorios (
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

-- Inserindo usuários (1 cuidador, 1 responsável)
INSERT INTO usuarios (nome, email, senha, telefone, tipo_usuario, foto_perfil, data_nascimento, cpf, created_by, updated_by) VALUES
('João Cuidador', 'joao@agecare.com', 'senha123', '11988887777', 'CUIDADOR', 'https://exemplo.com/joao.jpg', '1985-05-10', '111.111.111-11', 'dev', 'dev'),
('Maria Responsável', 'maria@agecare.com', 'senha456', '11999996666', 'RESPONSAVEL', 'https://exemplo.com/maria.jpg', '1970-03-20', '222.222.222-22', 'dev', 'dev');

-- Inserindo profissional (associado ao usuário "João Cuidador")
INSERT INTO profissionais (formacao, biografia, curriculo_url, nota_media, total_avaliacoes, total_atendimentos, status_disponibilidade, created_by, updated_by) VALUES
('Técnico de Enfermagem', '10 anos de experiência com idosos.', 'https://cv.joao.com', 4.9, 100, 500, 'ABERTO', 'dev', 'dev');

-- Inserindo serviços (vinculado ao profissional_id 1)
INSERT INTO servicos (nome, descricao, duracao_minutos, preco, ativo, profissional_id, created_by, updated_by) VALUES
('Banho Assistido', 'Ajudamos o idoso com segurança no banho.', 30, 70.00, TRUE, 1, 'dev', 'dev'),
('Acompanhamento Médico', 'Levamos e acompanhamos em consultas.', 90, 120.00, TRUE, 1, 'dev', 'dev');

-- Inserindo especializações
INSERT INTO especializacoes (nome, descricao) VALUES
('Enfermagem Geriátrica', 'Cuidado clínico de idosos'),
('Cuidados Domiciliares', 'Atenção integral em casa');

-- Associando profissional à especializações
INSERT INTO profissional_especializacoes (profissional_id, especializacao_id) VALUES (1, 1), (1, 2);

-- Inserindo paciente (idoso)
INSERT INTO paciente (responsavel_id, nome, data_nascimento, sexo, arquivo_pdf_url, condicoes_medicas, mobilidade, nivel_dependencia, observacoes_especiais, contato_emergencia, created_by, updated_by) VALUES
(2, 'Dona Ana', '1940-04-01', 'FEMININO', 'https://docs.com/donaanapdf', 'Hipertensão e osteoporose', 'ASSISTIDA', 'ALTO', 'Necessita apoio constante', '11970000000', 'dev', 'dev');

-- Endereços de cada tipo
INSERT INTO enderecos (usuario_id, bairro, cidade, estado, cep, referencia, created_by, updated_by) VALUES
(2, 'Centro', 'São Paulo', 'SP', '01001-000', 'Casa da Maria', 'dev', 'dev');

INSERT INTO enderecos (idoso_id, bairro, cidade, estado, cep, referencia, created_by, updated_by) VALUES
(1, 'Bairro Idoso', 'São Paulo', 'SP', '04000-000', 'Casa da Dona Ana', 'dev', 'dev');

INSERT INTO enderecos (profissional_id, bairro, cidade, estado, cep, referencia, created_by, updated_by) VALUES
(1, 'Bairro Cuidador', 'São Paulo', 'SP', '05000-000', 'Residência João', 'dev', 'dev');

-- Inserindo agendamento
INSERT INTO agendamentos (profissional_id, idoso_id, responsavel_id, endereco_id, servico_id, data_inicio, data_fim, status) VALUES
(1, 1, 2, 2, 1, '2025-08-01 09:00:00', '2025-08-01 09:30:00', 'AGENDADO');

-- Pagamento relacionado
INSERT INTO pagamentos (agendamento_id, servico_id, valor, metodo_pagamento, status_pagamento, data_pagamento, comprovante_url) VALUES
(1, 1, 70.00, 'PIX', 'CONFIRMADO', '2025-08-01 09:40:00', 'https://comprovante.pix/1');

-- Relatório pós-consulta
INSERT INTO relatorios (agendamento_id, profissional_id, relatorio, medicamentos_administrados, intercorrencias, observacoes_comportamento) VALUES
(1, 1, 'Atendimento ocorreu com tranquilidade.', 'Dipirona', 'Nenhuma', 'Paciente tranquila e colaborativa');
