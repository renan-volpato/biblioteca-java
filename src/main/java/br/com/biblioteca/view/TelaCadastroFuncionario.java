package br.com.biblioteca.view;

import br.com.biblioteca.dao.FuncionarioDAO;
import br.com.biblioteca.model.Funcionario;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastroFuncionario extends JFrame {
    private JTextField txtNome, txtCpf, txtTelefone, txtEmail, txtMatricula, txtCargo;
    private JButton btnSalvar;

    public TelaCadastroFuncionario() {
        setTitle("Cadastro de Funcionário");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtMatricula = new JTextField();
        txtCargo = new JTextField();
        btnSalvar = new JButton("Salvar");

        // Montagem da interface
        add(new JLabel(" Nome:")); add(txtNome);
        add(new JLabel(" CPF:")); add(txtCpf);
        add(new JLabel(" Telefone:")); add(txtTelefone);
        add(new JLabel(" Email:")); add(txtEmail);
        add(new JLabel(" Matrícula:")); add(txtMatricula);
        add(new JLabel(" Cargo:")); add(txtCargo);
        add(new JLabel("")); add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarFuncionario());
    }

    // Salva funcionário no banco
    private void salvarFuncionario() {
        try {
            Funcionario f = new Funcionario(
                txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), 
                txtEmail.getText(), txtMatricula.getText(), txtCargo.getText()
            );
            new FuncionarioDAO().cadastrar(f);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}