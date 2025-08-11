// Função para alternar tema
function toggleTheme() {
    const body = document.body;
    const themeToggle = document.getElementById('themeToggle');
    
    body.classList.toggle('light');
    
    if (body.classList.contains('light')) {
        themeToggle.textContent = '🌙';
    } else {
        themeToggle.textContent = '☀️';
    }
}

// Função para cadastrar usuário
function cadastrar(event) {
    if (event) {
        event.preventDefault();
    }

    // Pegar os valores dos inputs pelos placeholders, já que não têm IDs
    const inputs = document.querySelectorAll('.input-group input');
    
    if (inputs.length < 3) {
        console.error("Inputs não encontrados!");
        return;
    }
    
    const nome = inputs[0].value.trim();
    const email = inputs[1].value.trim();
    const senha = inputs[2].value.trim();

    console.log("Dados coletados:", { nome, email, senha: "***" });

    // Validações básicas
    if (!nome || !email || !senha) {
        alert("Por favor, preencha todos os campos obrigatórios.");
        return;
    }

    if (senha.length < 6) {
        alert("Senha deve ter pelo menos 6 caracteres.");
        return;
    }

    // Validação básica de email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Por favor, insira um email válido.");
        return;
    }

    // Desabilitar botão durante o envio
    const submitBtn = document.querySelector('.btn-submit');
    if (!submitBtn) {
        console.error("Botão de submit não encontrado!");
        return;
    }
    
    const originalText = submitBtn.textContent;
    submitBtn.disabled = true;
    submitBtn.textContent = "Cadastrando...";

    // Preparar dados para envio (seguindo o modelo do backend)
    const userData = {
        nome: nome,
        email: email,
        senha: senha,
        tipoUsuario: "RESPONSAVEL" // Valor padrão baseado no enum do backend
    };

    console.log("Enviando dados para API:", userData);

    // Fazer requisição para a API
    fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        console.log("Response status:", response.status);
        console.log("Response headers:", response.headers);
        
        if (!response.ok) {
            return response.text().then(text => {
                console.error("Response error text:", text);
                throw new Error(`Erro HTTP: ${response.status} - ${text}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log("Resposta da API:", data);
        
        // Mostrar mensagem de sucesso
        const confirmation = document.getElementById('confirmation');
        if (confirmation) {
            confirmation.textContent = "Usuário cadastrado com sucesso!";
            confirmation.style.color = 'var(--success)';
            confirmation.style.display = 'block';
        }
        
        // Limpar formulário
        inputs[0].value = "";
        inputs[1].value = "";
        inputs[2].value = "";
        
        // Redirecionar para index.html após 2 segundos
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 2000);
    })
    .catch(error => {
        console.error("Erro detalhado:", error);
        
        let errorMessage = "Erro ao cadastrar usuário. ";
        if (error.message.includes("500")) {
            errorMessage += "Erro interno do servidor. Verifique se o banco de dados está funcionando.";
        } else if (error.message.includes("404")) {
            errorMessage += "Endpoint não encontrado. Verifique se o servidor está rodando.";
        } else if (error.message.includes("Failed to fetch")) {
            errorMessage += "Não foi possível conectar ao servidor. Verifique sua conexão.";
        } else {
            errorMessage += error.message;
        }
        
        alert(errorMessage);
        
        // Mostrar erro na tela
        const confirmation = document.getElementById('confirmation');
        if (confirmation) {
            confirmation.textContent = "Erro ao cadastrar usuário!";
            confirmation.style.color = '#ff4444';
            confirmation.style.display = 'block';
        }
    })
    .finally(() => {
        // Reabilitar botão
        if (submitBtn) {
            submitBtn.disabled = false;
            submitBtn.textContent = originalText;
        }
    });
}

// Função para o botão de login
function irParaLogin() {
    window.location.href = 'index.html';
}

// Função para entrar com Google (placeholder)
function entrarComGoogle() {
    alert("Funcionalidade de login com Google será implementada em breve!");
}

// Inicialização quando a página carregar
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM carregado, inicializando eventos...");
    
    // Adicionar evento de submit ao botão "Finalizar Cadastro"
    const submitBtn = document.querySelector('.btn-submit');
    if (submitBtn) {
        console.log("Botão submit encontrado, adicionando evento...");
        submitBtn.addEventListener('click', function(e) {
            e.preventDefault();
            cadastrar(e);
        });
    } else {
        console.error("Botão submit não encontrado!");
    }
    
    // Adicionar evento ao botão de login
    const loginBtn = document.querySelector('.btn-login');
    if (loginBtn) {
        loginBtn.addEventListener('click', irParaLogin);
    } else {
        console.warn("Botão login não encontrado");
    }
    
    // Adicionar evento ao botão do Google
    const googleBtn = document.querySelector('.btn-google');
    if (googleBtn) {
        googleBtn.addEventListener('click', entrarComGoogle);
    } else {
        console.warn("Botão Google não encontrado");
    }
    
    // Configurar validação em tempo real
    const inputs = document.querySelectorAll('.input-group input');
    console.log(`Encontrados ${inputs.length} inputs`);
    
    inputs.forEach((input, index) => {
        console.log(`Configurando input ${index}: ${input.placeholder}`);
        
        input.addEventListener('blur', function() {
            if (this.value.trim() === '') {
                this.parentElement.style.borderColor = '#ff4444';
            } else {
                this.parentElement.style.borderColor = '';
            }
        });
        
        input.addEventListener('input', function() {
            this.parentElement.style.borderColor = '';
        });
        
        // Adicionar evento Enter para submeter o formulário
        input.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                cadastrar();
            }
        });
    });

    // Verificar se o backend está acessível
    fetch('http://localhost:8080/api/usuarios', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            console.log("✅ Backend está acessível");
        } else {
            console.warn("⚠️ Backend retornou status:", response.status);
        }
    })
    .catch(error => {
        console.error("❌ Erro ao conectar com backend:", error.message);
    });
});