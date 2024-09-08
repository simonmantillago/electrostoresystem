package com.electrostoresystem.clientphone.infrastructure.clientphoneui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhoneByPhoneUseCase;
import com.electrostoresystem.clientphone.application.UpdateClientPhoneUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class UpdateClientPhoneUi extends JFrame {
    private final UpdateClientPhoneUseCase updateClientPhoneUseCase;
    private final FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase;
    private final ClientPhoneUiController clientPhoneUiController;

    
    
    private JTextField jTextField1; 
    private JTextField jTextField2; 
    private JTextField jTextField3; 
    private JButton jButton1; 
    private JButton jButton2; 
    private JButton jButton3; 
    private JButton jButton4; 
    private String foundPhone;

    public UpdateClientPhoneUi(UpdateClientPhoneUseCase updateClientPhoneUseCase, FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase, ClientPhoneUiController clientPhoneUiController) {
        this.updateClientPhoneUseCase = updateClientPhoneUseCase;
        this.findClientPhoneByPhoneUseCase = findClientPhoneByPhoneUseCase;
        this.clientPhoneUiController = clientPhoneUiController;
    }

    public void frmUpdateClientPhone() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();
        FindAllClientPhoneUseCase findAllClientPhoneUseCase = new FindAllClientPhoneUseCase(clientPhoneService);
        initComponents(findAllClientPhoneUseCase);
        setVisible(true);
    }

    private void initComponents(FindAllClientPhoneUseCase findAllClientPhoneUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Client Phone");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update ClientPhone");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        
 

        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateClientPhone());
        jButton3.addActionListener(e -> {
            dispose();
            clientPhoneUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findClientPhone());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Phone Number:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Client Id:"), 3, 0);
        addComponent(jTextField2, 3, 1);
        addComponent(new JLabel("Phone Number:"), 4, 0);
        addComponent(jTextField3, 4, 1);
        

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 5, 0, 2);

        setLocationRelativeTo(null);

        hideComponents();
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
        gbc.anchor = GridBagConstraints.CENTER; 

        add(component, gbc);  
    }

    private void updateClientPhone() {
        try {
            ClientPhone clientPhone = new ClientPhone();
            clientPhone.setClientId(jTextField2.getText()); 
            clientPhone.setPhone(jTextField3.getText());  
        
            updateClientPhoneUseCase.execute(clientPhone,foundPhone);  
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();  
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findClientPhone() {
        String phoneToUpdate = jTextField1.getText();  // Obtener el número de teléfono a buscar
        Optional<ClientPhone> clientPhoneToUpdate = findClientPhoneByPhoneUseCase.execute(phoneToUpdate);
    
        if (clientPhoneToUpdate.isPresent()) {
            ClientPhone foundClientPhone = clientPhoneToUpdate.get();
            foundPhone = phoneToUpdate;  
            jTextField2.setText(foundClientPhone.getClientId());  
            jTextField3.setText(foundClientPhone.getPhone());  
            showComponents(); 
            revalidate();  
            repaint();  
        } else {
            JOptionPane.showMessageDialog(this, "ClientPhone not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void resetFields() {
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        hideComponents();
    }

    private void hideComponents() {
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jTextField1.setEditable(true);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        jTextField1.setEditable(false);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }



    
}

