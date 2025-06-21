package com.example.AgeCare.controller;

import com.example.AgeCare.model.EspecialidadeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AgeCare.service.EspecialidadeService;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin (
    origins = "*"
)
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService service;

@PostMapping
public ResponseEntity<EspecialidadeModel> salvar(@RequestBody EspecialidadeModel especialidade) {
    return ResponseEntity.ok(service.salvar(especialidade));

}

@GetMapping
public ResponseEntity<List<EspecialidadeModel>> listar() {
    return ResponseEntity.ok(service.listar());
}

@GetMapping ("/{id}")
public ResponseEntity<EspecialidadeModel> buscarCargo (@PathVariable Long id) {
    return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}


@DeleteMapping ("/{id}")
public ResponseEntity<Void> deletar(@PathVariable Long id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();

}

@PutMapping ("/{id}")
public ResponseEntity<EspecialidadeModel> atualizar (@PathVariable Long id, @RequestBody EspecialidadeModel especialidade) {
    return ResponseEntity.ok(service.atualizar(id, especialidade));
}

}