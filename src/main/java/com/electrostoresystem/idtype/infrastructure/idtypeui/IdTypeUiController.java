package com.electrostoresystem.idtype.infrastructure.idtypeui;

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

import com.electrostoresystem.idtype.application.CreateIdTypeUseCase;
import com.electrostoresystem.idtype.application.DeleteIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.application.UpdateIdTypeUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class IdTypeUiController {
    private final CreateIdTypeUseCase createIdTypeUseCase;
    private final FindIdTypeByIdUseCase findIdTypeByIdUseCase;
    private final UpdateIdTypeUseCase updateIdTypeUseCase;
    private final DeleteIdTypeUseCase deleteIdTypeUseCase;
    private JFrame frame;

    



    public IdTypeUiController(CreateIdTypeUseCase createIdTypeUseCase, FindIdTypeByIdUseCase findIdTypeByIdUseCase,
            UpdateIdTypeUseCase updateIdTypeUseCase, DeleteIdTypeUseCase deleteIdTypeUseCase) {
        this.createIdTypeUseCase = createIdTypeUseCase;
        this.findIdTypeByIdUseCase = findIdTypeByIdUseCase;
        this.updateIdTypeUseCase = updateIdTypeUseCase;
        this.deleteIdTypeUseCase = deleteIdTypeUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Id Types Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Id Types");
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

        // Botón Create IdType
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateIdTypeUi idTypeUi = new CreateIdTypeUi(createIdTypeUseCase, this);
            idTypeUi.frmRegIdType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateIdTypeUi updateIdTypeUi = new UpdateIdTypeUi(updateIdTypeUseCase, this);
            updateIdTypeUi.frmUpdateIdType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindIdTypeByIdUi findIdTypeUi = new FindIdTypeByIdUi(findIdTypeByIdUseCase, this);
            findIdTypeUi.showFindIdType();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteIdTypeUi deleteCustomerUi = new DeleteIdTypeUi(deleteIdTypeUseCase, this);
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
