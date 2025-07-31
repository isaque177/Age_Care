
package com.example.AgeCare.controller;


import com.example.AgeCare.model.RelatorioModel;
import com.example.AgeCare.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorio")
@CrossOrigin (origins = "*")
public class RelatorioController {


    @Autowired
    private RelatorioService service;


    @PostMapping
    public ResponseEntity<RelatorioModel> salvar(@RequestBody RelatorioModel relatorio) {
        return ResponseEntity.ok(service.salvar(relatorio));
    }

    @GetMapping
    public ResponseEntity<List<RelatorioModel>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioModel> buscarPorid(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatorioModel> atualizar(@PathVariable Long id, @RequestBody RelatorioModel relatorio) {
        return ResponseEntity.ok(service.atualizar(id, relatorio));

    }

    public ResponseEntity<Void>deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    /// teste
}
