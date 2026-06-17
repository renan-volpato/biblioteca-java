package br.com.biblioteca.model;

public class Tcc extends Obra {
    private String curso;

    public Tcc() {
        super();
    }

    // Construtor completo do TCC
    public Tcc(String titulo, String autor, int ano, String categoria, String editora, String curso) {
        super(titulo, autor, ano, categoria, editora);
        this.curso = curso;
    }

    // Polimorfismo
    @Override
    public String obterDetalhes() {
        return String.format("TCC: %s | Curso: %s | Autor: %s | Ano: %d", 
            getTitulo(), curso, getAutor(), getAno());
    }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}