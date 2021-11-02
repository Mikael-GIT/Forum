package br.com.alura.forum.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.forum.models.Topico;
import br.com.alura.forum.repositories.TopicoRepository;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    public List<Topico> listar() {
        return repository.findAll();
    }

    public List<Topico> listarPorNomeCurso(String nomeDoCurso) {
        return repository.findByCurso_Nome(nomeDoCurso);
    }
}