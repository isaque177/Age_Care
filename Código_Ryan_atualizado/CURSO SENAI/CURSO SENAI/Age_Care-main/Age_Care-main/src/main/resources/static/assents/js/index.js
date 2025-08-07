document.addEventListener('DOMContentLoaded', function() {
    
    // --- SCRIPT PARA O DROPDOWN DO PERFIL ---
    const profileBtn = document.getElementById('profileBtn');
    const dropdownContent = document.getElementById('dropdownContent');
    if (profileBtn) {
        profileBtn.addEventListener('click', function(event) {
            event.stopPropagation();
            dropdownContent.classList.toggle('show');
        });
    }
    window.addEventListener('click', function(event) {
        if (dropdownContent && !event.target.closest('.profile-dropdown')) {
            if (dropdownContent.classList.contains('show')) {
                dropdownContent.classList.remove('show');
            }
        }
    });

    // --- SCRIPT PARA BUSCA E RENDERIZAÇÃO DINÂMICA ---

    // 1. SELEÇÃO DOS ELEMENTOS DO DOM
    const searchInput = document.getElementById('searchInput');
    const listingsGrid = document.getElementById('listingsGrid');

    // NOVO: Variável para armazenar a lista completa de profissionais vinda da API
    let allProfessionals = [];

    // 2. FUNÇÃO PARA RENDERIZAR OS CARDS
    function renderProfissionais(profissionaisParaRenderizar) {
        listingsGrid.innerHTML = ''; // Limpa o grid

        if (profissionaisParaRenderizar.length === 0) {
            listingsGrid.innerHTML = '<p class="not-found">Nenhum profissional encontrado.</p>';
            return;
        }

        profissionaisParaRenderizar.forEach(profissional => {
            const cardHtml = `
                <div class="card" data-id="${profissional.id}">
                    <img src="${profissional.imagemUrl || 'https://via.placeholder.com/400x300?text=Sem+Foto'}" alt="Foto de ${profissional.nome}">
                    <div class="card-body">
                        <h3>${profissional.nome}</h3>
                        <p class="specialty">${profissional.especialidade}</p>
                        <div class="card-meta-info">
                            <span class="location"><i class="fas fa-map-marker-alt"></i> ${profissional.localizacao}</span>
                            <span class="price">A partir de R$ ${profissional.preco || 'N/A'}/h</span>
                        </div>
                        <div class="card-buttons">
                            <button class="btn-details">Ver Perfil</button>
                            <button class="btn-action">Agendar</button>
                        </div>
                    </div>
                </div>
            `;
            listingsGrid.innerHTML += cardHtml;
        });
    }

    // 3. LÓGICA DE BUSCA (filtra a lista `allProfessionals`)
    searchInput.addEventListener('keyup', (event) => {
        const searchTerm = event.target.value.toLowerCase();
        
        const profissionaisFiltrados = allProfessionals.filter(p => {
            return p.nome.toLowerCase().includes(searchTerm) ||
                   p.especialidade.toLowerCase().includes(searchTerm) ||
                   p.localizacao.toLowerCase().includes(searchTerm);
        });

        renderProfissionais(profissionaisFiltrados);
    });

    // 4. LÓGICA DE CLIQUE (REDIRECIONAMENTO)
    listingsGrid.addEventListener('click', (event) => {
        const target = event.target;
        const card = target.closest('.card');
        if (!card) return;

        const profissionalId = card.dataset.id;
        if (target.classList.contains('btn-action')) {
            window.location.href = `Agendamento.html?id=${profissionalId}`;
        }
        if (target.classList.contains('btn-details')) {
            alert(`Funcionalidade "Ver Perfil" para o profissional ID: ${profissionalId} ainda não implementada.`);
        }
    });

    // 5. FUNÇÃO PARA BUSCAR DADOS DA API
    async function fetchAndRenderProfessionals() {
        listingsGrid.innerHTML = '<p class="loading">Carregando profissionais...</p>';

        try {
            const response = await fetch('http://localhost:8080/api/profissional');

            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.statusText}`);
            }

            const data = await response.json();
            allProfessionals = data; // Armazena a lista completa
            renderProfissionais(allProfessionals); // Renderiza os profissionais na tela

        } catch (error) {
            console.error('Falha ao buscar profissionais:', error);
            listingsGrid.innerHTML = '<p class="error">Não foi possível carregar os profissionais. Verifique se o servidor está ativo e tente novamente.</p>';
        }
    }

    // --- INICIALIZAÇÃO ---
    fetchAndRenderProfessionals();
});
