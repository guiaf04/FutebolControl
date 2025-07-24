package com.futebol.ui;

import com.futebol.services.ApiService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Janela principal do aplicativo - Menu principal
 */
public class MainWindow extends JFrame {
    private final ApiService apiService;
    
    // Janelas secundárias
    private ClubeWindow clubeWindow;
    private CampeonatoWindow campeonatoWindow;
    private PartidaWindow partidaWindow;

    public MainWindow() {
        this.apiService = new ApiService();
        initializeUI();
    }

    public MainWindow(String apiBaseUrl) {
        this.apiService = new ApiService(apiBaseUrl);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Futebol Control - Sistema de Gestão");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior com logo/título
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Painel central com botões do menu
        JPanel menuPanel = createMenuPanel();
        add(menuPanel, BorderLayout.CENTER);

        // Painel inferior com informações
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(34, 139, 34));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("⚽ FUTEBOL CONTROL ⚽", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("Sistema de Gestão de Futebol", JLabel.CENTER);
        subtitleLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        subtitleLabel.setForeground(Color.WHITE);
        panel.add(subtitleLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título do menu
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel menuTitle = new JLabel("Selecione uma opção:", JLabel.CENTER);
        menuTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        panel.add(menuTitle, gbc);

        gbc.gridwidth = 1;

        // Botão Clubes
        gbc.gridx = 0; gbc.gridy = 1;
        JButton btnClubes = createMenuButton("🏟️ Gestão de Clubes", 
            "Criar, editar, visualizar e gerenciar clubes", 
            new Color(255, 87, 34));
        btnClubes.addActionListener(this::abrirJanelaClubes);
        panel.add(btnClubes, gbc);

        // Botão Campeonatos
        gbc.gridx = 1; gbc.gridy = 1;
        JButton btnCampeonatos = createMenuButton("🏆 Gestão de Campeonatos", 
            "Criar, editar e gerenciar campeonatos", 
            new Color(255, 193, 7));
        btnCampeonatos.addActionListener(this::abrirJanelaCampeonatos);
        panel.add(btnCampeonatos, gbc);

        // Botão Partidas
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnPartidas = createMenuButton("⚽ Gestão de Partidas", 
            "Registrar partidas e visualizar históricos", 
            new Color(33, 150, 243));
        btnPartidas.addActionListener(this::abrirJanelaPartidas);
        panel.add(btnPartidas, gbc);

        // Separador
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(20), gbc);

        // Botão Sobre
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        JButton btnSobre = createSmallButton("ℹ️ Sobre", new Color(128, 128, 128));
        btnSobre.addActionListener(this::mostrarSobre);
        panel.add(btnSobre, gbc);

        // Botão Sair
        gbc.gridx = 1; gbc.gridy = 4;
        JButton btnSair = createSmallButton("🚪 Sair", new Color(244, 67, 54));
        btnSair.addActionListener(this::sair);
        panel.add(btnSair, gbc);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel statusLabel = new JLabel("Status: Conectado ao servidor | API: " + apiService.toString());
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        statusLabel.setForeground(Color.DARK_GRAY);
        panel.add(statusLabel);

        return panel;
    }

    private JButton createMenuButton(String title, String description, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(220, 80));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        button.add(titleLabel, BorderLayout.CENTER);

        JLabel descLabel = new JLabel(description, JLabel.CENTER);
        descLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        button.add(descLabel, BorderLayout.SOUTH);

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JButton createSmallButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // ==================== MÉTODOS DE AÇÃO ====================

    private void abrirJanelaClubes(ActionEvent e) {
        if (clubeWindow == null) {
            clubeWindow = new ClubeWindow(apiService);
        }
        clubeWindow.setVisible(true);
        clubeWindow.toFront();
    }

    private void abrirJanelaCampeonatos(ActionEvent e) {
        if (campeonatoWindow == null) {
            campeonatoWindow = new CampeonatoWindow(apiService);
        }
        campeonatoWindow.setVisible(true);
        campeonatoWindow.toFront();
    }

    private void abrirJanelaPartidas(ActionEvent e) {
        if (partidaWindow == null) {
            partidaWindow = new PartidaWindow(apiService);
        }
        partidaWindow.setVisible(true);
        partidaWindow.toFront();
    }

    private void mostrarSobre(ActionEvent e) {
        String sobre = "Futebol Control v2.0\n\n" +
            "Sistema de Gestão de Futebol desenvolvido em Java\n\n" +
            "Funcionalidades:\n" +
            "• Gestão completa de clubes\n" +
            "• Criação e administração de campeonatos\n" +
            "• Registro e acompanhamento de partidas\n" +
            "• Estatísticas detalhadas\n" +
            "• Interface gráfica moderna\n\n" +
            "Tecnologias utilizadas:\n" +
            "• Java 11\n" +
            "• Maven\n" +
            "• Gson para JSON\n" +
            "• Swing para interface gráfica\n" +
            "• HttpURLConnection para API REST\n\n" +
            "Desenvolvido para o curso de Programação";

        JOptionPane.showMessageDialog(this, sobre, "Sobre o Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sair(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja sair do sistema?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Fechar janelas secundárias
            if (clubeWindow != null) clubeWindow.dispose();
            if (campeonatoWindow != null) campeonatoWindow.dispose();
            if (partidaWindow != null) partidaWindow.dispose();
            
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Definir look and feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Usar look and feel padrão se falhar
            System.err.println("Não foi possível definir o look and feel do sistema: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
