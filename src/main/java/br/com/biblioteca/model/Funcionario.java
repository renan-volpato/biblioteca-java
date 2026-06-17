package br.com.biblioteca.model;

public class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String matricula;
    private String cargo;

    public Funcionario() {}

    // Construtor para cadastro de funcionário
    public Funcionario(String nome, String cpf, String telefone, String email, String matricula, String cargo) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.matricula = matricula;
        this.cargo = cargo;
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
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}