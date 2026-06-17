package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Copia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CopiaDAO {

    // Cadastra uma nova cópia no banco de dados
    public void cadastrar(Copia copia) throws SQLException {
        String sql = "INSERT INTO copia (codigo, status, localizacao, id_obra) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, copia.getCodigo());
            stmt.setString(2, copia.getStatus());
            stmt.setString(3, copia.getLocalizacao());
            
            // Associa a cópia à obra (chave estrangeira)
            stmt.setInt(4, copia.getObra().getId());

            stmt.executeUpdate();
        }
    }

    // Lista todas as cópias disponíveis para empréstimo
    public java.util.List<br.com.biblioteca.model.Copia> listarDisponiveis() throws SQLException {
        java.util.List<br.com.biblioteca.model.Copia> copias = new java.util.ArrayList<>();

        // JOIN entre cópia e obra para buscar dados relacionados
        String sql = "SELECT c.id, c.codigo, c.status, c.localizacao, o.id AS id_obra, o.titulo " +
                     "FROM copia c " +
                     "INNER JOIN obra o ON c.id_obra = o.id " +
                     "WHERE c.status = 'Disponível'";

        try (Connection conn = br.com.biblioteca.factory.ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
               
                // Monta objeto Obra
                br.com.biblioteca.model.Obra obra = new br.com.biblioteca.model.Livro();
                obra.setId(rs.getInt("id_obra"));
                obra.setTitulo(rs.getString("titulo"));

                // Monta objeto Cópia
                br.com.biblioteca.model.Copia copia = new br.com.biblioteca.model.Copia();
                copia.setId(rs.getInt("id"));
                copia.setCodigo(rs.getString("codigo"));
                copia.setStatus(rs.getString("status"));
                copia.setLocalizacao(rs.getString("localizacao"));
                copia.setObra(obra); // Associa a obra à cópia

                copias.add(copia);
            }
        }
        return copias;
    }
}