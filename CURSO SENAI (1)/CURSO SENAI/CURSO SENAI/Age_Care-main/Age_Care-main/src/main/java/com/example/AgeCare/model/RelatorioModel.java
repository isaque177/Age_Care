package com.example.AgeCare.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "relatorios")
public class RelatorioModel extends AuditableModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private AgendamentoModel agendamento;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    @JsonIgnore
    private ProfissionalModel profissional;

    private String relatorio;
    private String medicamentosAdministrados;
    private String intercorrencias;
    private String observacoesComportamento;
    private LocalDateTime dataRelatorio;
}
