package com.example.AgeCare.model;

import java.time.LocalDateTime;

import com.example.AgeCare.Status.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;

@Entity
@Table(name = "agendamentos")
public class AgendamentoModel extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "profissional_id", nullable = false)
    @JsonIgnore
    private ProfissionalModel profissional;

    @ManyToOne @JoinColumn(name = "idoso_id", nullable = false)
    @JsonIgnore
    private PacienteModel paciente;

    @ManyToOne @JoinColumn(name = "responsavel_id", nullable = false)
    @JsonIgnore
    private UsuarioModel responsavel;

    @ManyToOne @JoinColumn(name = "endereco_id", nullable = false)
    @JsonIgnore
    private EnderecoModel endereco;

    @ManyToOne @JoinColumn(name = "servico_id", nullable = false)
    @JsonIgnore
    private ServicoModel servico;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status = StatusAgendamento.ESPERA;

    private LocalDateTime dataCancelamento;
    private String motivoCancelamento;

    @AssertTrue(message = "dataFim deve ser após dataInicio")
    public boolean isDataFimValida() {
        return dataFim != null && dataInicio != null && dataFim.isAfter(dataInicio);
    }

    // === Getters e Setters ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProfissionalModel getProfissional() { return profissional; }
    public void setProfissional(ProfissionalModel profissional) { this.profissional = profissional; }

    public PacienteModel getPaciente() { return paciente; }
    public void setPaciente(PacienteModel paciente) { this.paciente = paciente; }

    public UsuarioModel getResponsavel() { return responsavel; }
    public void setResponsavel(UsuarioModel responsavel) { this.responsavel = responsavel; }

    public EnderecoModel getEndereco() { return endereco; }
    public void setEndereco(EnderecoModel endereco) { this.endereco = endereco; }

    public ServicoModel getServico() { return servico; }
    public void setServico(ServicoModel servico) { this.servico = servico; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }

    public StatusAgendamento getStatus() { return status; }
    public void setStatus(StatusAgendamento status) { this.status = status; }

    public LocalDateTime getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDateTime dataCancelamento) { this.dataCancelamento = dataCancelamento; }

    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }

    
}
