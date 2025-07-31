package com.example.AgeCare.controller;

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

import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.service.ServicoService;


@RestController
@RequestMapping("/api/servico")
@CrossOrigin(origins = "*")
public class ServicoController {    

    @Autowired
    private ServicoService service;

    @PostMapping
    public ResponseEntity<ServicoModel> salvar(@RequestBody ServicoModel servico) {
        return ResponseEntity.ok(service.salvar(servico));
    }

    @GetMapping
    public ResponseEntity<?> listarPorProfissional(@RequestParam(required = false) Long profissionalId) {
    if (profissionalId != null) {
        return ResponseEntity.ok(service.buscarPorProfissionalId(profissionalId));
    } else {
        return ResponseEntity.ok(service.listar());
    }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
