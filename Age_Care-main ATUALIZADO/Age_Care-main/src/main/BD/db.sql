DROP DATABASE IF EXISTS AgeCare;
CREATE DATABASE AgeCare;
USE AgeCare;

-- 1. usuários (cliente)
CREATE TABLE cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(11),
    tipo_usuario ENUM('CLIENTE') NOT NULL,
    foto_perfil VARCHAR(255),
    data_nascimento DATE,
    cpf VARCHAR(14) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- 2. profissionais (cuidador)
CREATE TABLE cuidador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(11),
    formacao TEXT,
    foto_url VARCHAR(255),
    biografia TEXT,
    curriculo_url VARCHAR(255),
    nota_media DECIMAL(3,2) DEFAULT 0.00,
    total_avaliacoes INT DEFAULT 0,
    total_atendimentos INT DEFAULT 0,
    tipo_usuario ENUM('CUIDADOR') NOT NULL,
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
    CONSTRAINT fk_servico_profissional FOREIGN KEY (profissional_id) REFERENCES cuidador(id) ON DELETE CASCADE
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
    FOREIGN KEY (profissional_id) REFERENCES cuidador(id) ON DELETE CASCADE,
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
    FOREIGN KEY (responsavel_id) REFERENCES cliente(id) ON DELETE CASCADE
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
    FOREIGN KEY (usuario_id) REFERENCES cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES paciente(id) ON DELETE CASCADE,
    FOREIGN KEY (profissional_id) REFERENCES cuidador(id) ON DELETE CASCADE,
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
    data_fim DATETIME NULL,
    status ENUM('AGENDADO','ESPERA','CANCELADO') DEFAULT 'ESPERA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento TIMESTAMP NULL,
    motivo_cancelamento TEXT,
    FOREIGN KEY (profissional_id) REFERENCES cuidador(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES paciente(id) ON DELETE CASCADE,
    FOREIGN KEY (responsavel_id) REFERENCES cliente(id) ON DELETE CASCADE,
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
    FOREIGN KEY (profissional_id) REFERENCES cuidador(id) ON DELETE CASCADE
);

-- (opcional que você usou antes)
ALTER TABLE agendamentos MODIFY COLUMN data_fim DATETIME NULL;

-- =====================================================
-- ================ DADOS DE EXEMPLO ===================
-- =====================================================

-- cliente (2)
INSERT INTO cliente
  (nome, email, senha, telefone, tipo_usuario, foto_perfil, data_nascimento, cpf, created_by, updated_by)
VALUES
  ('Maria Responsável',  'maria@agecare.com',  'senha123', '11999998888', 'CLIENTE', NULL, '1972-08-11', '123.456.789-01', 'dev', 'dev'),
  ('Carlos Responsável', 'carlos@agecare.com', 'senha456', '11988887777', 'CLIENTE', NULL, '1968-04-02', '321.654.987-02', 'dev', 'dev');

-- cuidador (2)
INSERT INTO cuidador
  (nome, email, senha, telefone, formacao, foto_url, biografia, curriculo_url,
   nota_media, total_avaliacoes, total_atendimentos, tipo_usuario, status_disponibilidade,
   created_by, updated_by)
VALUES
  ('João Cuidador',       'joao@agecare.com',   'senha321', '11987776655',
   'Técnico em Enfermagem', 'https://exemplo.com/joao.jpg',
   '10 anos de experiência em cuidados domiciliares',
   'https://exemplo.com/joao_cv.pdf',
   4.80, 24, 120, 'CUIDADOR', 'ABERTO', 'dev', 'dev'),
  ('Luciana Enfermeira',  'luciana@agecare.com','senha789', '11976665544',
   'Enfermagem - USP', 'https://exemplo.com/luciana.jpg',
   'Especializada em idosos acamados',
   'https://exemplo.com/luciana_cv.pdf',
   4.90, 33, 200, 'CUIDADOR', 'OCUPADO', 'dev', 'dev');

-- servicos (2) -> referenciam cuidador 1 e 2
INSERT INTO servicos
  (nome, descricao, duracao_minutos, preco, ativo, profissional_id, created_by, updated_by)
VALUES
  ('Banho Assistido',        'Auxílio ao idoso para banho seguro.',                 60,  80.00, TRUE, 1, 'dev', 'dev'),
  ('Acompanhamento Noturno', 'Monitoramento do paciente durante toda a noite.',   480, 350.00, TRUE, 2, 'dev', 'dev');

-- especializacoes (2)
INSERT INTO especializacoes (nome, descricao) VALUES
  ('Cuidados com Alzheimer', 'Atendimento especializado para Alzheimer'),
  ('Fisioterapia',           'Fisioterapia domiciliar');

-- profissional_especializacoes (2) -> (cuidador 1 -> esp 1) e (cuidador 2 -> esp 2)
INSERT INTO profissional_especializacoes (profissional_id, especializacao_id) VALUES
  (1, 1),
  (2, 2);

-- medicamentos (2)
INSERT INTO medicamentos (nome) VALUES
  ('Losartana'),
  ('Paracetamol');

-- paciente (2) -> ligados aos clientes 1 e 2
INSERT INTO paciente
  (responsavel_id, nome, data_nascimento, sexo, arquivo_pdf_url, condicoes_medicas, mobilidade, nivel_dependencia, observacoes_especiais, contato_emergencia, created_by, updated_by)
VALUES
  (1, 'Dona Ana', '1955-05-20', 'FEMININO', NULL, 'Diabetes, Hipertensão', 'ASSISTIDA', 'MEDIO', 'Atenção com quedas', '11999998888', 'dev', 'dev'),
  (2, 'Seu José', '1947-10-11', 'MASCULINO', NULL, 'Alzheimer',             'ACAMADO',  'ALTO',  'Sensível a barulho', '11988887777', 'dev', 'dev');

-- enderecos (2) -> um do cliente 1; um do cuidador 2 (respeitando o CHECK)
INSERT INTO enderecos
  (usuario_id, idoso_id, profissional_id, tipo_endereco, numero, bairro, cidade, estado, cep, referencia, created_by, updated_by)
VALUES
  (1, NULL, NULL, 'RESIDENCIAL', '125', 'Centro', 'São Paulo', 'SP', '01001-000', 'Apartamento 22', 'dev', 'dev'),
  (NULL, NULL, 2, 'ATENDIMENTO', '500', 'Jardins', 'São Paulo', 'SP', '01415-000', 'Casa 3', 'dev', 'dev');

-- agendamentos (2) -> casam com FKs; data_fim > data_inicio
INSERT INTO agendamentos
  (profissional_id, idoso_id, responsavel_id, endereco_id, servico_id, data_inicio, data_fim, status, data_cancelamento, motivo_cancelamento)
VALUES
  (1, 1, 1, 1, 1, '2025-08-20 10:00:00', '2025-08-20 11:00:00', 'AGENDADO', NULL, NULL),
  (2, 2, 2, 2, 2, '2025-08-21 20:00:00', '2025-08-21 22:00:00', 'ESPERA',   NULL, NULL);

-- pagamentos (2) -> um por agendamento
INSERT INTO pagamentos
  (agendamento_id, servico_id, valor, metodo_pagamento, status_pagamento, data_pagamento, comprovante_url)
VALUES
  (1, 1, 80.00,  'PIX',            'CONFIRMADO', '2025-08-19 13:00:00', 'https://exemplo.com/comp1.png'),
  (2, 2, 350.00, 'CARTAO_CREDITO', 'PENDENTE',   NULL,                  NULL);

-- relatorios (2) -> casam com (agendamento_id, profissional_id)
INSERT INTO relatorios
  (agendamento_id, profissional_id, relatorio, medicamentos_administrados, intercorrencias, observacoes_comportamento)
VALUES
  (1, 1, 'Banho realizado normalmente. Paciente tranquila.', 'Paracetamol', NULL, 'Dia calmo'),
  (2, 2, 'Plantão noturno tranquilo. Sem intercorrências.',  NULL,          NULL, 'Sono regular');
