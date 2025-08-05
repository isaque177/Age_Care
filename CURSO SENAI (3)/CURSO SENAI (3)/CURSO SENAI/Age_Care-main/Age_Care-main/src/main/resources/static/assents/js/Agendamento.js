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

  async function carregarProfissional() {
    const res = await fetch(`${apiBase}/profissional/${profissionalId}`);
    const profissional = await res.json();

    document.getElementById("professional-name").textContent = profissional.nome || "Profissional";
    document.getElementById("professional-specialty").innerHTML = `<i class="fas fa-user-md"></i> ${profissional.formacao || "-"}`;
    document.getElementById("professional-location").innerHTML = `<i class="fas fa-map-marker-alt"></i> Local não informado`;
    document.getElementById("professional-registry").innerHTML = `<i class="fas fa-id-card"></i> ${profissional.id || "-"}`;
    document.getElementById("professional-rating").innerHTML = `<i class="fas fa-star"></i> ${profissional.notaMedia?.toFixed(1) || "0.0"}`;
    document.getElementById("professional-image").src = profissional.fotoPerfil || "https://via.placeholder.com/300x200";
  }

  async function carregarServicos() {
    const res = await fetch(`${apiBase}/servico?profissionalId=${profissionalId}`);
    servicosDoProfissional = await res.json();

    const container = document.getElementById("service-cards-container");
    container.innerHTML = "";

    servicosDoProfissional.forEach((servico, index) => {
      const card = document.createElement("div");
      card.className = "service-type-card";
      card.innerHTML = `
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
    const res = await fetch(`${apiBase}/paciente`);
    const pacientes = await res.json();

    const container = document.getElementById("paciente-cards-container");
    container.innerHTML = "";

    if (!pacientes || pacientes.length === 0) {
      container.innerHTML = "<p>Nenhum paciente cadastrado.</p>";
      return;
    }

    pacientes.forEach((paciente, index) => {
      const card = document.createElement("div");
      card.className = "paciente-card";
      card.innerHTML = `
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

 function prepararEnvio() {
  const form = document.getElementById("agendamento-form");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const data = document.querySelector(".date-item.selected")?.dataset.date;
    const hora = document.querySelector(".time-item.selected")?.dataset.time;

    if (!data || !hora || !servicoSelecionado || !pacienteSelecionado) {
      alert("Preencha todos os campos antes de continuar.");
      return;
    }

    const dataInicio = `${data}T${hora}:00`;
    const fim = new Date(new Date(dataInicio).getTime() + servicoSelecionado.duracaoMinutos * 60000);
    const dataFim = fim.toISOString();

    console.log("Paciente selecionado:", pacienteSelecionado);

    const responsavelId =
      pacienteSelecionado.responsavel?.id ?? pacienteSelecionado.id ?? null;

    console.log("Responsável ID final:", responsavelId);

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
      dataFim
    };

    console.log("Payload final sendo enviado:", payload);

    try {
      const res = await fetch(`${apiBase}/agendamento`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (res.ok) {
        alert("Agendamento realizado com sucesso!");
        window.location.reload();
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
  prepararEnvio();
});
