package com.electrostoresystem;

import javax.swing.SwingUtilities;

import com.electrostoresystem.uicontroller.infrastructure.MainUiController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUiController.createAndShowMainUI();;
 
        });
    }
}