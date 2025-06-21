package com.example.AgeCare.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "enderecos")
@Getter
@Setter
@Entity
public class EnderecoModel {

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
    private ClienteModel cliente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = true)
    private ProfissionalModel profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = true)
    private PacienteModel paciente;



}