
CREATE DATABASE AgeCare;
USE AgeCare;

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

CREATE TABLE profissionais (
    id BIGINT PRIMARY KEY,
    usuario_id INT NOT NULL,
    experiencia_anos INT,
    formacao TEXT,
    especializacoes TEXT,
    valor_hora DECIMAL(10,2),
    valor_diaria DECIMAL(10,2),
    disponibilidade_24h BOOLEAN DEFAULT FALSE,
    raio_atendimento_km INT DEFAULT 10,
    biografia TEXT,
    curriculo_url VARCHAR(255),
    registro_profissional VARCHAR(50),
    nota_media DECIMAL(3,2) DEFAULT 0.00,
    total_avaliacoes INT DEFAULT 0,
    total_atendimentos INT DEFAULT 0,
    status_aprovacao ENUM('pendente', 'aprovado', 'rejeitado') DEFAULT 'pendente',
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);


CREATE TABLE especialidades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    descricao TEXT,
    categoria ENUM('cuidados_basicos', 'cuidados_especializados', 'companhia', 'domestico') NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);


CREATE TABLE profissional_especialidades (
    profissional_id BIGINT,
    especialidade_id BIGINT,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (especialidade_id) REFERENCES especialidades(id) ON DELETE CASCADE
);

-- Tabela de idosos (pacientes)
CREATE TABLE idosos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    responsavel_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    genero ENUM('masculino', 'feminino', 'outro'),
    condicoes_medicas TEXT,
    medicamentos TEXT,
    mobilidade ENUM('independente', 'assistida', 'cadeira_rodas', 'acamado'),
    nivel_dependencia ENUM('baixo', 'medio', 'alto'),
    observacoes_especiais TEXT,
    contato_emergencia VARCHAR(100),
    telefone_emergencia VARCHAR(20),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE enderecos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    idoso_id INT,
    tipo_endereco ENUM('residencial', 'comercial', 'atendimento', 'emergencia') DEFAULT 'residencial',
    numero VARCHAR(10),
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    referencia TEXT, -- Pontos de referência
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    endereco_principal BOOLEAN DEFAULT FALSE,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (idoso_id) REFERENCES idosos(id) ON DELETE CASCADE,
    CHECK ((usuario_id IS NOT NULL AND idoso_id IS NULL) OR (usuario_id IS NULL AND idoso_id IS NOT NULL))
);



CREATE TABLE agendamentos (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    profissional_id INT NOT NULL,
    idoso_id INT NOT NULL,
    responsavel_id INT NOT NULL,
    endereco_atendimento_id INT NOT NULL,
    data_inicio DATETIME NOT NULL,
    data_fim DATETIME NOT NULL,
    tipo_servico ENUM('hora', 'diaria', 'pernoite', 'semanal') NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    status ENUM('solicitado', 'confirmado', 'em_andamento', 'concluido', 'cancelado') DEFAULT 'solicitado',
    observacoes TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_cancelamento TIMESTAMP NULL,
    motivo_cancelamento TEXT,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id),
    FOREIGN KEY (idoso_id) REFERENCES idosos(id),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id),
    FOREIGN KEY (endereco_atendimento_id) REFERENCES enderecos(id)
);


CREATE TABLE disponibilidade (
    id INT PRIMARY KEY AUTO_INCREMENT,
    profissional_id INT NOT NULL,
    dia_semana ENUM('domingo', 'segunda', 'terca', 'quarta', 'quinta', 'sexta', 'sabado') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);

-- Tabela de indisponibilidades específicas
CREATE TABLE indisponibilidades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    profissional_id INT NOT NULL,
    data_inicio DATETIME NOT NULL,
    data_fim DATETIME NOT NULL,
    motivo VARCHAR(255),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);


CREATE TABLE avaliacoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id INT NOT NULL,
    avaliador_id INT NOT NULL,
    avaliado_id INT NOT NULL,
    nota INT NOT NULL CHECK (nota >= 1 AND nota <= 5),
    comentario TEXT,
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),
    FOREIGN KEY (avaliador_id) REFERENCES usuarios(id),
    FOREIGN KEY (avaliado_id) REFERENCES usuarios(id)
);

-- Tabela de mensagens entre usuários
CREATE TABLE mensagens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    remetente_id INT NOT NULL,
    destinatario_id INT NOT NULL,
    agendamento_id INT,
    assunto VARCHAR(100),
    mensagem TEXT NOT NULL,
    lida BOOLEAN DEFAULT FALSE,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (remetente_id) REFERENCES usuarios(id),
    FOREIGN KEY (destinatario_id) REFERENCES usuarios(id),
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
);


CREATE TABLE documentos_profissional (
    id INT PRIMARY KEY AUTO_INCREMENT,
    profissional_id INT NOT NULL,
    tipo_documento ENUM('rg', 'cpf', 'certidao_antecedentes', 'comprovante_endereco', 'certificado_curso', 'outro') NOT NULL,
    nome_arquivo VARCHAR(255) NOT NULL,
    caminho_arquivo VARCHAR(500) NOT NULL,
    verificado BOOLEAN DEFAULT FALSE,
    data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);


CREATE TABLE pagamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    metodo_pagamento ENUM('dinheiro', 'pix', 'cartao_credito', 'cartao_debito', 'transferencia') NOT NULL,
    status_pagamento ENUM('pendente', 'processando', 'confirmado', 'estornado') DEFAULT 'pendente',
    data_pagamento TIMESTAMP NULL,
    comprovante_url VARCHAR(255),
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id)
);

-- Tabela de relatórios de atendimento
CREATE TABLE relatorios_atendimento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agendamento_id INT NOT NULL,
    profissional_id INT NOT NULL,
    relatorio TEXT NOT NULL,
    medicamentos_administrados TEXT,
    intercorrencias TEXT,
    observacoes_comportamento TEXT,
    data_relatorio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),
    FOREIGN KEY (profissional_id) REFERENCES profissionais(id)
);


INSERT INTO especialidades (nome, descricao, categoria) VALUES
('Cuidados de Higiene Pessoal', 'Auxílio com banho, escovação de dentes, cuidados com cabelo', 'cuidados_basicos'),
('Administração de Medicamentos', 'Controle e administração de medicamentos conforme prescrição médica', 'cuidados_especializados'),
('Acompanhamento Médico', 'Acompanhamento em consultas e exames médicos', 'cuidados_especializados'),
('Preparação de Refeições', 'Preparo de refeições adequadas às necessidades nutricionais', 'cuidados_basicos'),
('Companhia e Conversação', 'Proporcionar companhia e estímulo social', 'companhia'),
('Auxílio na Mobilidade', 'Ajuda para caminhar, transferências e exercícios básicos', 'cuidados_basicos'),
('Cuidados com Alzheimer/Demência', 'Cuidados especializados para pacientes com demência', 'cuidados_especializados'),
('Fisioterapia Básica', 'Exercícios básicos de fisioterapia e reabilitação', 'cuidados_especializados'),
('Organização do Ambiente', 'Manutenção da organização e limpeza do ambiente', 'domestico'),
('Cuidados Paliativos', 'Cuidados especializados para pacientes em estado terminal', 'cuidados_especializados');

-- Triggers para atualizar nota média dos cuidadores
DELIMITER //
CREATE TRIGGER atualizar_nota_cuidador
AFTER INSERT ON avaliacoes
FOR EACH ROW
BEGIN
    UPDATE cuidadores SET 
        nota_media = (
            SELECT AVG(nota) 
            FROM avaliacoes 
            WHERE avaliado_id = NEW.avaliado_id
        ),
        total_avaliacoes = (
            SELECT COUNT(*) 
            FROM avaliacoes 
            WHERE avaliado_id = NEW.avaliado_id
        )
    WHERE usuario_id = NEW.avaliado_id;
END//
DELIMITER ;

-- Trigger para contar total de atendimentos
DELIMITER //
CREATE TRIGGER contar_atendimentos
AFTER UPDATE ON agendamentos
FOR EACH ROW
BEGIN
    IF NEW.status = 'concluido' AND OLD.status != 'concluido' THEN
        UPDATE cuidadores SET 
            total_atendimentos = total_atendimentos + 1
        WHERE id = NEW.cuidador_id;
    END IF;
END//
DELIMITER ;
