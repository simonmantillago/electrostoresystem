package com.electrostoresystem.uicontroller.infrastructure;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;
import com.electrostoresystem.clientphone.infrastructure.clientphoneui.ClientPhoneUiController;
import com.electrostoresystem.clientphone.application.CreateClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.DeleteClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhoneByPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.application.UpdateClientPhoneUseCase;


public class CrudUiController{

    public CrudUiController() {

    }

    public static void createAndShowMainUI() {
        JFrame frame = new JFrame("Survey Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JButton btnClientPhones = createStyledButton("Client Phones", buttonSize, buttonFont);
        btnClientPhones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClientPhones.addActionListener(e -> {
            frame.setVisible(false);
            openClientPhoneUiController();
        });

        buttonPanel.add(btnClientPhones);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void openClientPhoneUiController() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();

        CreateClientPhoneUseCase createClientPhoneUseCase = new CreateClientPhoneUseCase(clientPhoneService);
        FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase = new FindClientPhoneByPhoneUseCase(clientPhoneService);
        UpdateClientPhoneUseCase updateClientPhoneUseCase = new UpdateClientPhoneUseCase(clientPhoneService);
        DeleteClientPhoneUseCase deleteClientPhoneUseCase = new DeleteClientPhoneUseCase(clientPhoneService);
        FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase = new FindClientPhonesByClientIdUseCase(clientPhoneService);
        FindAllClientPhoneUseCase findAllClientPhoneUseCase = new FindAllClientPhoneUseCase(clientPhoneService);



        ClientPhoneUiController clientPhoneUiController = new ClientPhoneUiController(createClientPhoneUseCase, findClientPhonesByClientIdUseCase, updateClientPhoneUseCase, deleteClientPhoneUseCase, findAllClientPhoneUseCase, findClientPhoneByPhoneUseCase);
        clientPhoneUiController.showCrudOptions();
    }

    private static JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}