package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // Cadastra uma nova reserva
    public void cadastrar(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reserva (data_reserva, status, id_leitor, id_obra) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reserva.getDataReserva()));
            stmt.setString(2, reserva.getStatus());
            // Associa a reserva ao leitor e à obra
            stmt.setInt(3, reserva.getLeitor().getId());
            stmt.setInt(4, reserva.getObra().getId());

            stmt.executeUpdate();
        }
    }

    // Atualiza os dados de uma reserva
    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reserva SET data_reserva = ?, status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(reserva.getDataReserva()));
            stmt.setString(2, reserva.getStatus());
            stmt.setInt(3, reserva.getId());

            stmt.executeUpdate();
        }
    }

    // Remove uma reserva pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Busca uma reserva específica pelo ID
    public Reserva buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, data_reserva, status, id_leitor, id_obra FROM reserva WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setId(rs.getInt("id"));
                    reserva.setDataReserva(rs.getDate("data_reserva").toLocalDate());
                    reserva.setStatus(rs.getString("status"));
                    return reserva;
                }
            }
        }
        return null;
    }

    // Retorna todas as reservas cadastradas
    public List<Reserva> listarTodas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id, data_reserva, status, id_leitor, id_obra FROM reserva";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setDataReserva(rs.getDate("data_reserva").toLocalDate());
                reserva.setStatus(rs.getString("status"));
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    // Retorna as reservas de um leitor específico
    public List<Reserva> listarPorLeitor(int idLeitor) throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id, data_reserva, status, id_leitor, id_obra FROM reserva WHERE id_leitor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLeitor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setId(rs.getInt("id"));
                    reserva.setDataReserva(rs.getDate("data_reserva").toLocalDate());
                    reserva.setStatus(rs.getString("status"));
                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }
}
