package com.example.AgeCare.model;

import java.time.LocalDateTime;

import com.example.AgeCare.Status.StatusAgendamento;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Agendamentos")
@Getter
@Setter
@Entity
public class AgendamentoModel {
    
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalModel profissional;

    @ManyToOne
    @JoinColumn(name = "idoso_id", nullable = false)
    private PacienteModel idoso; 
    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    private UsuarioModel responsavel;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private EnderecoModel endereco;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private ServicoModel servico;

    private LocalDateTime data_inicio;

    private LocalDateTime data_fim;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status = StatusAgendamento.ESPERA;

    private LocalDateTime data_criacao;

    private LocalDateTime data_cancelamento;

    private String motivo_cancelamento;



}
