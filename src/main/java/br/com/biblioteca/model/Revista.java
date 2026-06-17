package br.com.biblioteca.model;

public class Revista extends Obra {
    private int edicao;

    public Revista() {
        super();
    }

    // Construtor completo da Revista
    public Revista(String titulo, String autor, int ano, String categoria, String editora, int edicao) {
        super(titulo, autor, ano, categoria, editora);
        this.edicao = edicao;
    }

    // Polimorfismo
    @Override
    public String obterDetalhes() {
        return String.format("Revista: %s | Edição: %d | Autor: %s | Editora: %s", 
            getTitulo(), edicao, getAutor(), getEditora());
    }

    public int getEdicao() { return edicao; }
    public void setEdicao(int edicao) { this.edicao = edicao; }
}