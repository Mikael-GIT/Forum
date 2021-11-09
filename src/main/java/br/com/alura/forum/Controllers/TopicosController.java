package br.com.alura.forum.Controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    @Cacheable(value = "listaDeTopicos") // Funciona como se fosse o id do cache para o spring identificar
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable paginacao) {
        if (nomeCurso == null) {
            return topicoService.listar(paginacao);
        }
        return topicoService.listarPorNomeCurso(nomeCurso, paginacao);
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
        return topicoService.detalhar(id);
    }

    @PostMapping
    @CacheEvict(value = "listaDeTopicos", allEntries = true) // Indica para o spring que e para limpar o cache passando
    // o cache que deseja limpar
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoService.salvar(form);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        TopicoDto topicoAtualizado = topicoService.atualizar(id, form);
        return ResponseEntity.ok(topicoAtualizado);
    }

    /*
     * Cache so deve ser utilizado em lugares em que a mudanca de dados nao for
     * frequente
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        topicoService.deletar(id);
        return ResponseEntity.ok().build();
    }
}