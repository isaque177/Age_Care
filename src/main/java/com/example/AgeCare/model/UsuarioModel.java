package com.example.AgeCare.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Schema(description = "Representa um usuário da plataforma (cuidador ou responsável)")
public class UsuarioModel {

    public UsuarioModel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "Maria da Silva")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "maria.silva@example.com")
    private String email;

    @Schema(description = "Hash da senha criptografada", example = "$2a$10$abc123def456...")
    private String senhaHash;

    @Schema(description = "Número de telefone fixo", example = "1122334455")
    private String telefone;

    @Schema(description = "Número de celular", example = "11999998888")
    private String celular;

    @Schema(description = "URL da foto de perfil", example = "https://exemplo.com/perfil/maria.jpg")
    private String fotoPerfil;

    @Schema(description = "Data de nascimento do usuário", example = "1985-06-15")
    private LocalDate dataNascimento;

    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Data de criação do registro", example = "2024-06-01")
    private LocalDate dataCriacao;

    @Schema(description = "Data da última atualização", example = "2024-06-15")
    private LocalDate dataAtualizacao;
}
