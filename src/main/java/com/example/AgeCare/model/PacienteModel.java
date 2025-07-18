package com.example.AgeCare.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "idosos")
public class PacienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Integer idade;
    private String sexo;
    private String condicao;
    private String medicamentos;
    private String observacoes;
    private String alergias;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private UsuarioModel cliente;

    // Foreign Key para Profissional
    // A coluna no banco de dados será 'profissional_id'. Pode ser nula.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = true)
    private ProfissionalModel profissional;

    // Foreign Key para Paciente
    // A coluna no banco de dados será 'paciente_id'. Pode ser nula.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = true)
    private PacienteModel paciente;


}