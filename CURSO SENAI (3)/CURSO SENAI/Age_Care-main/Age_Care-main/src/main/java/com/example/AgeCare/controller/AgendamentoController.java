// src/main/java/com/example/AgeCare/controller/AgendamentoController.java
package com.example.AgeCare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AgeCare.dto.AgendamentoDTO;
import com.example.AgeCare.model.AgendamentoModel;
import com.example.AgeCare.model.EnderecoModel;
import com.example.AgeCare.model.PacienteModel;
import com.example.AgeCare.model.ProfissionalModel;
import com.example.AgeCare.model.ServicoModel;
import com.example.AgeCare.model.UsuarioModel;
import com.example.AgeCare.repository.AgendamentoRepository;
import com.example.AgeCare.repository.EnderecoRepository;
import com.example.AgeCare.repository.PacienteRepository;
import com.example.AgeCare.repository.ProfissionalRepository;
import com.example.AgeCare.repository.ServicoRepository;
import com.example.AgeCare.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
@CrossOrigin(origins = "*")
@Validated
public class AgendamentoController {

    @Autowired private AgendamentoRepository agendamentoRepo;
    @Autowired private ProfissionalRepository profissionalRepo;
    @Autowired private ServicoRepository servicoRepo;
    @Autowired private PacienteRepository pacienteRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private EnderecoRepository enderecoRepo;

    @GetMapping
    public List<AgendamentoModel> listar() {
        return agendamentoRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody AgendamentoDTO dto) {
        // Buscas
        ProfissionalModel prof = profissionalRepo.findById(dto.getProfissionalId())
            .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));
        ServicoModel serv = servicoRepo.findById(dto.getServicoId())
            .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        PacienteModel pac = pacienteRepo.findById(dto.getIdosoId())
            .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        UsuarioModel resp = usuarioRepo.findById(dto.getResponsavelId())
            .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));
        EnderecoModel end = enderecoRepo.findById(dto.getEnderecoId())
            .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));

        // Montagem
        AgendamentoModel ag = new AgendamentoModel();
        ag.setDataInicio(dto.getDataInicio());
        ag.setDataFim(dto.getDataFim());
        ag.setProfissional(prof);
        ag.setServico(serv);
        ag.setPaciente(pac);
        ag.setResponsavel(resp);
        ag.setEndereco(end);
        ag.setStatus(dto.getStatus());

        // Salva
        return ResponseEntity.ok(agendamentoRepo.save(ag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return agendamentoRepo.findById(id)
            .map(a -> { agendamentoRepo.delete(a); return ResponseEntity.ok().<Void>build(); })
            .orElse(ResponseEntity.notFound().build());
    }
}
