package com.ecopower.controller;

import com.ecopower.model.Ocorrencia;
import com.ecopower.service.OcorrenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OcorrenciaController {

    private final OcorrenciaService service;

    public OcorrenciaController(OcorrenciaService service) {
        this.service = service;
    }

    @GetMapping("/ocorrencias")
    public List<Ocorrencia> listar() {
        return service.listar();
    }

    @GetMapping("/ocorrencias/{id}")
    public Ocorrencia buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/ocorrencias")
    public Ocorrencia criar(@RequestBody Ocorrencia ocorrencia) {
        return service.criar(ocorrencia);
    }

    @PutMapping("/ocorrencias/{id}")
    public Ocorrencia atualizar(@PathVariable Long id,
                                @RequestBody Ocorrencia ocorrencia) {

        return service.atualizar(id, ocorrencia);
    }

    @DeleteMapping("/ocorrencias/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "Ocorrência removida com sucesso!";
    }
}