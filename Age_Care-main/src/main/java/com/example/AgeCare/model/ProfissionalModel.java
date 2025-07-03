package com.example.AgeCare.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profissionais")
@Getter
@Setter

public class ProfissionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer experienciaAnos;
    private String formacao;
    private String especializacoes;
    private BigDecimal valorHora;
    private BigDecimal valorDiaria;
    private Boolean disponibilidade24h ;
    private BigDecimal raioAtendimentoKm;
    private String biografia;
    private String curriculoUrl;
    private Double notaMedia ;
    private Integer totalAvaliacoes ;
    private Integer totalAtendimentos ;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status_aprovacao", columnDefinition = "ENUM('pendente', 'aprovado', 'rejeitado') DEFAULT 'pendente'")
    private StatusAprovacao statusAprovacao = StatusAprovacao.pendente;
    
    
    public enum StatusAprovacao {
        pendente, aprovado, rejeitado
    }
   
}
