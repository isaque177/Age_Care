package com.example.AgeCare.model;

import com.example.AgeCare.Status.TipoEndereco;
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
@Table(name = "enderecos")
public class EnderecoModel extends AuditableModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String referencia;

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipoEndereco;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "idoso_id")
    @JsonIgnore
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    @JsonIgnore
    private ProfissionalModel profissional;
}
