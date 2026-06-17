package br.com.biblioteca.model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao; // Pode ser null até a devolução
    private String status; // Ativo, Atrasado ou Devolvido

    // Agregação: empréstimo envolve outras entidades
    private Leitor leitor;
    private Funcionario funcionario;
    private Copia copia;

    public Emprestimo() {}

    // Usado no momento do empréstimo
    public Emprestimo(LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao, String status, 
                      Leitor leitor, Funcionario funcionario, Copia copia) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.status = status;
        this.leitor = leitor;
        this.funcionario = funcionario;
        this.copia = copia;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) { this.dataPrevistaDevolucao = dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Leitor getLeitor() { return leitor; }
    public void setLeitor(Leitor leitor) { this.leitor = leitor; }
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
    public Copia getCopia() { return copia; }
    public void setCopia(Copia copia) { this.copia = copia; }
}