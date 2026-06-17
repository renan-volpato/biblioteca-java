package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Leitor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeitorDAO {

    // Cadastra um novo leitor no banco
    public void cadastrar(Leitor leitor) throws SQLException {
        String sql = "INSERT INTO leitor (nome, cpf, telefone, email, limite_emprestimos) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, leitor.getNome());
            stmt.setString(2, leitor.getCpf());
            stmt.setString(3, leitor.getTelefone());
            stmt.setString(4, leitor.getEmail());
            stmt.setInt(5, leitor.getLimiteEmprestimos());

            stmt.executeUpdate();
        }
    }

    // Atualiza os dados de um leitor existente
    public void atualizar(Leitor leitor) throws SQLException {
        String sql = "UPDATE leitor SET nome = ?, cpf = ?, telefone = ?, email = ?, limite_emprestimos = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, leitor.getNome());
            stmt.setString(2, leitor.getCpf());
            stmt.setString(3, leitor.getTelefone());
            stmt.setString(4, leitor.getEmail());
            stmt.setInt(5, leitor.getLimiteEmprestimos());
            stmt.setInt(6, leitor.getId());

            stmt.executeUpdate();
        }
    }

    // Remove um leitor pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM leitor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Busca um leitor específico pelo ID
    public Leitor buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, cpf, telefone, email, limite_emprestimos FROM leitor WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Leitor leitor = new Leitor();

                    // Preenche o objeto com os dados retornados
                    leitor.setId(rs.getInt("id"));
                    leitor.setNome(rs.getString("nome"));
                    leitor.setCpf(rs.getString("cpf"));
                    leitor.setTelefone(rs.getString("telefone"));
                    leitor.setEmail(rs.getString("email"));
                    leitor.setLimiteEmprestimos(rs.getInt("limite_emprestimos"));
                    return leitor;
                }
            }
        }
        return null;
    }

    // Retorna todos os leitores cadastrados
    public List<Leitor> listarTodos() throws SQLException {
        List<Leitor> leitores = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, telefone, email, limite_emprestimos FROM leitor";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                Leitor leitor = new Leitor();

                // Preenche o objeto com os dados do banco
                leitor.setId(rs.getInt("id"));
                leitor.setNome(rs.getString("nome"));
                leitor.setCpf(rs.getString("cpf"));
                leitor.setTelefone(rs.getString("telefone"));
                leitor.setEmail(rs.getString("email"));
                leitor.setLimiteEmprestimos(rs.getInt("limite_emprestimos"));
                leitores.add(leitor);
            }
        }
        return leitores;
    }
}