package br.com.biblioteca.model;

public class Leitor {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private int limiteEmprestimos;

    // Construtor vazio
    public Leitor() {}

    // Construtor completo para cadastro
    public Leitor(String nome, String cpf, String telefone, String email, int limiteEmprestimos) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.limiteEmprestimos = limiteEmprestimos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getLimiteEmprestimos() { return limiteEmprestimos; }
    public void setLimiteEmprestimos(int limiteEmprestimos) { this.limiteEmprestimos = limiteEmprestimos; }
}
