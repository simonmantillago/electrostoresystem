package com.electrostoresystem.clienttype.infrastructure.clienttypeui;

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

import com.electrostoresystem.clienttype.application.CreateClientTypeUseCase;
import com.electrostoresystem.clienttype.application.DeleteClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.application.UpdateClientTypeUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class ClientTypeUiController {
    private final CreateClientTypeUseCase createClientTypeUseCase;
    private final FindClientTypeByIdUseCase findClientTypeByIdUseCase;
    private final UpdateClientTypeUseCase updateClientTypeUseCase;
    private final DeleteClientTypeUseCase deleteClientTypeUseCase;
    private JFrame frame;

    



    public ClientTypeUiController(CreateClientTypeUseCase createClientTypeUseCase, FindClientTypeByIdUseCase findClientTypeByIdUseCase,
            UpdateClientTypeUseCase updateClientTypeUseCase, DeleteClientTypeUseCase deleteClientTypeUseCase) {
        this.createClientTypeUseCase = createClientTypeUseCase;
        this.findClientTypeByIdUseCase = findClientTypeByIdUseCase;
        this.updateClientTypeUseCase = updateClientTypeUseCase;
        this.deleteClientTypeUseCase = deleteClientTypeUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Client Types Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Client Types");
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

        // Botón Create ClientType
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateClientTypeUi clientTypeUi = new CreateClientTypeUi(createClientTypeUseCase, this);
            clientTypeUi.frmRegClientType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateClientTypeUi updateClientTypeUi = new UpdateClientTypeUi(updateClientTypeUseCase, this);
            updateClientTypeUi.frmUpdateClientType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindClientTypeByIdUi findClientTypeUi = new FindClientTypeByIdUi(findClientTypeByIdUseCase, this);
            findClientTypeUi.showFindClientType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteClientTypeUi deleteCustomerUi = new DeleteClientTypeUi(deleteClientTypeUseCase, this);
            deleteCustomerUi.showDeleteCustomer();
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
