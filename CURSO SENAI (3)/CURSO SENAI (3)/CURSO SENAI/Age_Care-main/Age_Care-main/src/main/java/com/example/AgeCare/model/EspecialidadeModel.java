// EspecialidadeModel.java
package com.example.AgeCare.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "especializacoes")
public class EspecialidadeModel extends AuditableModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

@ManyToMany(mappedBy = "especialidades")
@com.fasterxml.jackson.annotation.JsonIgnore
private List<ProfissionalModel> profissionais;



}
