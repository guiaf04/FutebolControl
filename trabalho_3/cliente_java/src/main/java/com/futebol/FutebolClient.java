package com.futebol;

import com.futebol.ui.MainWindow;

import javax.swing.*;

/**
 * Classe principal do cliente Java para o sistema de controle de futebol
 */
public class FutebolClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}