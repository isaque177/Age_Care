package com.example.AgeCare.dto;

import java.time.LocalDateTime;

import com.example.AgeCare.Status.StatusAgendamento;


public class AgendamentoDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long profissionalId;
    private Long servicoId;
    private Long idosoId;
    private Long responsavelId;
    private Long enderecoId;
    private StatusAgendamento status = StatusAgendamento.ESPERA;

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

public LocalDateTime getDataInicio() {
    return dataInicio;
}
public void setDataInicio(LocalDateTime dataInicio) {
    this.dataInicio = dataInicio;
}

public LocalDateTime getDataFim() {
    return dataFim;
}
public void setDataFim(LocalDateTime dataFim) {
    this.dataFim = dataFim;
}

public Long getProfissionalId() {
    return profissionalId;
}
public void setProfissionalId(Long profissionalId) {
    this.profissionalId = profissionalId;
}

public Long getServicoId() {
    return servicoId;
}
public void setServicoId(Long servicoId) {
    this.servicoId = servicoId;
}

public Long getIdosoId() {
    return idosoId;
}
public void setIdosoId(Long idosoId) {
    this.idosoId = idosoId;
}

public Long getResponsavelId() {
    return responsavelId;
}
public void setResponsavelId(Long responsavelId) {
    this.responsavelId = responsavelId;
}

public Long getEnderecoId() {
    return enderecoId;
}
public void setEnderecoId(Long enderecoId) {
    this.enderecoId = enderecoId;
}

public StatusAgendamento getStatus() {
    return status;
}
public void setStatus(StatusAgendamento status) {
    this.status = status;
}

}
