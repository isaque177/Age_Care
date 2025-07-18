package com.example.AgeCare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AgeCare.model.AgendamentoModel;
import com.example.AgeCare.service.AgendamentoService;

@RestController
@RequestMapping("/api/agendamento")

public class AgendamentoController {

    @Autowired
    private AgendamentoService service;


    @PostMapping
    public ResponseEntity<AgendamentoModel> salvar (AgendamentoModel agendamento){
        return ResponseEntity.ok(service.salvar(agendamento));
    }

    @GetMapping
    public ResponseEntity <List<AgendamentoModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoModel> buscarPorId(Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoModel> atualizar(Long id, AgendamentoModel agendamento) {
        return ResponseEntity.ok(service.atualizar(id, agendamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
