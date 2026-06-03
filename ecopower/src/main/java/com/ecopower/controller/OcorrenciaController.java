package com.ecopower.controller;

import com.ecopower.model.Ocorrencia;
import com.ecopower.repository.OcorrenciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OcorrenciaController {

    private final OcorrenciaRepository repository;

    public OcorrenciaController(OcorrenciaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ocorrencias")
    public List<Ocorrencia> listar() {
        return repository.findAll();
    }

    @GetMapping("/ocorrencias/{id}")
    public Ocorrencia buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/ocorrencias")
    public Ocorrencia criar(@RequestBody Ocorrencia ocorrencia) {
        return repository.save(ocorrencia);
    }

    @PutMapping("/ocorrencias/{id}")
    public Ocorrencia atualizar(@PathVariable Long id, @RequestBody Ocorrencia novaOcorrencia) {

        Ocorrencia ocorrencia = repository.findById(id).orElse(null);

        if (ocorrencia == null) {
            return null;
        }

        ocorrencia.setTitulo(novaOcorrencia.getTitulo());
        ocorrencia.setDescricao(novaOcorrencia.getDescricao());
        ocorrencia.setEndereco(novaOcorrencia.getEndereco());
        ocorrencia.setCategoria(novaOcorrencia.getCategoria());
        ocorrencia.setStatus(novaOcorrencia.getStatus());

        return repository.save(ocorrencia);
    }

    @DeleteMapping("/ocorrencias/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "Ocorrência removida com sucesso!";
    }
}