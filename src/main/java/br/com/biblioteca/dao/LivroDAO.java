package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LivroDAO {

    // Cadastra um livro nas tabelas obra e livro
    public void cadastrar(Livro livro) throws SQLException {
        String sqlObra = "INSERT INTO obra (titulo, autor, ano, categoria, editora) VALUES (?, ?, ?, ?, ?)";
        String sqlLivro = "INSERT INTO livro (isbn, id_obra) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmtObra = null;
        PreparedStatement stmtLivro = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            
            // Inicia uma transação
            conn.setAutoCommit(false); 

            // Insere os dados da obra
            stmtObra = conn.prepareStatement(sqlObra, Statement.RETURN_GENERATED_KEYS);
            stmtObra.setString(1, livro.getTitulo());
            stmtObra.setString(2, livro.getAutor());
            stmtObra.setInt(3, livro.getAno());
            stmtObra.setString(4, livro.getCategoria());
            stmtObra.setString(5, livro.getEditora());
            
            stmtObra.executeUpdate();

            // Recupera o ID gerado para a obra
            rs = stmtObra.getGeneratedKeys();
            int idObraGerado = 0;
            if (rs.next()) {
                idObraGerado = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao cadastrar obra, nenhum ID obtido.");
            }

            // Insere os dados específicos do livro
            stmtLivro = conn.prepareStatement(sqlLivro);
            stmtLivro.setString(1, livro.getIsbn());
            // Associa o livro à obra cadastrada
            stmtLivro.setInt(2, idObraGerado);
            
            stmtLivro.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            // Desfaz a transação em caso de erro
            if (conn != null) {
                conn.rollback();
            }
            throw e; 
        } finally {

            // Libera os recursos utilizados
            if (rs != null) rs.close();
            if (stmtObra != null) stmtObra.close();
            if (stmtLivro != null) stmtLivro.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
