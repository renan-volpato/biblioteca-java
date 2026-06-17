package br.com.biblioteca.model;

public class Livro extends Obra {
    private String isbn;

    public Livro() {
        super();
    }

    // Construtor completo do Livro
    public Livro(String titulo, String autor, int ano, String categoria, String editora, String isbn) {
        super(titulo, autor, ano, categoria, editora);
        this.isbn = isbn;
    }

    // Sobrescrita do método para exibir detalhes específicos do Livro
    @Override
    public String obterDetalhes() {
        return String.format("Livro: %s | Autor: %s | ISBN: %s | Editora: %s", 
            getTitulo(), getAutor(), isbn, getEditora());
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
