package br.com.biblioteca.dao;

import br.com.biblioteca.factory.ConnectionFactory;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Obra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {

    // Retorna todas as obras cadastradas
    public List<Obra> listarTodas() throws SQLException {
        List<Obra> obras = new ArrayList<>();
        String sql = "SELECT id, titulo, autor FROM obra";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Cria o objeto e preenche com os dados do banco
                Livro obra = new Livro(); 
                obra.setId(rs.getInt("id"));
                obra.setTitulo(rs.getString("titulo"));
                obra.setAutor(rs.getString("autor"));
                
                obras.add(obra);
            }
        }
        return obras;
    }
}