// ================== LOGIN REAL POR PROMPT ==================
let responsavelIdTemp = null;

// Login via prompt: pede email e senha, autentica no backend!
async function loginPromptUsuario() {
  while (true) {
    const email = prompt("Digite seu e-mail:");
    const senha = prompt("Digite sua senha:");
    if (!email || !senha) {
      alert("Preencha o e-mail e a senha.");
      continue;
    }

    const res = await fetch("http://localhost:8080/api/usuarios/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: email, senha: senha })
    });

    if (!res.ok) {
      alert("Usuário ou senha inválidos. Tente novamente.");
      continue;
    }

    const usuario = await res.json();
    responsavelIdTemp = usuario.id;
    alert("Bem-vindo(a), " + usuario.nome + "!");
    return responsavelIdTemp;
  }
}
// ================== FIM LOGIN POR PROMPT ==================

document.addEventListener("DOMContentLoaded", async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const profissionalId = urlParams.get("id");
  const apiBase = "http://localhost:8080/api";

  if (!profissionalId) {
    alert("Profissional não especificado.");
    return;
  }

  let servicosDoProfissional = [];
  let servicoSelecionado = null;
  let pacienteSelecionado = null;
  let selectedPaymentMethod = null;

  const formatarPreco = valor =>
    valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

  function scrollWithArrows(containerId, prevBtnId, nextBtnId, step = 120) {
    const container = document.getElementById(containerId);
    const prevBtn = document.getElementById(prevBtnId);
    const nextBtn = document.getElementById(nextBtnId);

    if (!container || !prevBtn || !nextBtn) return;

    prevBtn.addEventListener("click", () => {
      container.scrollBy({ left: -step, behavior: "smooth" });
    });

    nextBtn.addEventListener("click", () => {
      container.scrollBy({ left: step, behavior: "smooth" });
    });
  }

  async function carregarEnderecoProfissional(profissionalId) {
    const res = await fetch(`${apiBase}/enderecos?profissionalId=${profissionalId}`);
    const enderecos = await res.json();

    let enderecoFormatado = 'Local não informado';
    if (enderecos && enderecos.length > 0) {
      const endereco = enderecos[0];
      enderecoFormatado = `${endereco.bairro}, ${endereco.cidade}/${endereco.estado}`;
      if (endereco.numero) {
        enderecoFormatado += ` - Nº ${endereco.numero}`;
      }
      if (endereco.referencia) {
        enderecoFormatado += ` (${endereco.referencia})`;
      }
    }
    document.getElementById("professional-location").innerHTML =
      `<i class="fas fa-map-marker-alt"></i> ${enderecoFormatado}`;
  }

  async function carregarProfissional() {
    const res = await fetch(`${apiBase}/profissional/${profissionalId}`);
    const profissional = await res.json();

    document.getElementById("professional-name").textContent = profissional.nome || "Profissional";
    document.getElementById("professional-specialty").innerHTML = `<i class="fas fa-user-md"></i> ${profissional.formacao || "-"}`;
    document.getElementById("professional-registry").innerHTML = `<i class="fas fa-id-card"></i> ${profissional.id || "-"}`;
    document.getElementById("professional-rating").innerHTML = `<i class="fas fa-star"></i> ${profissional.notaMedia?.toFixed(1) || "0.0"}`;
    document.getElementById("professional-image").src = profissional.fotoPerfil || "https://randomuser.me/api/portraits/women/44.jpg";

    await carregarEnderecoProfissional(profissionalId);
  }

  async function carregarServicos() {
    const res = await fetch(`${apiBase}/servico?profissionalId=${profissionalId}`);
    servicosDoProfissional = await res.json();

    const container = document.getElementById("service-cards-container");
    container.innerHTML = "";

    servicosDoProfissional.forEach((servico, index) => {
      const card = document.createElement("div");
      card.className = "service-type-card";
      let badge = index === 0 ? `<span class="badge">Popular</span>` : "";

      card.innerHTML = `
        ${badge}
        <h4>${servico.nome}</h4>
        <p class="price">${formatarPreco(servico.preco)}</p>
        <div class="details">
          <p>Duração: ${servico.duracaoMinutos} min</p>
          <p>${servico.descricao || "Sem descrição."}</p>
        </div>
      `;

      card.addEventListener("click", () => {
        document.querySelectorAll(".service-type-card").forEach(c => c.classList.remove("selected"));
        card.classList.add("selected");
        servicoSelecionado = servico;
        document.getElementById("subtotal-value").textContent = formatarPreco(servico.preco);
        gerarHorarios();
      });

      if (index === 0) {
        card.classList.add("selected");
        servicoSelecionado = servico;
        document.getElementById("subtotal-value").textContent = formatarPreco(servico.preco);
      }

      container.appendChild(card);
    });

    gerarHorarios();
  }

  async function carregarPaciente() {
    const responsavelId = await loginPromptUsuario();
    const res = await fetch(`${apiBase}/paciente?responsavelId=${responsavelId}`);
    const pacientes = await res.json();

    const container = document.getElementById("paciente-cards-container");
    container.innerHTML = "";

    if (!pacientes || pacientes.length === 0) {
      container.innerHTML = "<p>Nenhum paciente cadastrado para esse responsável.</p>";
      return;
    }

    pacientes.forEach((paciente, index) => {
      const card = document.createElement("div");
      card.className = "paciente-card";
      let avatar = `<img class="patient-avatar" src="${paciente.foto || 'https://randomuser.me/api/portraits/lego/7.jpg'}" alt="Avatar do paciente">`;

      card.innerHTML = `
        ${avatar}
        <h4>${paciente.nome}</h4>
        <p><strong>Sexo:</strong> ${paciente.sexo}</p>
        <p><strong>Nasc:</strong> ${new Date(paciente.data_nascimento).toLocaleDateString()}</p>
        <p><strong>Mobilidade:</strong> ${paciente.mobilidade.replace("_", " ")}</p>
      `;

      card.addEventListener("click", () => {
        document.querySelectorAll(".paciente-card").forEach(c => c.classList.remove("selected"));
        card.classList.add("selected");
        pacienteSelecionado = paciente;
      });

      if (index === 0) {
        card.classList.add("selected");
        pacienteSelecionado = paciente;
      }

      container.appendChild(card);
    });
  }

  function gerarDias() {
    const container = document.getElementById("date-items-container");
    container.innerHTML = "";

    const hoje = new Date();
    for (let i = 0; i < 7; i++) {
      const data = new Date(hoje);
      data.setDate(hoje.getDate() + i);

      const item = document.createElement("div");
      item.className = "date-item";
      item.dataset.date = data.toISOString().split("T")[0];
      item.innerHTML = `
        <span class="day-of-week">${data.toLocaleDateString("pt-BR", { weekday: "short" })}</span>
        <span class="day-of-month">${data.getDate().toString().padStart(2, '0')}/${(data.getMonth() + 1).toString().padStart(2, '0')}</span>
      `;

      item.addEventListener("click", () => {
        document.querySelectorAll(".date-item").forEach(el => el.classList.remove("selected"));
        item.classList.add("selected");
      });

      container.appendChild(item);
    }

    document.querySelector(".date-item")?.classList.add("selected");
  }

  function gerarHorarios() {
    const container = document.getElementById("time-items-container");
    container.innerHTML = "";

    const inicio = new Date();
    inicio.setHours(8, 0, 0, 0);
    const fim = new Date();
    fim.setHours(18, 0, 0, 0);

    const duracao = servicoSelecionado?.duracaoMinutos || 60;

    while (inicio < fim) {
      const horaFormatada = inicio.toTimeString().slice(0, 5);
      const item = document.createElement("div");
      item.className = "time-item";
      item.dataset.time = horaFormatada;
      item.innerHTML = `<span class="time">${horaFormatada}</span>`;

      item.addEventListener("click", () => {
        document.querySelectorAll(".time-item").forEach(el => el.classList.remove("selected"));
        item.classList.add("selected");
      });

      container.appendChild(item);
      inicio.setMinutes(inicio.getMinutes() + duracao);
    }

  }

  function ativarPagamentoDinamico() {
    const paymentOptions = document.querySelectorAll('.payment-option');
    const paymentTip = document.querySelector('.payment-tip');

    function mostrarDicaInicial() {
      paymentTip.innerHTML = `Selecione uma forma de pagamento.`;
    }
    mostrarDicaInicial();

    paymentOptions.forEach(option => {
      option.addEventListener('click', function() {
        paymentOptions.forEach(o => o.classList.remove('selected'));
        this.classList.add('selected');
        selectedPaymentMethod = this.dataset.method;

        const iconImg = this.querySelector('img').outerHTML;
        const methodName = this.querySelector('span').innerText;

        paymentTip.innerHTML = `
          <span style="display:inline-flex;align-items:center;gap:6px;">
            ${iconImg}
            <span>
              <span style="background:#eaf5fd;color:#208ee7;padding:3px 14px 3px 12px;border-radius:18px;font-weight:700;box-shadow:0 1px 6px #208ee714;margin-right:8px;">
                ${methodName}
              </span>
              selecionado
            </span>
          </span>
        `;
      });
    });
  }

  function mostrarCardSucesso(servico, paciente, data, hora) {
    const antigo = document.getElementById("card-sucesso-agendamento");
    if (antigo) antigo.remove();

    const dt = new Date(`${data}T${hora}:00`);
    const dataFormatada = dt.toLocaleDateString('pt-BR');
    const horaFormatada = dt.toLocaleTimeString('pt-BR', {hour: '2-digit', minute: '2-digit'});

    const card = document.createElement("div");
    card.id = "card-sucesso-agendamento";
    card.style.background = "#d2ffe3";
    card.style.border = "2px solid #28a745";
    card.style.borderRadius = "10px";
    card.style.padding = "18px";
    card.style.margin = "22px 0";
    card.style.boxShadow = "0 2px 8px 0 #0b58313d";
    card.style.display = "flex";
    card.style.flexDirection = "column";
    card.style.alignItems = "center";
    card.style.fontSize = "1.1rem";
    card.style.fontWeight = "500";
    card.innerHTML = `
      <i class="fas fa-check-circle" style="font-size:2.2rem;color:#1bc070;margin-bottom:10px"></i>
      <span style="font-size:1.2rem;">Agendamento realizado com sucesso!</span>
      <span style="margin:10px 0 0 0;"><b>Paciente:</b> ${paciente.nome}</span>
      <span><b>Serviço:</b> ${servico.nome}</span>
      <span><b>Quando:</b> ${dataFormatada} às ${horaFormatada}</span>
    `;

    document.querySelector(".main-content .container").prepend(card);
  }

  function prepararEnvio() {
    const form = document.getElementById("agendamento-form");

    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      const data = document.querySelector(".date-item.selected")?.dataset.date;
      const hora = document.querySelector(".time-item.selected")?.dataset.time;

      if (!data || !hora || !servicoSelecionado || !pacienteSelecionado || !selectedPaymentMethod) {
        alert("Preencha todos os campos antes de continuar, inclusive a forma de pagamento.");
        return;
      }

      const dataInicio = `${data}T${hora}:00`;

      const responsavelId =
        pacienteSelecionado.responsavel?.id ?? pacienteSelecionado.id ?? null;

      if (responsavelId === null) {
        alert("Erro: não foi possível determinar o ID do responsável.");
        return;
      }

      const payload = {
        profissionalId: parseInt(profissionalId),
        responsavelId,
        idosoId: pacienteSelecionado.id,
        servicoId: servicoSelecionado.id,
        enderecoId: pacienteSelecionado.enderecoId || 1,
        dataInicio,
        metodoPagamento: selectedPaymentMethod
      };

      try {
        const res = await fetch(`${apiBase}/agendamento`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });

        if (res.ok) {
          mostrarCardSucesso(servicoSelecionado, pacienteSelecionado, data, hora);
        } else {
          const erro = await res.text();
          console.error("Erro ao agendar:", erro);
          alert("Erro ao agendar: " + erro);
        }
      } catch (err) {
        console.error("Erro de conexão:", err);
        alert("Erro de conexão com o servidor.");
      }
    });
  }

  // Execução
  await carregarProfissional();
  await carregarServicos();
  await carregarPaciente();
  gerarDias();
  scrollWithArrows("date-items-container", "prev-date", "next-date");
  scrollWithArrows("time-items-container", "prev-time", "next-time");
  ativarPagamentoDinamico();
  prepararEnvio();
});
