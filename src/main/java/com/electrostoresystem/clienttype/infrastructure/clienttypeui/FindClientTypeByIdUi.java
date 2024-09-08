package com.electrostoresystem.clienttype.infrastructure.clienttypeui;

import java.util.Optional;


import java.util.List;
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

import com.electrostoresystem.clienttype.application.FindAllClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByNameUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;

public class FindClientTypeByIdUi extends JFrame {
    private final FindClientTypeByIdUseCase findClientTypeByIdUseCase;
    private final ClientTypeUiController clientTypeUiController;
    private JComboBox<String> clientTypeOptions; 
    private JTextArea resultArea;



    public FindClientTypeByIdUi(FindClientTypeByIdUseCase findClientTypeByIdUseCase, ClientTypeUiController clientTypeUiController) {
        this.findClientTypeByIdUseCase = findClientTypeByIdUseCase;
        this.clientTypeUiController = clientTypeUiController;
    }

    public void showFindClientType() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find ClientType");
        setSize(500, 500);

        initComponents();
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

        JLabel titleLabel = new JLabel("Find ClientType");
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

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findClientType());
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


    private void findClientType() {
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByNameUseCase findClientTypeByNameUseCase = new FindClientTypeByNameUseCase(clientTypeService);

        String clientTypeCode = (String) clientTypeOptions.getSelectedItem();
        Optional<ClientType> clientTypeFind = findClientTypeByNameUseCase.execute(clientTypeCode);
        int clientTypeFindId = clientTypeFind.get().getId();


        Optional<ClientType> clientTypeOpt = findClientTypeByIdUseCase.execute(clientTypeFindId);
        if (clientTypeOpt.isPresent()) {
            ClientType clientType = clientTypeOpt.get();
            String message = String.format(
                "ClientType found:\n\n" +
                "Id: %d\n" +
                "ClientType Name: %s\n",
                clientType.getId(),
                clientType.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("ClientType not found!");
        }
    }
}
