package br.com.biblioteca.model;

public class Copia {
    private int id;
    private String codigo;
    private String status; // Disponível, Emprestada, Danificada
    private String localizacao; // Local físico na biblioteca
    
    // Associação: cada cópia pertence a uma Obra
    private Obra obra;

    public Copia() {}

    public Copia(String codigo, String status, String localizacao, Obra obra) {
        this.codigo = codigo;
        this.status = status;
        this.localizacao = localizacao;
        this.obra = obra;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }
}