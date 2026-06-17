package br.com.biblioteca.view;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.model.Emprestimo;
import javax.swing.*;
import java.awt.*;

public class TelaDevolucao extends JFrame {
    private JComboBox<Emprestimo> cbEmprestimosAtivos;
    private JButton btnFinalizar;

    public TelaDevolucao() {
        setTitle("Finalizar Empréstimo (Devolução)");
        setSize(400, 200);
        setLayout(new FlowLayout());

        cbEmprestimosAtivos = new JComboBox<>();
        btnFinalizar = new JButton("Confirmar Devolução");

        // Carrega empréstimos com status 'Ativo'
        carregarEmprestimosAtivos();

        add(new JLabel("Selecione o empréstimo:"));
        add(cbEmprestimosAtivos);
        add(btnFinalizar);

        btnFinalizar.addActionListener(e -> finalizar());
    }

    private void finalizar() {
        Emprestimo emp = (Emprestimo) cbEmprestimosAtivos.getSelectedItem();
        if (emp != null) {
            try {
                new EmprestimoDAO().registrarDevolucao(emp.getId(), emp.getCopia().getId());
                JOptionPane.showMessageDialog(this, "Devolução concluída com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }
    
    private void carregarEmprestimosAtivos() {
        try {
            // Chama o método no EmprestimoDAO
            java.util.List<Emprestimo> ativos = new EmprestimoDAO().listarAtivos();
            
            for (Emprestimo e : ativos) {
                cbEmprestimosAtivos.addItem(e);
            }
            
            // Renderer para mostrar o nome no combo
            cbEmprestimosAtivos.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Emprestimo) {
                        Emprestimo e = (Emprestimo) value;
                        setText("ID: " + e.getId() + " | Leitor: " + e.getLeitor().getNome());
                    }
                    return this;
                }
            });

        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lista: " + ex.getMessage());
        }
    }
}