package com.example.AgeCare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.service.ProfissionalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/profissional")
@CrossOrigin (origins = "*")
@Tag(name = "Profissional", description = "Endpoints para gerenciamento de profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;

    @Operation(summary = "Salvar profissional", description = "Cria e salva um novo profissional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional salvo com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProfissionalModel> salvar(
        @RequestBody ProfissionalModel profissional) {
        return ResponseEntity.ok(service.salvar(profissional));
    }

    @Operation(summary = "Listar profissionais", description = "Retorna uma lista com todos os profissionais cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProfissionalModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar profissional por ID", description = "Retorna os dados de um profissional específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional encontrado"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalModel> buscarPorId(
        @Parameter(description = "ID do profissional a ser consultado") 
        @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar profissional", description = "Atualiza os dados de um profissional existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalModel> atualizar(
        @Parameter(description = "ID do profissional a ser atualizado")
        @PathVariable Long id,
        @RequestBody ProfissionalModel profissional) {
        return ResponseEntity.ok(service.atualizar(id, profissional));
    }

    @Operation(summary = "Deletar profissional", description = "Remove um profissional pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Profissional deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do profissional a ser deletado")
        @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
