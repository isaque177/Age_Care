package com.example.AgeCare.service;

import com.example.AgeCare.model.PacienteModel;
import com.example.AgeCare.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<PacienteModel> listar(){
        return repository.findAll();
    }

    public PacienteModel salvar(PacienteModel paciente){
        return repository.save(paciente);
    }

    public Optional<PacienteModel> buscarPorId(Long id){
        return repository.findById(id);
    }

    public PacienteModel atualizar(Long id, PacienteModel paciente) {
        paciente.setId(id);
        return repository.save(paciente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}