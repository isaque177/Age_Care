// ==================== SISTEMA DE LOGS DEBUG ====================
class DebugLogger {
    constructor() {
        this.logs = [];
        this.isVisible = false;
    }

    log(level, message, data = null) {
        const timestamp = new Date().toLocaleTimeString();
        const logEntry = {
            timestamp,
            level,
            message,
            data
        };
        
        this.logs.push(logEntry);
        console.log(`[${timestamp}] ${level.toUpperCase()}: ${message}`, data || '');
    }

    info(message, data = null) {
        this.log('info', message, data);
    }

    error(message, data = null) {
        this.log('error', message, data);
    }

    warn(message, data = null) {
        this.log('warn', message, data);
    }

    success(message, data = null) {
        this.log('success', message, data);
    }
}

const logger = new DebugLogger();

// ==================== GERENCIADOR DE PERFIL PROFISSIONAL ====================
class PerfilProfissionalManager {
    constructor() {
        this.apiBase = 'http://localhost:8080/api';
        this.profissionalId = null;
        this.profissionalData = null;
        
        this.init();
    }

    init() {
        logger.info('Inicializando perfil profissional');
        
        // Obter ID do profissional da URL
        this.profissionalId = this.getIdFromUrl();
        
        if (!this.profissionalId) {
            logger.error('ID do profissional não encontrado na URL');
            this.showError('Profissional não encontrado. Redirecionando...');
            setTimeout(() => {
                window.location.href = '../cadastro/cadastroProfissional.html';
            }, 2000);
            return;
        }

        logger.info(`ID do profissional: ${this.profissionalId}`);
        
        // Carregar dados do perfil
        this.carregarDadosProfissional();
        
        // Configurar event listeners
        this.setupEventListeners();
    }

    getIdFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('id');
    }

    async carregarDadosProfissional() {
        try {
            logger.info('Carregando dados do profissional');
            
            // Carregar dados básicos do profissional
            const profissional = await this.fetchProfissional();
            this.profissionalData = profissional;
            
            // Atualizar interface
            this.atualizarDadosPerfil(profissional);
            
            // Carregar agendamentos
            await this.carregarAgendamentos();
            
            // Carregar serviços
            await this.carregarServicos();
            
            logger.success('Dados do perfil carregados com sucesso');
            
        } catch (error) {
            logger.error('Erro ao carregar dados do profissional', error);
            this.showError('Erro ao carregar dados do perfil.');
        }
    }

    async fetchProfissional() {
        logger.info(`Buscando profissional ID: ${this.profissionalId}`);
        
        const response = await fetch(`${this.apiBase}/profissional/${this.profissionalId}`);
        
        if (!response.ok) {
            throw new Error(`Erro ao buscar profissional: ${response.status}`);
        }
        
        return await response.json();
    }

    atualizarDadosPerfil(profissional) {
        logger.info('Atualizando dados do perfil na interface');
        
        // Atualizar campos básicos
        this.updateElement('prof-nome', profissional.usuario?.nome || 'Nome não disponível');
        this.updateElement('prof-email', profissional.usuario?.email || 'Email não disponível');
        this.updateElement('prof-telefone', this.formatTelefone(profissional.usuario?.telefone) || 'Telefone não disponível');
        this.updateElement('prof-especialidade', profissional.formacao || 'Especialidade não disponível');
    }

    async carregarAgendamentos() {
        try {
            logger.info('Carregando agendamentos');
            
            const agendamentos = await this.fetchAgendamentos();
            this.atualizarListaAgendamentos(agendamentos);
            
        } catch (error) {
            logger.warn('Erro ao carregar agendamentos', error);
            this.atualizarListaAgendamentos([]);
        }
    }

    async fetchAgendamentos() {
        const response = await fetch(`${this.apiBase}/agendamentos/profissional/${this.profissionalId}`);
        
        if (!response.ok) {
            throw new Error(`Erro ao