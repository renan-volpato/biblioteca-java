package br.com.biblioteca.model;

import java.time.LocalDate;

public class Reserva {
    private int id;
    private LocalDate dataReserva;
    private String status; // Pendente, Atendida ou Cancelada

    // Associação: reserva envolve um leitor e uma obra
    private Leitor leitor;
    private Obra obra;

    public Reserva() {}

    // Construtor para criação de reserva
    public Reserva(LocalDate dataReserva, String status, Leitor leitor, Obra obra) {
        this.dataReserva = dataReserva;
        this.status = status;
        this.leitor = leitor;
        this.obra = obra;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getDataReserva() { return dataReserva; }
    public void setDataReserva(LocalDate dataReserva) { this.dataReserva = dataReserva; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Leitor getLeitor() { return leitor; }
    public void setLeitor(Leitor leitor) { this.leitor = leitor; }
    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }
}
