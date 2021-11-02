package br.com.alura.forum.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.models.Curso;
import br.com.alura.forum.models.Topico;
import br.com.alura.forum.models.dtos.TopicoDto;
import br.com.alura.forum.repositories.TopicoRepository;
import br.com.alura.forum.services.TopicoService;

@RestController
public class TopicosController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping("/topicos")
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoService.listar();
            return TopicoDto.converter(topicos);
        }
        return TopicoDto.converter(topicoService.listarPorNomeCurso(nomeCurso));
    }
}