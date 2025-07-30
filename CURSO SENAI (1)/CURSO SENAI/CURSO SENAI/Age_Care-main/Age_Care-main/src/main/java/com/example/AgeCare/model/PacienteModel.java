package com.example.AgeCare.model;

import java.time.LocalDate;

import com.example.AgeCare.Status.Mobilidade;
import com.example.AgeCare.Status.NivelDependencia;
import com.example.AgeCare.Status.Sexo;
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
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "paciente")
public class PacienteModel extends AuditableModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private String arquivoPdfUrl;
    private String condicoesMedicas;

    @Enumerated(EnumType.STRING)
    private Mobilidade mobilidade;

    @Enumerated(EnumType.STRING)
    private NivelDependencia nivelDependencia;

    private String observacoesEspeciais;
    private String contatoEmergencia;

    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    @JsonIgnore
    private UsuarioModel responsavel;
}
