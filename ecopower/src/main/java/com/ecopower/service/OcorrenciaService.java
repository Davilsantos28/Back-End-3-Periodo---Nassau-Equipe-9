package com.ecopower.service;

import com.ecopower.model.Ocorrencia;
import com.ecopower.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository repository;

    public OcorrenciaService(OcorrenciaRepository repository) {
        this.repository = repository;
    }

    public List<Ocorrencia> listar() {
        return repository.findAll();
    }

    public Ocorrencia buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Ocorrencia criar(Ocorrencia ocorrencia) {
        return repository.save(ocorrencia);
    }

    public Ocorrencia atualizar(Long id, Ocorrencia novaOcorrencia) {

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

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}