package com.example.AgeCare.model;


import java.time.LocalDateTime;

import com.example.AgeCare.Status.TipoEndereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enderecos")
@Schema(description = "Representa um endereço cadastrado no sistema.")
public class EnderecoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsuarioModel usuario;

    @ManyToOne
    private PacienteModel idoso;

    @ManyToOne
    private ProfissionalModel profissional;

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipoEndereco = TipoEndereco.RESIDENCIAL;

    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String referencia;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
    /* 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String pontoReferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private UsuarioModel cliente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = true)
    private ProfissionalModel profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = true)
    private PacienteModel paciente;

*/ 
