package com.example.AgeCare.controller;


import com.example.AgeCare.model.EnderecoModel;
import com.example.AgeCare.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @PostMapping
    public ResponseEntity<EnderecoModel> salvar(@RequestBody EnderecoModel endereco) {
        return ResponseEntity.ok(service.salvar(endereco));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listar() {
        return ResponseEntity.ok(service.listar());

    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoModel> atualizar(@PathVariable Long id, @RequestBody EnderecoModel endereco) {
        return ResponseEntity.ok(service.atualizar(id, endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
