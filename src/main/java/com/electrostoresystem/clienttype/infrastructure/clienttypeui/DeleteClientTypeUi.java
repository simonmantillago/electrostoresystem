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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.electrostoresystem.clienttype.application.DeleteClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindAllClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByNameUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;

public class DeleteClientTypeUi extends JFrame {
    private final DeleteClientTypeUseCase deleteClientTypeUseCase;
    private final ClientTypeUiController clientTypeUiController;
    private JComboBox<String> clientTypeOptions; 
    private JTextArea resultArea;
    
    public DeleteClientTypeUi(DeleteClientTypeUseCase deleteClientTypeUseCase, ClientTypeUiController clientTypeUiController) {
        this.deleteClientTypeUseCase = deleteClientTypeUseCase;
        this.clientTypeUiController = clientTypeUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete ClientType");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindAllClientTypeUseCase findAllClientTypeUseCase = new FindAllClientTypeUseCase(clientTypeService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete ClientType");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("ClientType:");
        addComponent(lblId, 1, 0);

        clientTypeOptions = new JComboBox<>();
        List<ClientType> clientTypes = findAllClientTypeUseCase.execute();
        for (ClientType clientType : clientTypes) {
            clientTypeOptions.addItem(clientType.getName());
        }
        addComponent(clientTypeOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteClientType());
        addComponent(btnDelete, 2, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            clientTypeUiController.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 2);
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

    private void deleteClientType() {
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByNameUseCase findClientTypeByNameUseCase = new FindClientTypeByNameUseCase(clientTypeService);

        String clientTypeCode = (String) clientTypeOptions.getSelectedItem();
        Optional<ClientType> clientTypeFind = findClientTypeByNameUseCase.execute(clientTypeCode);
        int clientTypeFindId = clientTypeFind.get().getId();
        
        ClientType deletedClientType = deleteClientTypeUseCase.execute(clientTypeFindId);
        reloadComboBoxOptions();

        if (deletedClientType != null) {
            String message = String.format(
                "ClientType deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedClientType.getId(),
                deletedClientType.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("ClientType not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        clientTypeOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindAllClientTypeUseCase findAllClientTypeUseCase = new FindAllClientTypeUseCase(clientTypeService);
        
        List<ClientType> countries = findAllClientTypeUseCase.execute();
        for (ClientType clientType : countries) {
            clientTypeOptions.addItem(clientType.getName()); // Añade los clientTypes actualizados al JComboBox
        }
    }
    
}
