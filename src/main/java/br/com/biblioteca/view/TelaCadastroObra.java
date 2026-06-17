package br.com.biblioteca.view;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.RevistaDAO;
import br.com.biblioteca.dao.TccDAO;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Revista;
import br.com.biblioteca.model.Tcc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TelaCadastroObra extends JFrame {

    private JTextField txtTitulo, txtAutor, txtAno, txtCategoria, txtEditora;
    private JComboBox<String> cbTipoObra;
    private JLabel lblEspecifico;
    private JTextField txtEspecifico;
    private JButton btnSalvar;

    public TelaCadastroObra() {
        setTitle("Cadastro de Obras (Herança)");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAno = new JTextField();
        txtCategoria = new JTextField();
        txtEditora = new JTextField();
        
        String[] tipos = {"Livro", "Revista", "TCC"};
        cbTipoObra = new JComboBox<>(tipos);
        
        lblEspecifico = new JLabel(" ISBN:");
        txtEspecifico = new JTextField();
        
        btnSalvar = new JButton("Salvar Obra");

        // Interface
        add(new JLabel(" Tipo de Obra:")); add(cbTipoObra);
        add(new JLabel(" Título:")); add(txtTitulo);
        add(new JLabel(" Autor:")); add(txtAutor);
        add(new JLabel(" Ano de Publicação:")); add(txtAno);
        add(new JLabel(" Categoria:")); add(txtCategoria);
        add(new JLabel(" Editora:")); add(txtEditora);
        
        // Troca campo específico conforme tipo
        add(lblEspecifico); add(txtEspecifico);
        
        add(new JLabel("")); // Espaço em branco
        add(btnSalvar);


        // Faz o texto do campo específico mudar quando troca a seleção
        cbTipoObra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) cbTipoObra.getSelectedItem();
                if ("Livro".equals(selecionado)) {
                    lblEspecifico.setText(" ISBN:");
                } else if ("Revista".equals(selecionado)) {
                    lblEspecifico.setText(" Edição (Número):");
                } else if ("TCC".equals(selecionado)) {
                    lblEspecifico.setText(" Curso:");
                }
            }
        });

        // Ação do Botão Salvar
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarObra();
            }
        });
    }

    private void salvarObra() {
        try {
            // Coleta dados comuns da classe Obra
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String categoria = txtCategoria.getText();
            String editora = txtEditora.getText();
            
            if (titulo.trim().isEmpty() || autor.trim().isEmpty()) {
                throw new IllegalArgumentException("Título e Autor são obrigatórios.");
            }

            int ano = Integer.parseInt(txtAno.getText());
            String selecionado = (String) cbTipoObra.getSelectedItem();
            String dadoEspecifico = txtEspecifico.getText();

            if (dadoEspecifico.trim().isEmpty()) {
                throw new IllegalArgumentException("Preencha o campo específico (" + lblEspecifico.getText().trim() + ").");
            }

            // Instancia o filho correto e chama o DAO correto (Herança)
            if ("Livro".equals(selecionado)) {
                Livro livro = new Livro(titulo, autor, ano, categoria, editora, dadoEspecifico);
                LivroDAO dao = new LivroDAO();
                dao.cadastrar(livro);
                
            } else if ("Revista".equals(selecionado)) {
                int edicao = Integer.parseInt(dadoEspecifico);
                Revista revista = new Revista(titulo, autor, ano, categoria, editora, edicao);
                RevistaDAO dao = new RevistaDAO();
                dao.cadastrar(revista);
                
            } else if ("TCC".equals(selecionado)) {
                Tcc tcc = new Tcc(titulo, autor, ano, categoria, editora, dadoEspecifico);
                TccDAO dao = new TccDAO();
                dao.cadastrar(tcc);
            }

            JOptionPane.showMessageDialog(this, selecionado + " salvo com sucesso no Banco de Dados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique se os campos 'Ano' e 'Edição' (se for Revista) contêm apenas números válidos.", "Erro de Digitação", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro no Banco de Dados: " + ex.getMessage(), "Erro Fatal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAno.setText("");
        txtCategoria.setText("");
        txtEditora.setText("");
        txtEspecifico.setText("");
        cbTipoObra.setSelectedIndex(0);
    }
}