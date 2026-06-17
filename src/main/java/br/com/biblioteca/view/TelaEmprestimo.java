package br.com.biblioteca.view;

import br.com.biblioteca.dao.CopiaDAO;
import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.FuncionarioDAO;
import br.com.biblioteca.dao.LeitorDAO;
import br.com.biblioteca.model.Copia;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Funcionario;
import br.com.biblioteca.model.Leitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaEmprestimo extends JFrame {

    private JComboBox<Leitor> cbLeitores;
    private JComboBox<Funcionario> cbFuncionarios;
    private JComboBox<Copia> cbCopias;
    private JTextField txtDataEmprestimo;
    private JTextField txtDataDevolucaoPrevista;
    private JButton btnRegistrar;

    // Formatador para exibir a data
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaEmprestimo() {
        setTitle("Registrar Empréstimo (Agregação Máxima)");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 2, 10, 10));

        // Inicializando os componentes dinâmicos
        cbLeitores = new JComboBox<>();
        cbFuncionarios = new JComboBox<>();
        cbCopias = new JComboBox<>();

        // Configura as datas automáticas por padrão
        txtDataEmprestimo = new JTextField(LocalDate.now().format(formatter));
        txtDataDevolucaoPrevista = new JTextField(LocalDate.now().plusDays(7).format(formatter));

        btnRegistrar = new JButton("Confirmar Empréstimo");

        // Aparência visual dos objetos no Combo
        cbLeitores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Leitor) {
                    Leitor l = (Leitor) value;
                    setText(l.getNome() + " (CPF: " + l.getCpf() + ")");
                }
                return this;
            }
        });

        cbFuncionarios.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario) {
                    Funcionario f = (Funcionario) value;
                    setText(f.getNome() + " [Matrícula: " + f.getMatricula() + "]");
                }
                return this;
            }
        });

        cbCopias.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Copia) {
                    Copia c = (Copia) value;
                    setText(c.getCodigo() + " - " + c.getObra().getTitulo());
                }
                return this;
            }
        });

        // Adicionando componentes na janela
        add(new JLabel(" Selecione o Leitor:")); add(cbLeitores);
        add(new JLabel(" Funcionário Atendente:")); add(cbFuncionarios);
        add(new JLabel(" Livro/Obra Disponível:")); add(cbCopias);
        add(new JLabel(" Data do Empréstimo (dd/MM/yyyy):")); add(txtDataEmprestimo);
        add(new JLabel(" Devolução Prevista (dd/MM/yyyy):")); add(txtDataDevolucaoPrevista);
        add(new JLabel("")); add(btnRegistrar);

        // Carrega as informações vindas do banco de dados
        carregarDadosIniciais();

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                efetuarEmprestimo();
            }
        });
    }

    private void carregarDadosIniciais() {
        try {
            // Busca e popula os Leitores
            List<Leitor> leitores = new LeitorDAO().listarTodos();
            for (Leitor l : leitores) cbLeitores.addItem(l);

            // Busca e popula os Funcionários
            List<Funcionario> funcionarios = new FuncionarioDAO().listarTodos();
            for (Funcionario f : funcionarios) cbFuncionarios.addItem(f);

            // Busca e popula apenas as Cópias Disponíveis
            List<Copia> copias = new CopiaDAO().listarDisponiveis();
            for (Copia c : copias) cbCopias.addItem(c);

            // Validação visual se há estoque ou cadastros
            if (leitores.isEmpty() || funcionarios.isEmpty() || copias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aviso: Certifique-se de ter pelo menos um Leitor, um Funcionário e uma Cópia DISPONÍVEL cadastrados no sistema.", "Falta de dados", JOptionPane.WARNING_MESSAGE);
                btnRegistrar.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados para o empréstimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void efetuarEmprestimo() {
        try {
            // Recupera os objetos inteiros selecionados nos Combos
            Leitor leitorSel = (Leitor) cbLeitores.getSelectedItem();
            Funcionario funcSel = (Funcionario) cbFuncionarios.getSelectedItem();
            Copia copiaSel = (Copia) cbCopias.getSelectedItem();

            // Tenta converter as caixas de texto de volta para LocalDate
            LocalDate dataEmp = LocalDate.parse(txtDataEmprestimo.getText(), formatter);
            LocalDate dataDev = LocalDate.parse(txtDataDevolucaoPrevista.getText(), formatter);

            if (dataDev.isBefore(dataEmp)) {
                throw new IllegalArgumentException("A data de devolução não pode ser anterior à data de empréstimo.");
            }

            // Monta o objeto Empréstimo injetando as outras 3 entidades completas dentro dele
            // Agregação
            Emprestimo emprestimo = new Emprestimo(dataEmp, dataDev, "Ativo", leitorSel, funcSel, copiaSel);

            // Salva no banco executando a transação dupla
            EmprestimoDAO dao = new EmprestimoDAO();
            dao.registrarEmprestimo(emprestimo);

            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!\nO livro agora está marcado como 'Emprestado'.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Fecha a janela atual e atualiza a listagem se abrir novamente
            this.dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use o padrão dd/MM/yyyy (Ex: 15/10/2023)", "Erro de Data", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro crítico no banco de dados: " + ex.getMessage(), "Erro Fatal", JOptionPane.ERROR_MESSAGE);
        }
    }
}