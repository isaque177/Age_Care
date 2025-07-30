package com.example.AgeCare.model;

import java.util.List;

import com.example.AgeCare.Status.StatusDisponibilidade;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "profissionais")
public class ProfissionalModel extends AuditableModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formacao;
    private String biografia;
    private String curriculoUrl;

    private double notaMedia;
    private int totalAvaliacoes;
    private int totalAtendimentos;

    @Enumerated(EnumType.STRING)
    private StatusDisponibilidade statusDisponibilidade;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoModel servico;

    @OneToMany(mappedBy = "profissional")
    @JsonIgnore
    private List<AgendamentoModel> agendamentos;

    @ManyToMany
@JoinTable(
    name = "profissional_especializacoes",
    joinColumns = @JoinColumn(name = "profissional_id"),
    inverseJoinColumns = @JoinColumn(name = "especializacao_id")
)
private List<EspecialidadeModel> especialidades;

    @OneToMany(mappedBy = "profissional")
    @JsonIgnore
    private List<EnderecoModel> enderecos;
}
