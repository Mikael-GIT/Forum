package br.com.alura.forum.models.forms;

import br.com.alura.forum.models.Curso;
import br.com.alura.forum.models.Topico;
import br.com.alura.forum.repositories.CursoRepository;

public class TopicoForm {

    private String titulo;
    private String mensagem;
    private String nomeCurso;

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return this.nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository repository) {
        Curso curso = repository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }

}
