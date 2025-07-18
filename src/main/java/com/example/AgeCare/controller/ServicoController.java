package com.example.AgeCare.controller;


import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servico")
@CrossOrigin (origins = "*")
public class ServicoController {


    @Autowired
    private ServicoService service;


    @PostMapping
    public ResponseEntity<ServicoModel> salvar(@RequestBody ServicoModel servico) {
        return ResponseEntity.ok(service.salvar(servico));
    }

    @GetMapping
    public ResponseEntity<List<ServicoModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoModel> buscarPorid(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoModel> atualizar(@PathVariable Long id, @RequestBody ServicoModel servico) {
        return ResponseEntity.ok(service.atualizar(id, servico));

    }

    public ResponseEntity<Void>deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    /// teste
}
