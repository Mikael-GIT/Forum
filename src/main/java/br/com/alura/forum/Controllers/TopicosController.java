package br.com.alura.forum.Controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.models.Curso;
import br.com.alura.forum.models.Topico;
import br.com.alura.forum.models.dtos.DetalhesDoTopicoDto;
import br.com.alura.forum.models.dtos.TopicoDto;
import br.com.alura.forum.models.forms.AtualizacaoTopicoForm;
import br.com.alura.forum.models.forms.TopicoForm;
import br.com.alura.forum.repositories.TopicoRepository;
import br.com.alura.forum.services.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoService.listar();
            return TopicoDto.converter(topicos);
        }
        return TopicoDto.converter(topicoService.listarPorNomeCurso(nomeCurso));
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
        return topicoService.detalhar(id);
    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoService.salvar(form);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        TopicoDto topicoAtualizado = topicoService.atualizar(id, form);
        return ResponseEntity.ok(topicoAtualizado);
    }
}