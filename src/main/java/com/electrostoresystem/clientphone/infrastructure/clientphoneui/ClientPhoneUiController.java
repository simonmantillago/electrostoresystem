package com.electrostoresystem.clientphone.infrastructure.clientphoneui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.electrostoresystem.clientphone.application.CreateClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.DeleteClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhoneByPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.application.UpdateClientPhoneUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class ClientPhoneUiController {
    private final CreateClientPhoneUseCase createClientPhoneUseCase;
    private final FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase;
    private final UpdateClientPhoneUseCase updateClientPhoneUseCase;
    private final DeleteClientPhoneUseCase deleteClientPhoneUseCase;
    private final FindAllClientPhoneUseCase findAllClientPhoneUseCase;
    private final FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase;


    private JFrame frame;

    public ClientPhoneUiController(CreateClientPhoneUseCase createClientPhoneUseCase, FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase,
            UpdateClientPhoneUseCase updateClientPhoneUseCase, DeleteClientPhoneUseCase deleteClientPhoneUseCase, FindAllClientPhoneUseCase findAllClientPhoneUseCase, FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase) {
        this.createClientPhoneUseCase = createClientPhoneUseCase;
        this.findClientPhonesByClientIdUseCase = findClientPhonesByClientIdUseCase;
        this.updateClientPhoneUseCase = updateClientPhoneUseCase;
        this.deleteClientPhoneUseCase = deleteClientPhoneUseCase;
        this.findAllClientPhoneUseCase = findAllClientPhoneUseCase;
        this.findClientPhoneByPhoneUseCase = findClientPhoneByPhoneUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Client Phones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Client Phones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo común para los botones
        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Botón Create ClientPhone
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateClientPhoneUi clientPhoneUi = new CreateClientPhoneUi(createClientPhoneUseCase, this);
            clientPhoneUi.frmRegClientPhone();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateClientPhoneUi updateClientPhoneUi = new UpdateClientPhoneUi(updateClientPhoneUseCase, findClientPhoneByPhoneUseCase, this);
            updateClientPhoneUi.frmUpdateClientPhone();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllClientPhoneUi findAllClientPhoneUi = new FindAllClientPhoneUi(findAllClientPhoneUseCase, this);
            findAllClientPhoneUi.showAllClientPhones();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindClientPhonesByClientIdUi findClientPhonesByClientIdUi = new FindClientPhonesByClientIdUi(findClientPhonesByClientIdUseCase, this);
            findClientPhonesByClientIdUi.showFoundClientPhones();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteClientPhoneUi deleteCustomerUi = new DeleteClientPhoneUi(deleteClientPhoneUseCase, this);
            deleteCustomerUi.showDeleteClientPhone();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Go Back", buttonSize, buttonFont);
        btnBackToMain.addActionListener(e -> {
            frame.dispose(); 
            CrudUiController.createAndShowMainUI(); 
        });
        buttonPanel.add(btnBackToMain);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
