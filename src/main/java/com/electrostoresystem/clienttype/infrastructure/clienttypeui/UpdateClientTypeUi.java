package com.electrostoresystem.clienttype.infrastructure.clienttypeui;

import java.util.List;
import java.util.Optional;
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
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import com.electrostoresystem.clienttype.application.UpdateClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindAllClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;  // Added import for FindClientTypeByIdUseCase
import com.electrostoresystem.clienttype.application.FindClientTypeByNameUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;

public class UpdateClientTypeUi extends JFrame {
    private final UpdateClientTypeUseCase updateClientTypeUseCase;
    private final ClientTypeUiController clientTypeUiController;

   private JComboBox<String> clientTypeOptions;
   private JTextField clientTypeNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int clientTypeFindId;

    public UpdateClientTypeUi(UpdateClientTypeUseCase updateClientTypeUseCase, ClientTypeUiController clientTypeUiController) {
        this.updateClientTypeUseCase = updateClientTypeUseCase;
        this.clientTypeUiController = clientTypeUiController;
    }

    public void frmUpdateClientType() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindAllClientTypeUseCase findAllClientTypeUseCase = new FindAllClientTypeUseCase(clientTypeService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update ClientType");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update ClientType");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        clientTypeOptions = new JComboBox<>();
        List<ClientType> clientTypes = findAllClientTypeUseCase.execute();
        for (ClientType clientType : clientTypes) {
            clientTypeOptions.addItem(clientType.getName());
        }
        clientTypeNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateClientType());
        jButton3.addActionListener(e -> {
            dispose();
            clientTypeUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findClientType());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("ClientType:"), 1, 0);
        addComponent(clientTypeOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("ClientType Name:"), 3, 0);
        addComponent(clientTypeNameField, 3, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

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
        add(component, gbc);
    }

    private void updateClientType() {
        try {
            ClientType clientType = new ClientType();
            clientType.setId(clientTypeFindId);
            clientType.setName(clientTypeNameField.getText());

            updateClientTypeUseCase.execute(clientType);
            JOptionPane.showMessageDialog(this, "ClientType updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findClientType() {
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByNameUseCase findClientTypeByNameUseCase = new FindClientTypeByNameUseCase(clientTypeService);
        FindClientTypeByIdUseCase findClientTypeByIdUseCase = new FindClientTypeByIdUseCase(clientTypeService);

        String clientTypeCode = (String) clientTypeOptions.getSelectedItem();
        Optional<ClientType> clientTypeFind = findClientTypeByNameUseCase.execute(clientTypeCode);
        clientTypeFindId = clientTypeFind.get().getId();

        Optional<ClientType> clientTypeToUpdate = findClientTypeByIdUseCase.execute(clientTypeFindId);
    
        if (clientTypeToUpdate.isPresent()) {
            ClientType foundClientType = clientTypeToUpdate.get();
            clientTypeNameField.setText(foundClientType.getName());
            clientTypeOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "ClientType not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        clientTypeNameField.setText("");
        clientTypeOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        clientTypeNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        clientTypeNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        clientTypeOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindAllClientTypeUseCase findAllClientTypeUseCase = new FindAllClientTypeUseCase(clientTypeService);
        
        List<ClientType> clientTypes = findAllClientTypeUseCase.execute();
        for (ClientType clientType : clientTypes) {
            clientTypeOptions.addItem(clientType.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
