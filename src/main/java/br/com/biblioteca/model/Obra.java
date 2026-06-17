package br.com.biblioteca.model;

public abstract class Obra {
    private int id;
    private String titulo;
    private String autor;
    private int ano;
    private String categoria;
    private String editora;

    public Obra() {}

    // Construtor base para reutilização nas subclasses
    public Obra(String titulo, String autor, int ano, String categoria, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.categoria = categoria;
        this.editora = editora;
    }

    // Método abstrato (polimorfismo) implementado pelas subclasses
    public abstract String obterDetalhes();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
}
