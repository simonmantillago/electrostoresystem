package com.electrostoresystem;

import javax.swing.SwingUtilities;

import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CrudUiController.createAndShowMainUI();; 
 
        });
    }
}