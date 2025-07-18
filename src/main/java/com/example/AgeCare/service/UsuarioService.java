package com.example.AgeCare.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AgeCare.model.UsuarioModel;
import com.example.AgeCare.repository.UsuarioRepository;

@Service
public class UsuarioService {

    
     @Autowired
    private UsuarioRepository repository;

    
    public List<UsuarioModel> listar(){
        return repository.findAll();
    }

    public UsuarioModel salvar(UsuarioModel usuario){
        return repository.save(usuario);
    }

    public Optional<UsuarioModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public UsuarioModel atualizar(Long id, UsuarioModel usuario) {
        usuario.setId(id);
        return repository.save(usuario);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }


}