package com.example.AgeCare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AgeCare.model.Usuario;
import com.example.AgeCare.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RequestMapping ("/api/usuario")
@RestController
@CrossOrigin (origins = "*")

public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> registrar (@RequestBody Usuario usuario){
       return ResponseEntity.ok(service.registrarUsuario(usuario)); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar (@PathVariable Long id){
        return service.buscarPorid(id)
        .map(ResponseEntity:: ok)
        .orElse(ResponseEntity.notFound().build());
    }
  

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(service.atualizarUsuario(id, usuario));

    }

    @GetMapping
    public ResponseEntity <List<Usuario>> listar(){
        return ResponseEntity.ok(service.listarUsuarios());

    }
    
}