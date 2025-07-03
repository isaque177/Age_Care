package com.example.AgeCare.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter

public class UsuarioModel {

      public UsuarioModel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String senhaHash;
    private String telefone;
    private String celular;
    private String fotoPerfil;
    private LocalDate dataNascimento;
    private String cpf;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;


}
