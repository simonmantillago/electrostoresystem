package com.electrostoresystem.uicontroller.infrastructure;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class MainUiController {
    public static void createAndShowMainUI() {
        JFrame frame = new JFrame("Electro Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());

        ImageIcon salesImageIcon = new ImageIcon(MainUiController.class.getResource("/images/ElectroStore.png"));
        Image salesImage = salesImageIcon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        salesImageIcon = new ImageIcon(salesImage);

        JLabel imageLabel = new JLabel(salesImageIcon, JLabel.CENTER);
        topPanel.add(imageLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 

        Dimension buttonSize = new Dimension(200, 70);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton btnRegSale = createStyledButton("Sales", buttonSize, buttonFont);
        btnRegSale.addActionListener(e -> {
            frame.setVisible(false);
            openRegSale();
        });
        buttonPanel.add(btnRegSale, gbc);
        
        gbc.gridy++;
        JButton btnRefund = createStyledButton("Refunds", buttonSize, buttonFont);
        btnRefund.addActionListener(e -> {
            frame.setVisible(false);
            openRefundProduct();
        });
        buttonPanel.add(btnRefund, gbc);

        gbc.gridy++;
        JButton btnRegOrder = createStyledButton("Orders", buttonSize, buttonFont);
        btnRegOrder.addActionListener(e -> {
            frame.setVisible(false);
            openRegOrder();
        });
        buttonPanel.add(btnRegOrder, gbc);

        gbc.gridy++;
        JButton btnDataBase = createStyledButton("Database", buttonSize, buttonFont);
        btnDataBase.addActionListener(e -> {
            frame.setVisible(false);
            openCrudUiController();
        });
        buttonPanel.add(btnDataBase, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static void openRegSale() {
        RegSaleUiController regSaleUiController = new RegSaleUiController();
        regSaleUiController.frmRegSale();
    }

    private static void openRegOrder() {
        RegOrderUiController regOrderUiController = new RegOrderUiController();
        regOrderUiController.frmRegOrder();
    }

    private static void openRefundProduct() {
        RefundSaleProductUi refundSaleProductUi = new RefundSaleProductUi();
        refundSaleProductUi.showRefundUi();
    }

    private static void openCrudUiController() {
        CrudUiController.createAndShowMainUI();
    }
}
