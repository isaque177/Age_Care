package com.example.AgeCare.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.AgeCare.Status.TipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
@Schema(description = "Representa um usuário do sistema, que pode ser cuidador ou responsável.")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do usuário.")
    private Long id;

    @NotBlank
    @Schema(description = "Nome completo do usuário.")
    private String nome;

    @Email
    @Column(unique = true, nullable = false)
    @Schema(description = "E-mail do usuário, utilizado para login.")
    private String email;

    @NotBlank
    @Size(min = 8)
    @Schema(description = "Senha do usuário, armazenada de forma segura.")
    private String senha;

    @Schema(description = "Telefone para contato.")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo do usuário: cuidador ou responsável.")
    private TipoUsuario tipoUsuario;

    @Schema(description = "URL da foto de perfil do usuário.")
    private String fotoPerfil;

    @Schema(description = "Data de nascimento.")
    private LocalDate dataNascimento;

    @Column(unique = true)
    @Schema(description = "CPF do usuário.")
    private String cpf;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

    
/* 
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
*/