package com.example.AgeCare.model;

import java.time.LocalDate;
import java.util.List;

import com.example.AgeCare.Status.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "usuarios")
public class UsuarioModel extends AuditableModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private String fotoPerfil;
    private LocalDate dataNascimento;
    private String cpf;

    @OneToMany(mappedBy = "responsavel")
    @JsonIgnore
    private List<PacienteModel> pacientes;

    @OneToMany(mappedBy = "responsavel")
    @JsonIgnore
    private List<AgendamentoModel> agendamentos;
}
