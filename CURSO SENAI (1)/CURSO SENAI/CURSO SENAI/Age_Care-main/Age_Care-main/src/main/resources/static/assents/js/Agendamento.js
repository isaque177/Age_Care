document.addEventListener("DOMContentLoaded", async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const profissionalId = urlParams.get("id");

    if (!profissionalId) {
        alert("Profissional não especificado.");
        return;
    }

    const apiBase = "/api";
    let servicoSelecionado = null;
    let pacienteSelecionado = null;

    const formatarPreco = valor =>
        valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

    async function carregarProfissional() {
        const res = await fetch(`${apiBase}/profissional/${profissionalId}`);
        const profissional = await res.json();

        document.getElementById("professional-name").textContent = profissional.nome || "Profissional";
        document.getElementById("professional-specialty").innerHTML = `<i class="fas fa-user-md"></i> ${profissional.formacao || "-"}`;
        document.getElementById("professional-location").innerHTML = `<i class="fas fa-map-marker-alt"></i> Local não informado`;
        document.getElementById("professional-registry").innerHTML = `<i class="fas fa-id-card"></i> ${profissional.id || "ID"}`;
        document.getElementById("professional-rating").innerHTML = `<i class="fas fa-star"></i> ${profissional.notaMedia.toFixed(1) || "0.0"}`;
        document.getElementById("professional-image").src = profissional.fotoPerfil || "https://via.placeholder.com/300x200";
    }

    async function carregarServicoDoProfissional() {
        const res = await fetch(`${apiBase}/servico?profissionalId=${profissionalId}`);
        const servico = await res.json();

        servicoSelecionado = servico;
        const container = document.getElementById("service-cards-container");
        container.innerHTML = "";

        const card = document.createElement("div");
        card.className = "service-card selected";
        card.innerHTML = `<h4>${servico.nome}</h4><p>${formatarPreco(servico.preco)}</p>`;
        container.appendChild(card);

        document.getElementById("subtotal-value").textContent = formatarPreco(servico.preco);
    }

    async function carregarPaciente() {
        const res = await fetch(`${apiBase}/pacientes`);
        const pacientes = await res.json();
        pacienteSelecionado = pacientes[0]; // usar primeiro paciente
    }

    function gerarDias() {
        const container = document.getElementById("date-items-container");
        const hoje = new Date();
        for (let i = 0; i < 7; i++) {
            const data = new Date(hoje);
            data.setDate(hoje.getDate() + i);
            const item = document.createElement("div");
            item.className = "date-item";
            item.textContent = data.toLocaleDateString("pt-BR", { weekday: "short", day: "2-digit", month: "2-digit" });
            item.dataset.date = data.toISOString().split("T")[0];
            item.addEventListener("click", () => {
                document.querySelectorAll(".date-item").forEach(el => el.classList.remove("selected"));
                item.classList.add("selected");
            });
            container.appendChild(item);
        }
    }

    function gerarHorarios() {
        const container = document.getElementById("time-items-container");
        const horarios = ["08:00", "09:00", "10:00", "14:00", "15:00", "16:00"];
        horarios.forEach(horario => {
            const item = document.createElement("div");
            item.className = "time-item";
            item.textContent = horario;
            item.dataset.time = horario;
            item.addEventListener("click", () => {
                document.querySelectorAll(".time-item").forEach(el => el.classList.remove("selected"));
                item.classList.add("selected");
            });
            container.appendChild(item);
        });
    }

    function prepararEnvio() {
        document.getElementById("agendamento-form").addEventListener("submit", async e => {
            e.preventDefault();

            const data = document.querySelector(".date-item.selected")?.dataset.date;
            const hora = document.querySelector(".time-item.selected")?.dataset.time;

            if (!data || !hora || !servicoSelecionado || !pacienteSelecionado) {
                alert("Preencha todos os dados do agendamento.");
                return;
            }

            const dataInicio = `${data}T${hora}:00`;
            const fim = new Date(new Date(dataInicio).getTime() + servicoSelecionado.duracaoMinutos * 60000);
            const dataFim = fim.toISOString();

            const payload = {
                profissionalId: parseInt(profissionalId),
                responsavelId: pacienteSelecionado.responsavel.id,
                idosoId: pacienteSelecionado.id,
                servicoId: servicoSelecionado.id,
                enderecoId: pacienteSelecionado.enderecoId || 2,
                dataInicio,
                dataFim
            };

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
                alert("Erro ao agendar: " + erro);
            }
        });
    }

    await carregarProfissional();
    await carregarServicoDoProfissional();
    await carregarPaciente();
    gerarDias();
    gerarHorarios();
    prepararEnvio();
});
