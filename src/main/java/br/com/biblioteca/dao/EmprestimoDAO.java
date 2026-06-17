package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; 

public class EmprestimoDAO {

    // Registra um novo empréstimo e atualiza o status da cópia
    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (data_emprestimo, data_prevista_devolucao, status, id_leitor, id_funcionario, id_copia) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        /* Após registrar o empréstimo, a cópia não pode mais
        ficar disponível para outros leitores */
        String sqlAtualizarCopia = "UPDATE copia SET status = 'Emprestada' WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmtEmprestimo = null;
        PreparedStatement stmtCopia = null;

        try {
            conn = ConnectionFactory.getConnection();
            // Inicia transação
            conn.setAutoCommit(false); 

            // Insere o empréstimo
            stmtEmprestimo = conn.prepareStatement(sql);
            stmtEmprestimo.setDate(1, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmtEmprestimo.setDate(2, Date.valueOf(emprestimo.getDataPrevistaDevolucao()));
            stmtEmprestimo.setString(3, emprestimo.getStatus());
            
            // Extrai as Chaves Estrangeiras (Associação)
            stmtEmprestimo.setInt(4, emprestimo.getLeitor().getId());
            stmtEmprestimo.setInt(5, emprestimo.getFuncionario().getId());
            stmtEmprestimo.setInt(6, emprestimo.getCopia().getId());
            
            stmtEmprestimo.executeUpdate();

            // Atualiza status da cópia para impedir novos empréstimos
            stmtCopia = conn.prepareStatement(sqlAtualizarCopia);
            stmtCopia.setInt(1, emprestimo.getCopia().getId());
            stmtCopia.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Desfaz se algo deu errado
            }
            throw e;
        } finally {
            if (stmtEmprestimo != null) stmtEmprestimo.close();
            if (stmtCopia != null) stmtCopia.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    //Registra a devolução de um empréstimo.
    public void registrarDevolucao(int idEmprestimo, int idCopia) throws SQLException {
    String sqlUpdateEmprestimo = "UPDATE emprestimo SET data_devolucao = ?, status = 'Devolvido' WHERE id = ?";
    String sqlUpdateCopia = "UPDATE copia SET status = 'Disponível' WHERE id = ?";

    try (Connection conn = ConnectionFactory.getConnection()) {
        conn.setAutoCommit(false); // Transação iniciada

        //Finaliza o Empréstimo
        try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdateEmprestimo)) {
            stmt1.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt1.setInt(2, idEmprestimo);
            stmt1.executeUpdate();
        }

        // Libera a Cópia
        try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateCopia)) {
            stmt2.setInt(1, idCopia);
            stmt2.executeUpdate();
        }

        conn.commit();
    }
    }

    // Lista empréstimos ativos para uso em telas
    public java.util.List<br.com.biblioteca.model.Emprestimo> listarAtivos() 
        throws java.sql.SQLException {

    // Lista que armazenará os empréstimos encontrados
    java.util.List<br.com.biblioteca.model.Emprestimo> lista = new java.util.ArrayList<>();
    String sql = "SELECT e.id, e.id_copia, l.nome, o.titulo " +
                 "FROM emprestimo e " +
                 "INNER JOIN leitor l ON e.id_leitor = l.id " +
                 "INNER JOIN copia c ON e.id_copia = c.id " +
                 "INNER JOIN obra o ON c.id_obra = o.id " +
                 "WHERE e.status = 'Ativo'";

    try (java.sql.Connection conn = br.com.biblioteca.factory.ConnectionFactory.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
         java.sql.ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            // Cria um objeto simplificado de empréstimo.
            br.com.biblioteca.model.Emprestimo e = new br.com.biblioteca.model.Emprestimo();
            e.setId(rs.getInt("id"));
            
            // Recupera a cópia associada.
            br.com.biblioteca.model.Copia c = new br.com.biblioteca.model.Copia();
            c.setId(rs.getInt("id_copia"));
            
            // Recupera o leitor apenas para exibir
            br.com.biblioteca.model.Leitor l = new br.com.biblioteca.model.Leitor();
            l.setNome(rs.getString("nome"));
            
            e.setCopia(c);
            e.setLeitor(l);
            
            lista.add(e);
        }
    }
    return lista;
}
}