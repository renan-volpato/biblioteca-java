package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Revista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RevistaDAO {

    // Cadastra uma revista nas tabelas obra e revista
    public void cadastrar(Revista revista) throws SQLException {
        String sqlObra = "INSERT INTO obra (titulo, autor, ano, categoria, editora) VALUES (?, ?, ?, ?, ?)";
        String sqlRevista = "INSERT INTO revista (edicao, id_obra) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmtObra = null;
        PreparedStatement stmtRevista = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia a transação

            // Insere os dados da obra
            stmtObra = conn.prepareStatement(sqlObra, Statement.RETURN_GENERATED_KEYS);
            stmtObra.setString(1, revista.getTitulo());
            stmtObra.setString(2, revista.getAutor());
            stmtObra.setInt(3, revista.getAno());
            stmtObra.setString(4, revista.getCategoria());
            stmtObra.setString(5, revista.getEditora());
            stmtObra.executeUpdate();

            // Recupera o ID gerado pela tabela obra
            rs = stmtObra.getGeneratedKeys();
            int idObraGerado = 0;
            if (rs.next()) {
                idObraGerado = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao cadastrar obra, nenhum ID obtido.");
            }

            // Insere os dados específicos da revista
            stmtRevista = conn.prepareStatement(sqlRevista);
            stmtRevista.setInt(1, revista.getEdicao());
            stmtRevista.setInt(2, idObraGerado);
            stmtRevista.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Desfaz a operação em caso de erro
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmtObra != null) stmtObra.close();
            if (stmtRevista != null) stmtRevista.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}