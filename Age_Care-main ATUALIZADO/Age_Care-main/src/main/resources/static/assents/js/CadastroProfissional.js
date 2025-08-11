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
        
        this.updateDebugDisplay();
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

    updateDebugDisplay() {
        const debugElement = document.getElementById('debugLogs');
        if (debugElement) {
            const logsText = this.logs.map(log => 
                `[${log.timestamp}] ${log.level.toUpperCase()}: ${log.message}${log.data ? '\n  Data: ' + JSON.stringify(log.data, null, 2) : ''}`
            ).join('\n\n');
            
            debugElement.textContent = logsText;
            debugElement.scrollTop = debugElement.scrollHeight;
        }
    }

    toggle() {
        this.isVisible = !this.isVisible;
        const debugElement = document.getElementById('debugLogs');
        const toggleButton = document.getElementById('debugToggle');
        
        if (debugElement) {
            debugElement.style.display = this.isVisible ? 'block' : 'none';
        }
        
        if (toggleButton) {
            toggleButton.textContent = this.isVisible ? '🐛 Ocultar Logs' : '🐛 Mostrar Logs';
        }
    }
}

// Instância global do logger
const logger = new DebugLogger();

// ==================== SISTEMA DE TEMA ====================
class ThemeManager {
    constructor() {
        this.currentTheme = 'light';
        this.init();
    }

    init() {
        logger.info('Inicializando sistema de tema');
        
        // Aguardar DOM estar pronto
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => this.setupThemeToggle());
        } else {
            this.setupThemeToggle();
        }
    }

    setupThemeToggle() {
        const themeToggle = document.getElementById('themeToggle');
        if (themeToggle) {
            themeToggle.addEventListener('click', () => this.toggle());
            logger.info('Event listener do tema adicionado');
        } else {
            logger.warn('Elemento themeToggle não encontrado');
        }
    }

    toggle() {
        this.currentTheme = this.currentTheme === 'light' ? 'dark' : 'light';
        
        const body = document.body;
        const themeToggle = document.getElementById('themeToggle');
        
        if (this.currentTheme === 'dark') {
            body.classList.add('dark-theme');
            if (themeToggle) themeToggle.textContent = '🌙';
        } else {
            body.classList.remove('dark-theme');
            if (themeToggle) themeToggle.textContent = '☀️';
        }
        
        logger.info(`Tema alterado para: ${this.currentTheme}`);
    }
}

// ==================== SISTEMA DE VALIDAÇÃO ====================
class ValidationManager {
    constructor() {
        this.validators = {
            nome: (value) => this.validateNome(value),
            email: (value) => this.validateEmail(value),
            cpf: (value) => this.validateCPF(value),
            telefone: (value) => this.validateTelefone(value),
            cep: (value) => this.validateCEP(value),
            senha: (value) => this.validateSenha(value),
            confirmarSenha: (value) => this.validateConfirmPassword(value),
            required: (value) => this.validateRequired(value)
        };
    }

    validateNome(nome) {
        if (!nome || nome.trim().length < 2) {
            return { valid: false, message: 'Nome deve ter pelo menos 2 caracteres' };
        }
        if (!/^[a-zA-ZÀ-ÿ\s]+$/.test(nome)) {
            return { valid: false, message: 'Nome deve conter apenas letras e espaços' };
        }
        return { valid: true, message: '' };
    }

    validateEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regex.test(email)) {
            return { valid: false, message: 'Email inválido' };
        }
        return { valid: true, message: '' };
    }

    validateCPF(cpf) {
        const cleanCPF = cpf.replace(/\D/g, '');
        
        if (cleanCPF.length !== 11) {
            return { valid: false, message: 'CPF deve ter 11 dígitos' };
        }
        
        // Verificar se todos os dígitos são iguais
        if (/^(\d)\1{10}$/.test(cleanCPF)) {
            return { valid: false, message: 'CPF inválido' };
        }
        
        // Validar primeiro dígito verificador
        let soma = 0;
        for (let i = 0; i < 9; i++) {
            soma += parseInt(cleanCPF.charAt(i)) * (10 - i);
        }
        let resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cleanCPF.charAt(9))) {
            return { valid: false, message: 'CPF inválido' };
        }
        
        // Validar segundo dígito verificador
        soma = 0;
        for (let i = 0; i < 10; i++) {
            soma += parseInt(cleanCPF.charAt(i)) * (11 - i);
        }
        resto = 11 - (soma % 11);
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cleanCPF.charAt(10))) {
            return { valid: false, message: 'CPF inválido' };
        }
        
        return { valid: true, message: '' };
    }

    validateTelefone(telefone) {
        const cleanPhone = telefone.replace(/\D/g, '');
        if (cleanPhone.length < 10 || cleanPhone.length > 11) {
            return { valid: false, message: 'Telefone deve ter 10 ou 11 dígitos' };
        }
        return { valid: true, message: '' };
    }

    validateCEP(cep) {
        const cleanCEP = cep.replace(/\D/g, '');
        if (cleanCEP.length !== 8) {
            return { valid: false, message: 'CEP deve ter 8 dígitos' };
        }
        return { valid: true, message: '' };
    }

    validateSenha(senha) {
        if (senha.length < 6) {
            return { valid: false, message: 'Senha deve ter pelo menos 6 caracteres' };
        }
        return { valid: true, message: '' };
    }

    validateConfirmPassword(confirmPassword) {
        const senhaInput = document.getElementById('senha');
        if (!senhaInput) {
            return { valid: false, message: 'Campo senha não encontrado' };
        }
        
        const senha = senhaInput.value;
        if (confirmPassword !== senha) {
            return { valid: false, message: 'Senhas não coincidem' };
        }
        return { valid: true, message: '' };
    }

    validateRequired(value) {
        if (!value || value.trim() === '') {
            return { valid: false, message: 'Campo obrigatório' };
        }
        return { valid: true, message: '' };
    }

    validateField(fieldId, validationType = 'required') {
        const field = document.getElementById(fieldId);
        const errorElement = document.getElementById(`error-${fieldId}`);
        
        if (!field) {
            logger.error(`Campo não encontrado: ${fieldId}`);
            return false;
        }

        const value = field.value.trim();
        let result;

        // Aplicar validação específica
        if (this.validators[fieldId]) {
            result = this.validators[fieldId](value);
        } else if (validationType === 'required') {
            result = this.validators.required(value);
        } else {
            result = { valid: true, message: '' };
        }

        // Atualizar interface
        const inputGroup = field.closest('.input-group');
        
        if (inputGroup) {
            if (result.valid) {
                inputGroup.classList.remove('invalid');
                inputGroup.classList.add('valid');
            } else {
                inputGroup.classList.remove('valid');
                inputGroup.classList.add('invalid');
            }
        }

        // Atualizar mensagem de erro
        if (errorElement) {
            errorElement.textContent = result.message;
            if (result.valid) {
                errorElement.classList.remove('show');
            } else {
                errorElement.classList.add('show');
            }
        }

        logger.info(`Validação ${fieldId}:`, { valid: result.valid, message: result.message });
        return result.valid;
    }

    validateAll() {
        const requiredFields = [
            'nome', 'email', 'telefone', 'cpf', 'cep', 'cidade', 
            'estado', 'bairro', 'endereco', 'profissao', 'registro', 
            'experiencia', 'senha', 'confirmarSenha'
        ];

        let allValid = true;
        
        requiredFields.forEach(fieldId => {
            const isValid = this.validateField(fieldId);
            if (!isValid) allValid = false;
        });

        logger.info(`Validação geral:`, { valid: allValid });
        return allValid;
    }
}

// ==================== SISTEMA DE MÁSCARAS ====================
class MaskManager {
    constructor() {
        this.init();
    }

    init() {
        logger.info('Inicializando máscaras de input');
        
        // Aguardar DOM estar pronto
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => this.setupMasks());
        } else {
            this.setupMasks();
        }
    }

    setupMasks() {
        const cpfInput = document.getElementById('cpf');
        const telefoneInput = document.getElementById('telefone');
        const cepInput = document.getElementById('cep');
        
        if (cpfInput) {
            cpfInput.addEventListener('input', (e) => this.applyCPFMask(e.target));
            logger.info('Máscara CPF aplicada');
        }
        
        if (telefoneInput) {
            telefoneInput.addEventListener('input', (e) => this.applyPhoneMask(e.target));
            logger.info('Máscara telefone aplicada');
        }
        
        if (cepInput) {
            cepInput.addEventListener('input', (e) => this.applyCEPMask(e.target));
            cepInput.addEventListener('blur', (e) => this.handleCEPBlur(e.target));
            logger.info('Máscara CEP aplicada');
        }
    }

    applyCPFMask(input) {
        let value = input.value.replace(/\D/g, '');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        input.value = value;
    }

    applyPhoneMask(input) {
        let value = input.value.replace(/\D/g, '');
        if (value.length <= 10) {
            value = value.replace(/(\d{2})(\d)/, '($1) $2');
            value = value.replace(/(\d{4})(\d)/, '$1-$2');
        } else {
            value = value.replace(/(\d{2})(\d)/, '($1) $2');
            value = value.replace(/(\d{5})(\d)/, '$1-$2');
        }
        input.value = value;
    }

    applyCEPMask(input) {
        let value = input.value.replace(/\D/g, '');
        value = value.replace(/(\d{5})(\d)/, '$1-$2');
        input.value = value;
    }

    async handleCEPBlur(input) {
        const cep = input.value.replace(/\D/g, '');
        if (cep.length === 8) {
            logger.info(`Buscando endereço para CEP: ${cep}`);
            await this.fetchAddressByCEP(cep);
        }
    }

    async fetchAddressByCEP(cep) {
        try {
            logger.info(`Iniciando busca de CEP: ${cep}`);
            
            const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const data = await response.json();
            
            if (!data.erro) {
                const cidadeField = document.getElementById('cidade');
                const estadoField = document.getElementById('estado');
                const bairroField = document.getElementById('bairro');
                const enderecoField = document.getElementById('endereco');
                
                if (cidadeField) cidadeField.value = data.localidade || '';
                if (estadoField) estadoField.value = data.uf || '';
                if (bairroField) bairroField.value = data.bairro || '';
                if (enderecoField && data.logradouro) enderecoField.value = data.logradouro;
                
                logger.success('CEP encontrado e campos preenchidos', data);
            } else {
                logger.error('CEP não encontrado');
                this.showMessage('CEP não encontrado!', 'error');
            }
        } catch (error) {
            logger.error('Erro ao buscar CEP', error);
            this.showMessage('Erro ao buscar CEP. Verifique sua conexão.', 'error');
        }
    }

    showMessage(message, type) {
        // Sistema básico de mensagens
        const messageDiv = document.createElement('div');
        messageDiv.textContent = message;
        messageDiv.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 10px 15px;
            border-radius: 5px;
            color: white;
            z-index: 9999;
            background-color: ${type === 'error' ? '#dc3545' : '#28a745'};
        `;
        
        document.body.appendChild(messageDiv);
        
        setTimeout(() => {
            if (messageDiv.parentNode) {
                messageDiv.parentNode.removeChild(messageDiv);
            }
        }, 3000);
    }
}

// ==================== SISTEMA DE CADASTRO ====================
class RegistrationManager {
    constructor() {
        this.apiBase = 'http://localhost:8080/api';
        this.isProcessing = false;
    }

    async cadastrarProfissional(dadosFormulario) {
        if (this.isProcessing) {
            logger.warn('Cadastro já em andamento, ignorando nova tentativa');
            return;
        }

        this.isProcessing = true;
        this.showLoading(true);

        try {
            logger.info('Iniciando processo de cadastro profissional');

            // Etapa 1: Criar usuário
            const usuario = await this.criarUsuario(dadosFormulario);
            logger.success('Usuário criado com sucesso', { id: usuario.id });

            // Etapa 2: Criar profissional
            const profissional = await this.criarProfissional(dadosFormulario, usuario);
            logger.success('Profissional criado com sucesso', { id: profissional.id });

            // Etapa 3: Criar endereço (opcional - não deve falhar o cadastro)
            try {
                const endereco = await this.criarEndereco(dadosFormulario, profissional);
                logger.success('Endereço criado com sucesso', { id: endereco.id });
            } catch (enderecoError) {
                logger.warn('Erro ao criar endereço (não crítico)', enderecoError);
            }

            // Sucesso total
            this.showSuccess(profissional.id);
            this.clearForm();

        } catch (error) {
            logger.error('Erro no processo de cadastro', error);
            this.showError(error.message);
        } finally {
            this.isProcessing = false;
            this.showLoading(false);
        }
    }

    async criarUsuario(dados) {
        const usuarioData = {
            nome: dados.nome,
            email: dados.email,
            senha: dados.senha,
            telefone: dados.telefone.replace(/\D/g, ''),
            tipoUsuario: "CUIDADOR",
            cpf: dados.cpf.replace(/\D/g, '')
        };

        logger.info('Enviando dados do usuário', usuarioData);

        const response = await fetch(`${this.apiBase}/usuarios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuarioData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ao criar usuário: ${response.status} - ${errorText}`);
        }

        return await response.json();
    }

    async criarProfissional(dados, usuario) {
        const experienciaTexto = {
            'menos-1': 'menos de 1 ano',
            '1-3': '1 a 3 anos',
            '3-5': '3 a 5 anos',
            '5-10': '5 a 10 anos',
            'mais-10': 'mais de 10 anos'
        };

        const profissionalData = {
            formacao: `${dados.profissao} - Registro: ${dados.registro}`,
            biografia: `Profissional com experiência de ${experienciaTexto[dados.experiencia] || dados.experiencia} na área de ${dados.profissao.toLowerCase()}.`,
            curriculoUrl: "",
            notaMedia: 0.0,
            totalAvaliacoes: 0,
            totalAtendimentos: 0,
            statusDisponibilidade: "ABERTO",
            servico: {
                id: 3
            },
            especialidades: []
        };

        logger.info('Enviando dados do profissional', profissionalData);

        const response = await fetch(`${this.apiBase}/profissional`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(profissionalData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ao criar profissional: ${response.status} - ${errorText}`);
        }

        return await response.json();
    }

    async criarEndereco(dados, profissional) {
        const enderecoData = {
            numero: this.extrairNumeroEndereco(dados.endereco),
            bairro: dados.bairro,
            cidade: dados.cidade,
            estado: dados.estado,
            cep: dados.cep.replace(/\D/g, ''),
            referencia: "",
            tipoEndereco: "COMERCIAL",
            profissional: {
                id: profissional.id
            }
        };

        logger.info('Enviando dados do endereço', enderecoData);

        const response = await fetch(`${this.apiBase}/enderecos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(enderecoData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ao criar endereço: ${response.status} - ${errorText}`);
        }

        return await response.json();
    }

    extrairNumeroEndereco(endereco) {
        const match = endereco.match(/\b\d+\b/);
        return match ? match[0] : 'S/N';
    }

    showLoading(show) {
        const loadingElement = document.getElementById('loadingIndicator');
        const submitButton = document.getElementById('btnCadastro');
        
        if (loadingElement) {
            loadingElement.style.display = show ? 'block' : 'none';
        }
        
        if (submitButton) {
            submitButton.disabled = show;
            submitButton.textContent = show ? 'Processando...' : 'Finalizar Cadastro';
        }
    }

    showSuccess(profissionalId) {
        const confirmationElement = document.getElementById('confirmation');
        if (confirmationElement) {
            confirmationElement.className = 'confirmation success';
            confirmationElement.innerHTML = `
                ✅ Cadastro realizado com sucesso!<br>
                Redirecionando para a página de perfil em <span id="countdown">3</span> segundos...
            `;

            // Countdown visual
            let countdown = 3;
            const countdownElement = document.getElementById('countdown');
            
            const countdownInterval = setInterval(() => {
                countdown--;
                if (countdownElement) {
                    countdownElement.textContent = countdown;
                }
                
                if (countdown <= 0) {
                    clearInterval(countdownInterval);
                    
                    // Redirecionar para o perfil
                    logger.info('Redirecionando para perfil do profissional:', profissionalId);
                    window.location.href = `PerfilProfissional.html?id=${profissionalId}`;
                }
            }, 1000);
        }
    }

    showError(errorMessage) {
        const confirmationElement = document.getElementById('confirmation');
        if (confirmationElement) {
            confirmationElement.className = 'confirmation error';
            confirmationElement.innerHTML = `
                ❌ Erro ao realizar cadastro: ${errorMessage}<br>
                Verifique os dados e tente novamente.
            `;
        }
    }

    clearForm() {
        const fieldsToReset = document.querySelectorAll('input, select');
        fieldsToReset.forEach(field => {
            if (field.type !== 'button' && field.type !== 'submit') {
                field.value = '';
            }
        });
        
        // Limpar classes de validação
        document.querySelectorAll('.input-group').forEach(group => {
            group.classList.remove('valid', 'invalid');
        });
        
        // Limpar mensagens de erro
        document.querySelectorAll('.error-message').forEach(error => {
            error.textContent = '';
            error.classList.remove('show');
        });
    }
}

// ==================== INICIALIZAÇÃO DO SISTEMA ====================
class AgeCareApp {
    constructor() {
        this.themeManager = null;
        this.validationManager = null;
        this.maskManager = null;
        this.registrationManager = null;
        
        this.init();
    }

    init() {
        logger.info('Inicializando aplicação Age Care');
        
        try {
            // Inicializar managers
            this.themeManager = new ThemeManager();
            this.validationManager = new ValidationManager();
            this.maskManager = new MaskManager();
            this.registrationManager = new RegistrationManager();
            
            // Event listeners principais (aguardar DOM)
            if (document.readyState === 'loading') {
                document.addEventListener('DOMContentLoaded', () => this.setupEventListeners());
            } else {
                this.setupEventListeners();
            }
            
            // Mostrar botão de debug se necessário
            this.setupDebugMode();
            
            logger.success('Aplicação inicializada com sucesso');
        } catch (error) {
            logger.error('Erro ao inicializar aplicação', error);
            throw error;
        }
    }

    setupEventListeners() {
        // Botão de cadastro
        const btnCadastro = document.getElementById('btnCadastro');
        if (btnCadastro) {
            btnCadastro.addEventListener('click', (e) => this.handleSubmit(e));
            logger.info('Event listener do botão de cadastro adicionado');
        } else {
            logger.warn('Botão de cadastro não encontrado');
        }

        // Botão toggle cliente
        const toggleClient = document.getElementById('toggleClient');
        if (toggleClient) {
            toggleClient.addEventListener('click', () => this.handleToggleClient());
            logger.info('Event listener do toggle cliente adicionado');
        }

        // Validação em tempo real
        const fieldsToValidate = [
            'nome', 'email', 'telefone', 'cpf', 'cep', 'cidade', 
            'estado', 'bairro', 'endereco', 'profissao', 'registro', 
            'experiencia', 'senha', 'confirmarSenha'
        ];

        fieldsToValidate.forEach(fieldId => {
            const field = document.getElementById(fieldId);
            if (field) {
                field.addEventListener('blur', () => {
                    this.validationManager.validateField(fieldId);
                });
            }
        });

        logger.info('Event listeners de validação adicionados');
    }

    setupDebugMode() {
        // Aguardar DOM se necessário
        const setupDebug = () => {
            const debugToggle = document.getElementById('debugToggle');
            if (debugToggle) {
                debugToggle.style.display = 'block';
                debugToggle.addEventListener('click', () => logger.toggle());
                logger.info('Modo debug ativado');
            }
        };

        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', setupDebug);
        } else {
            setupDebug();
        }
    }

    async handleSubmit(event) {
        event.preventDefault();
        logger.info('Iniciando processo de cadastro');

        try {
            // Validar todos os campos
            if (!this.validationManager.validateAll()) {
                logger.error('Validação falhou - campos obrigatórios não preenchidos');
                return;
            }

            // Coletar dados do formulário
            const dadosFormulario = this.collectFormData();
            logger.info('Dados do formulário coletados', dadosFormulario);

            // Executar cadastro
            await this.registrationManager.cadastrarProfissional(dadosFormulario);
        } catch (error) {
            logger.error('Erro no processo de cadastro', error);
        }
    }

    collectFormData() {
        const getFieldValue = (id) => {
            const field = document.getElementById(id);
            return field ? field.value.trim() : '';
        };

        return {
            nome: getFieldValue('nome'),
            email: getFieldValue('email'),
            telefone: getFieldValue('telefone'),
            cpf: getFieldValue('cpf'),
            cep: getFieldValue('cep'),
            cidade: getFieldValue('cidade'),
            estado: getFieldValue('estado'),
            bairro: getFieldValue('bairro'),
            endereco: getFieldValue('endereco'),
            profissao: getFieldValue('profissao'),
            registro: getFieldValue('registro'),
            experiencia: getFieldValue('experiencia'),
            senha: getFieldValue('senha'),
            confirmarSenha: getFieldValue('confirmarSenha')
        };
    }

    handleToggleClient() {
        logger.info('Redirecionamento para cadastro de cliente solicitado');
        // Descomente a linha abaixo quando quiser ativar o redirecionamento
        // window.location.href = '../cliente/cadastroCliente.html';
        alert('Redirecionamento para cadastro de cliente (desabilitado para testes)');
    }
}

// ==================== INICIALIZAÇÃO ====================
document.addEventListener('DOMContentLoaded', function() {
    try {
        window.ageCareApp = new AgeCareApp();
    } catch (error) {
        console.error('Erro ao inicializar aplicação:', error);
        
        // Fallback para mostrar erro ao usuário
        const errorDiv = document.createElement('div');
        errorDiv.style.cssText = `
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            background: #dc3545;
            color: white;
            padding: 15px;
            border-radius: 5px;
            z-index: 9999;
        `;
        errorDiv.textContent = 'Erro ao carregar a aplicação. Verifique o console para mais detalhes.';
        document.body.appendChild(errorDiv);
    }
});

// Expor logger globalmente para debug manual
window.logger = logger;