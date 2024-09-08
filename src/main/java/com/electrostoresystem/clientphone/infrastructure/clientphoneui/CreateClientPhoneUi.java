package com.electrostoresystem.clientphone.infrastructure.clientphoneui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import com.electrostoresystem.clientphone.application.CreateClientPhoneUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;

public class CreateClientPhoneUi extends JFrame {
    private final CreateClientPhoneUseCase createClientPhoneUseCase;
    private final ClientPhoneUiController clientPhoneUiController; 

    private JTextField jTextField1; 
    private JTextField jTextField2; 
    private JButton jButton1; 
    private JButton jButton2; 
    private JButton jButton3; 

    public CreateClientPhoneUi(CreateClientPhoneUseCase createClientPhoneUseCase, ClientPhoneUiController clientPhoneUiController) { 
        this.createClientPhoneUseCase = createClientPhoneUseCase;
        this.clientPhoneUiController = clientPhoneUiController; 
    }

    public void frmRegClientPhone() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Phone");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Add Phone");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField1 = new JTextField();
        jTextField2 = new JTextField();


        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveClientPhone());
        jButton3.addActionListener(e -> {
            dispose();
            clientPhoneUiController.showCrudOptions(); 
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Client ID:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(new JLabel("Phone Number:"), 2, 0);
        addComponent(jTextField2, 2, 1);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);
    }

    private void addComponent(Component component, int row, int col) {
        addComponent(component, row, col, 1);
    }

    private void addComponent(Component component, int row, int col, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        add(component, gbc);
    }

    private void saveClientPhone() {
        try {
            ClientPhone clientPhone = new ClientPhone();
            clientPhone.setClientId(jTextField1.getText());
            clientPhone.setPhone(jTextField2.getText());


            createClientPhoneUseCase.execute(clientPhone); 
           
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField1.setText("");
        jTextField2.setText("");

    }
}
