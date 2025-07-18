package com.example.AgeCare.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "relatorios")
@Schema(description = "Relatório de atendimento gerado por um profissional.")
public class RelatorioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private AgendamentoModel agendamento;

    @ManyToOne(optional = false)
    private ProfissionalModel profissional;

    private String relatorio;
    private String medicamentosAdministrados;
    private String intercorrencias;
    private String observacoesComportamento;

    private LocalDateTime dataRelatorio = LocalDateTime.now();
}  
