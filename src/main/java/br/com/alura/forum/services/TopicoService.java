package br.com.alura.forum.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.forum.models.Topico;
import br.com.alura.forum.models.dtos.DetalhesDoTopicoDto;
import br.com.alura.forum.models.dtos.TopicoDto;
import br.com.alura.forum.models.forms.AtualizacaoTopicoForm;
import br.com.alura.forum.models.forms.TopicoForm;
import br.com.alura.forum.repositories.CursoRepository;
import br.com.alura.forum.repositories.TopicoRepository;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    public Page<TopicoDto> listar(Pageable paginacao) {
        Page<Topico> topicos = repository.findAll(paginacao);
        return TopicoDto.converter(topicos);
    }

    public Page<TopicoDto> listarPorNomeCurso(String nomeDoCurso, Pageable paginacao) {
        Page<Topico> topicos = repository.findByCurso_Nome(nomeDoCurso, paginacao);
        return TopicoDto.converter(topicos);
    }

    public Topico salvar(TopicoForm form) {
        Topico topico = form.converter(cursoRepository);
        repository.save(topico);
        return topico;
    }

    public DetalhesDoTopicoDto detalhar(Long id) {
        Topico topico = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id, null));
        return new DetalhesDoTopicoDto(topico);
    }

    public TopicoDto atualizar(Long id, AtualizacaoTopicoForm form) {
        Topico topico = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id, null));
        topico.setTitulo(form.getTitulo());
        topico.setMensagem(form.getMensagem());
        repository.save(topico);
        return new TopicoDto(topico);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}