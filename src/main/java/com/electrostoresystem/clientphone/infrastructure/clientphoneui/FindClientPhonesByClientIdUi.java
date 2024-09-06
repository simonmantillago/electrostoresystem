package com.electrostoresystem.clientphone.infrastructure.clientphoneui;

import java.util.List;
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
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;

public class FindClientPhonesByClientIdUi extends JFrame {
    private final FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase;
    private final ClientPhoneUiController clientPhoneUiController;
    private JTextField idTxtBox;
    private JComboBox<String> clientPhoneOptions; 
    
    public FindClientPhonesByClientIdUi(FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase, ClientPhoneUiController clientPhoneUiController) {
        this.findClientPhonesByClientIdUseCase = findClientPhonesByClientIdUseCase;
        this.clientPhoneUiController = clientPhoneUiController;
    }
    
    public void showFoundClientPhones() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Search Phones");
        setSize(500, 500);
        

        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Título
        JLabel titleLabel = new JLabel("Search Phones");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 1, 1);
    
        // Etiqueta de Client Id
        JLabel lblId = new JLabel("Client Id:");
        addComponent(lblId, 1, 0);
    
        // Campo de texto para Client Id
        idTxtBox = new JTextField();
        addComponent(idTxtBox, 1, 1);  // Corrige la adición de idTxtBox en vez de titleLabel
    
        // Botón para buscar los teléfonos
        JButton findPhonesBtn = new JButton("Find");
        findPhonesBtn.addActionListener(e -> findPhonesComboBox());
        addComponent(findPhonesBtn, 1, 2);  // Colocar el botón en la columna 2
    
        JLabel lblphones = new JLabel("Related Phones:");
        addComponent(lblphones, 2, 0);
        // ComboBox para mostrar los teléfonos asociados al Client Id
        clientPhoneOptions = new JComboBox<>();
        addComponent(clientPhoneOptions, 2, 1, 2);  // Abarcar dos columnas
        clientPhoneOptions.setVisible(false);
    

    
        // Botón para cerrar la ventana
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            clientPhoneUiController.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 3);
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
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }


    private void findPhonesComboBox() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();
        FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase = new FindClientPhonesByClientIdUseCase(clientPhoneService);

        clientPhoneOptions.setVisible(true);
        String clientIdText = idTxtBox.getText();
    
        if (clientIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Client Id", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            String clientId = clientIdText.trim();
    
            // Buscar los teléfonos asociados al clientId
            List<ClientPhone> clientPhones = findClientPhonesByClientIdUseCase.execute(clientId);
    
            // Limpiar las opciones anteriores del JComboBox
            clientPhoneOptions.removeAllItems();
    
            if (!clientPhones.isEmpty()) {
              
                for (ClientPhone clientPhone : clientPhones) {
                    clientPhoneOptions.addItem(clientPhone.getPhone());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No phones found for the specified Client Id.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while fetching client phones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}


