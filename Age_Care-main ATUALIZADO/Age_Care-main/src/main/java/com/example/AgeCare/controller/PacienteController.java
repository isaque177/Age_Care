package com.example.AgeCare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AgeCare.model.PacienteModel;
import com.example.AgeCare.service.PacienteService;

@RestController
@RequestMapping("/api/paciente")
@CrossOrigin (origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteModel> salvar(@RequestBody PacienteModel paciente){
        return ResponseEntity.ok(service.salvar(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteModel>> listar(
    @RequestParam(required = false) Long responsavelId
    ) {
    if (responsavelId != null) {
        return ResponseEntity.ok(service.buscarPorResponsavelId(responsavelId));
    } else {
        return ResponseEntity.ok(service.listar());
    }
    }


    @GetMapping("/{id}")
    public ResponseEntity<PacienteModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteModel> atualizar(@PathVariable Long id,@RequestBody PacienteModel paciente) {
        return ResponseEntity.ok(service.atualiza(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}