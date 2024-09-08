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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.clientphone.application.DeleteClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;

public class DeleteClientPhoneUi extends JFrame {
    private final DeleteClientPhoneUseCase deleteClientPhoneUseCase;
    private final ClientPhoneUiController clientPhoneUiController;
    private JTextField idTxtBox;
    private JComboBox<String> clientPhoneOptions; 
    private JTextArea resultArea;
    
    public DeleteClientPhoneUi(DeleteClientPhoneUseCase deleteClientPhoneUseCase, ClientPhoneUiController clientPhoneUiController) {
        this.deleteClientPhoneUseCase = deleteClientPhoneUseCase;
        this.clientPhoneUiController = clientPhoneUiController;
    }
    
    public void showDeleteClientPhone() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Phone");
        setSize(600, 600);
        

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
        JLabel titleLabel = new JLabel("Delete Phone");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 1);
    
  
        JLabel lblId = new JLabel("Client Id:");
        addComponent(lblId, 1, 0);
    
       
        idTxtBox = new JTextField();
        addComponent(idTxtBox, 1, 1);  
    
      
        JButton findPhonesBtn = new JButton("Find");
        findPhonesBtn.addActionListener(e -> findPhonesComboBox());
        addComponent(findPhonesBtn, 1, 2);
    
        
        clientPhoneOptions = new JComboBox<>();
        addComponent(clientPhoneOptions, 2, 1, 1); 
    

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteClientPhone());
        addComponent(btnDelete, 3, 1, 1);
    
   
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
    
        // Botón para cerrar la ventana
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            clientPhoneUiController.showCrudOptions();
        });
        addComponent(btnClose, 5, 1, 1);
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

    private void deleteClientPhone() {
        String clientPhoneName = (String) clientPhoneOptions.getSelectedItem();
        ClientPhone deletedClientPhone = deleteClientPhoneUseCase.execute(clientPhoneName);

        if (deletedClientPhone != null) {
            String message = String.format(
                "ClientPhone deleted successfully:\n\n" +
                "Client Id: %s\n" +
                "Phone: %s\n",
                deletedClientPhone.getClientId(),
                deletedClientPhone.getPhone()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("ClientPhone deletion failed. ClientPhone not found.");
        }
    }

    private void findPhonesComboBox() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();
        FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase = new FindClientPhonesByClientIdUseCase(clientPhoneService);

        String clientIdText = idTxtBox.getText();
    
        if (clientIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Client Id", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            String clientId = clientIdText.trim();
    
            List<ClientPhone> clientPhones = findClientPhonesByClientIdUseCase.execute(clientId);
    
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

