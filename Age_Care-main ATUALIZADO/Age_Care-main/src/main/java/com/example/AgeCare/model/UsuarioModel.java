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
@Table(name = "cliente")
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

    public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}

public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}

public String getSenha() {
    return senha;
}
public void setSenha(String senha) {
    this.senha = senha;
}

public String getTelefone() {
    return telefone;
}
public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public TipoUsuario getTipoUsuario() {
    return tipoUsuario;
}
public void setTipoUsuario(TipoUsuario tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
}

public String getFotoPerfil() {
    return fotoPerfil;
}
public void setFotoPerfil(String fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
}

public LocalDate getDataNascimento() {
    return dataNascimento;
}
public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
}

public String getCpf() {
    return cpf;
}
public void setCpf(String cpf) {
    this.cpf = cpf;
}

public List<PacienteModel> getPacientes() {
    return pacientes;
}
public void setPacientes(List<PacienteModel> pacientes) {
    this.pacientes = pacientes;
}

public List<AgendamentoModel> getAgendamentos() {
    return agendamentos;
}
public void setAgendamentos(List<AgendamentoModel> agendamentos) {
    this.agendamentos = agendamentos;
}

}
