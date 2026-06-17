package br.com.biblioteca.view;

import br.com.biblioteca.dao.LeitorDAO;
import br.com.biblioteca.model.Leitor;
import br.com.biblioteca.util.TemaUI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastroLeitor extends JFrame {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtLimite;
    private JButton btnSalvar;
    private JButton btnLimpar;

    public TelaCadastroLeitor() {
        setTitle("Cadastro de Leitor");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(TemaUI.FUNDO_PRINCIPAL);
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(TemaUI.ROXO_ESCURO);
        JLabel lblTitulo = new JLabel("Cadastrar Novo Leitor");
        lblTitulo.setForeground(TemaUI.TEXTO_PRINCIPAL);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelTitulo.add(lblTitulo);

        // Painel de formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setBackground(TemaUI.FUNDO_SECUNDARIO);
        painelFormulario.setLayout(new GridLayout(6, 2, 10, 12));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos do formulário
        txtNome = criarCampoTexto();
        txtCpf = criarCampoTexto();
        txtTelefone = criarCampoTexto();
        txtEmail = criarCampoTexto();
        txtLimite = criarCampoTexto();
        txtLimite.setText("3");

        painelFormulario.add(criarLabel("Nome:"));
        painelFormulario.add(txtNome);
        painelFormulario.add(criarLabel("CPF:"));
        painelFormulario.add(txtCpf);
        painelFormulario.add(criarLabel("Telefone:"));
        painelFormulario.add(txtTelefone);
        painelFormulario.add(criarLabel("E-mail:"));
        painelFormulario.add(txtEmail);
        painelFormulario.add(criarLabel("Limite de Empréstimos:"));
        painelFormulario.add(txtLimite);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(TemaUI.FUNDO_PRINCIPAL);
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnSalvar = criarBotaoSalvar("✓ Salvar");
        btnLimpar = criarBotaoLimpar("🔄 Limpar");

        btnSalvar.addActionListener(e -> salvarLeitor());
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);

        // Montando a tela
        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(TemaUI.ROXO_CLARO);
        return lbl;
    }

    private JTextField criarCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 12));
        txt.setBackground(TemaUI.FUNDO_PRINCIPAL);
        txt.setForeground(TemaUI.TEXTO_PRINCIPAL);
        txt.setBorder(BorderFactory.createLineBorder(TemaUI.ROXO_MEDIO, 2));
        txt.setCaretColor(TemaUI.TEXTO_PRINCIPAL);
        return txt;
    }

    private JButton criarBotaoSalvar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(TemaUI.SUCESSO);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    private JButton criarBotaoLimpar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(TemaUI.ROXO_MEDIO);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    private void salvarLeitor() {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCpf.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são campos obrigatórios!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int limite = Integer.parseInt(txtLimite.getText());

            Leitor leitor = new Leitor(nome, cpf, telefone, email, limite);
            LeitorDAO dao = new LeitorDAO();
            dao.cadastrar(leitor);

            JOptionPane.showMessageDialog(this, "✓ Leitor cadastrado com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Limite deve ser um número válido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), 
                "Erro no Banco", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtLimite.setText("3");
    }
}
