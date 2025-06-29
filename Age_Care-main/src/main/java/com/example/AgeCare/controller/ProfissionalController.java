package com.example.AgeCare.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.service.ProfissionalService;

@RestController
@RequestMapping("/api/profissional")
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;

    @PostMapping
    public ResponseEntity<ProfissionalModel> salvar(@RequestBody ProfissionalModel profissional){
        return ResponseEntity.ok(service.salvar(profissional));
    }

    @GetMapping
    public ResponseEntity <List<ProfissionalModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalModel> atualizar(@PathVariable Long id,@RequestBody ProfissionalModel profissional) {
        return ResponseEntity.ok(service.atualizar(id, profissional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
