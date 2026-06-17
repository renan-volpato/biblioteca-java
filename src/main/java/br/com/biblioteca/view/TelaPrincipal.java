package br.com.biblioteca.view;

import br.com.biblioteca.util.TemaUI;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Biblioteca - Gestão Central");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal com cor de fundo dark
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(TemaUI.FUNDO_PRINCIPAL);
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(TemaUI.ROXO_ESCURO);
        painelTitulo.setPreferredSize(new Dimension(600, 60));
        JLabel lblTitulo = new JLabel("Sistema de Biblioteca");
        lblTitulo.setForeground(TemaUI.TEXTO_PRINCIPAL);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        painelTitulo.add(lblTitulo);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(TemaUI.FUNDO_PRINCIPAL);
        painelBotoes.setLayout(new GridLayout(7, 1, 10, 15));

        // Botões
        JButton btnObra = criarBotao("Cadastrar Obra");
        JButton btnCopia = criarBotao("Cadastrar Cópia");
        JButton btnLeitor = criarBotao("Cadastrar Leitor");
        JButton btnFuncionario = criarBotao("Cadastrar Funcionário");
        JButton btnEmprestimo = criarBotao("Registrar Empréstimo");
        JButton btnDevolucao = criarBotao("Registrar Devolução");
        JButton btnSair = criarBotaoSair("Sair");

        // Adicionando ações aos botões
        btnObra.addActionListener(e -> new TelaCadastroObra().setVisible(true));
        btnCopia.addActionListener(e -> new TelaCadastroCopia().setVisible(true));
        btnLeitor.addActionListener(e -> new TelaCadastroLeitor().setVisible(true));
        btnFuncionario.addActionListener(e -> new TelaCadastroFuncionario().setVisible(true));
        btnEmprestimo.addActionListener(e -> new TelaEmprestimo().setVisible(true));
        btnDevolucao.addActionListener(e -> new TelaDevolucao().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));

        // Adicionando botões ao painel
        painelBotoes.add(btnObra);
        painelBotoes.add(btnCopia);
        painelBotoes.add(btnLeitor);
        painelBotoes.add(btnFuncionario);
        painelBotoes.add(btnEmprestimo);
        painelBotoes.add(btnDevolucao);
        painelBotoes.add(btnSair);

        // Montando a tela
        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(TemaUI.ROXO_MEDIO);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(TemaUI.ROXO_CLARO);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(TemaUI.ROXO_MEDIO);
            }
        });
        
        return btn;
    }

    private JButton criarBotaoSair(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(TemaUI.ERRO);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(TemaUI.ERRO);
            }
        });
        
        return btn;
    }
}