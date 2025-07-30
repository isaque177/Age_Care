// ServicoModel.java
package com.example.AgeCare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
 @Setter
@Entity
@Table(name = "servicos")
public class ServicoModel extends AuditableModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonIgnore
    private String nome;

    private String descricao;

    @Column(name = "duracao_minutos")
    @JsonIgnore
    private Integer duracaoMinutos;

    @Column(nullable = false)
    @JsonIgnore
    private Double preco;

    @Column(nullable = false)
    @JsonIgnore
    private Boolean ativo = true;
}
