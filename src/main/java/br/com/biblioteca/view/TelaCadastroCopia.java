package br.com.biblioteca.view;

import br.com.biblioteca.dao.CopiaDAO;
import br.com.biblioteca.dao.ObraDAO;
import br.com.biblioteca.model.Copia;
import br.com.biblioteca.model.Obra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TelaCadastroCopia extends JFrame {

    // ComboBox armazena objetos Obra
    private JComboBox<Obra> cbObras; 
    private JTextField txtCodigo;
    private JComboBox<String> cbStatus;
    private JTextField txtLocalizacao;
    private JButton btnSalvar;

    public TelaCadastroCopia() {
        setTitle("Cadastro de Cópias (Associação)");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(5, 2, 10, 10));

        cbObras = new JComboBox<>();
        txtCodigo = new JTextField();
        
        // Status fixos da cópia
        String[] statusOpcoes = {"Disponível", "Emprestada", "Em Manutenção", "Perdida"};
        cbStatus = new JComboBox<>(statusOpcoes);
        
        txtLocalizacao = new JTextField();
        btnSalvar = new JButton("Salvar Cópia");

        // Exibição personalizada das obras no ComboBox
        cbObras.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Obra) {
                    Obra obra = (Obra) value;
                    setText("ID: " + obra.getId() + " - " + obra.getTitulo());
                }
                return this;
            }
        });

        // Montagem da interface
        add(new JLabel(" Obra Pertencente:")); add(cbObras);
        add(new JLabel(" Código da Cópia (ex: C001):")); add(txtCodigo);
        add(new JLabel(" Status:")); add(cbStatus);
        add(new JLabel(" Localização (ex: Estante 3):")); add(txtLocalizacao);
        add(new JLabel("")); add(btnSalvar); // Espaço vazio para alinhar o botão

        carregarObrasNoCombo();

        // Ação do Botão Salvar
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCopia();
            }
        });
    }

    // Carrega obras do banco para o ComboBox
    private void carregarObrasNoCombo() {
        try {
            ObraDAO dao = new ObraDAO();
            List<Obra> listaDeObras = dao.listarTodas();
            
            for (Obra obra : listaDeObras) {
                cbObras.addItem(obra); // Adiciona o objeto inteiro!
            }
            
            if (listaDeObras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma obra cadastrada. Cadastre uma Obra primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                btnSalvar.setEnabled(false); // Desativa o botão se não tem obra
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar obras: " + ex.getMessage(), "Erro de Banco", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Salva a cópia no banco
    private void salvarCopia() {
        try {
            Obra obraSelecionada = (Obra) cbObras.getSelectedItem();
            String codigo = txtCodigo.getText();
            String status = (String) cbStatus.getSelectedItem();
            String localizacao = txtLocalizacao.getText();

            // Validação de negócio
            if (obraSelecionada == null || codigo.trim().isEmpty() || localizacao.trim().isEmpty()) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
            }

            // Cria cópia associada à obra (associação)
            Copia copia = new Copia(codigo, status, localizacao, obraSelecionada);

            CopiaDAO dao = new CopiaDAO();
            dao.cadastrar(copia);

            JOptionPane.showMessageDialog(this, "Cópia cadastrada com sucesso!\nVinculada à obra: " + obraSelecionada.getTitulo(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpa os campos para o próximo cadastro
            txtCodigo.setText("");
            txtLocalizacao.setText("");
            cbStatus.setSelectedIndex(0);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados: " + ex.getMessage(), "Erro Fatal", JOptionPane.ERROR_MESSAGE);
        }
    }
}