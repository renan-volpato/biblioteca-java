package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // Cadastra um novo funcionário no banco
    public void cadastrar(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario (nome, cpf, telefone, email, matricula, cargo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getMatricula());
            stmt.setString(6, funcionario.getCargo());
            stmt.executeUpdate();
        }
    }

    // Retorna todos os funcionários cadastrados
    public List<Funcionario> listarTodos() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT id, nome, matricula FROM funcionario";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                Funcionario f = new Funcionario();

                // Preenche o objeto com os dados retornados pelo banco
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setMatricula(rs.getString("matricula"));
                funcionarios.add(f);
            }
        }
        return funcionarios;
    }
}