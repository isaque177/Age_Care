package com.example.AgeCare.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profissionais")
@Schema(description = "Representa um profissional de saúde no sistema.")
public class ProfissionalModel {
    @Id
    private Long id;

    private String formacao;
    private String biografia;
    private String curriculoUrl;
    private BigDecimal notaMedia = BigDecimal.ZERO;
    private int totalAvaliacoes = 0;
    private int totalAtendimentos = 0;

    @Enumerated(EnumType.STRING)
    private StatusDisponibilidade statusDisponibilidade = StatusDisponibilidade.ABERTO;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum StatusDisponibilidade {
        ABERTO, AUSENTE, OCUPADO
    }
}


/* 
@Entity
@Table(name = "profissionais")
@Getter
@Setter
@Schema(description = "Representa um profissional cuidador disponível na plataforma")
public class ProfissionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do profissional", example = "1")
    private Long id;

    @Schema(description = "Quantidade de anos de experiência", example = "5")
    private Integer experienciaAnos;

    @Schema(description = "Formação acadêmica do profissional", example = "Graduação em Enfermagem")
    private String formacao;

    @Schema(description = "Especializações ou áreas de atuação", example = "Cuidados paliativos, Alzheimer, Pós-operatório")
    private String especializacoes;

    @Schema(description = "Valor cobrado por hora", example = "80.00")
    private BigDecimal valorHora;

    @Schema(description = "Valor cobrado por diária", example = "500.00")
    private BigDecimal valorDiaria;

    @Schema(description = "Se o profissional está disponível 24h por dia", example = "true")
    private Boolean disponibilidade24h;

    @Schema(description = "Raio de atendimento em quilômetros", example = "30.0")
    private BigDecimal raioAtendimentoKm;

    @Schema(description = "Biografia e descrição profissional", example = "Profissional com mais de 5 anos de experiência em cuidados domiciliares.")
    private String biografia;

    @Schema(description = "URL do currículo em PDF ou link online", example = "https://exemplo.com/curriculos/maria_cv.pdf")
    private String curriculoUrl;

    @Schema(description = "Nota média recebida em avaliações", example = "4.75")
    private Double notaMedia;

    @Schema(description = "Quantidade total de avaliações recebidas", example = "20")
    private Integer totalAvaliacoes;

    @Schema(description = "Total de atendimentos realizados", example = "50")
    private Integer totalAtendimentos;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_aprovacao")
    @Schema(description = "Status de aprovação do cadastro", example = "aprovado", allowableValues = {"pendente", "aprovado", "rejeitado"})
    private StatusAprovacao statusAprovacao = StatusAprovacao.pendente;

    public enum StatusAprovacao {
        pendente, aprovado, rejeitado
    }
}
*/