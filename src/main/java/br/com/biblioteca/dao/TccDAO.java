package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Tcc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TccDAO {

    // Cadastra um TCC nas tabelas obra e tcc
    public void cadastrar(Tcc tcc) throws SQLException {
        String sqlObra = "INSERT INTO obra (titulo, autor, ano, categoria, editora) VALUES (?, ?, ?, ?, ?)";
        String sqlTcc = "INSERT INTO tcc (curso, id_obra) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmtObra = null;
        PreparedStatement stmtTcc = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia a transação

            // Insere dados da obra
            stmtObra = conn.prepareStatement(sqlObra, Statement.RETURN_GENERATED_KEYS);
            stmtObra.setString(1, tcc.getTitulo());
            stmtObra.setString(2, tcc.getAutor());
            stmtObra.setInt(3, tcc.getAno());
            stmtObra.setString(4, tcc.getCategoria());
            stmtObra.setString(5, tcc.getEditora());
            stmtObra.executeUpdate();

            // Recupera o ID gerado da obra
            rs = stmtObra.getGeneratedKeys();
            int idObraGerado = 0;
            if (rs.next()) {
                idObraGerado = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao cadastrar obra, nenhum ID obtido.");
            }

            // Insere dados específicos do TCC vinculando à obra
            stmtTcc = conn.prepareStatement(sqlTcc);
            stmtTcc.setString(1, tcc.getCurso());
            stmtTcc.setInt(2, idObraGerado);
            stmtTcc.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Desfaz a operação em caso de erro
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmtObra != null) stmtObra.close();
            if (stmtTcc != null) stmtTcc.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}