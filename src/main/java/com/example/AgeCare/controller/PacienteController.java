package com.example.AgeCare.controller;

import com.example.AgeCare.model.PacienteModel;
import com.example.AgeCare.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin (origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteModel> salvar(@RequestBody PacienteModel paciente){
        return ResponseEntity.ok(service.salvar(paciente));
    }

    @GetMapping
    public ResponseEntity <List<PacienteModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteModel> atualizar(@PathVariable Long id,@RequestBody PacienteModel paciente) {
        return ResponseEntity.ok(service.atualizar(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}