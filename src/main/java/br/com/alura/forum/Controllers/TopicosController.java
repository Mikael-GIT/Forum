package br.com.alura.forum.Controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, @RequestParam int pagina,
            @RequestParam int qtd, @RequestParam String ordenacao) {
        if (nomeCurso == null) {
            return topicoService.listar(pagina, qtd, ordenacao);
        }
        return topicoService.listarPorNomeCurso(nomeCurso, pagina, qtd, ordenacao);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}