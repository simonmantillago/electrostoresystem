package com.electrostoresystem.salestatus.infrastructure.salestatusui;

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

import com.electrostoresystem.salestatus.application.CreateSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.DeleteSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.application.UpdateSaleStatusUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class SaleStatusUiController {
    private final CreateSaleStatusUseCase createSaleStatusUseCase;
    private final FindSaleStatusByIdUseCase findSaleStatusByIdUseCase;
    private final UpdateSaleStatusUseCase updateSaleStatusUseCase;
    private final DeleteSaleStatusUseCase deleteSaleStatusUseCase;
    private JFrame frame;

    



    public SaleStatusUiController(CreateSaleStatusUseCase createSaleStatusUseCase, FindSaleStatusByIdUseCase findSaleStatusByIdUseCase,
            UpdateSaleStatusUseCase updateSaleStatusUseCase, DeleteSaleStatusUseCase deleteSaleStatusUseCase) {
        this.createSaleStatusUseCase = createSaleStatusUseCase;
        this.findSaleStatusByIdUseCase = findSaleStatusByIdUseCase;
        this.updateSaleStatusUseCase = updateSaleStatusUseCase;
        this.deleteSaleStatusUseCase = deleteSaleStatusUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Sale Status Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Sale Status");
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

        // Botón Create SaleStatus
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateSaleStatusUi saleStatusUi = new CreateSaleStatusUi(createSaleStatusUseCase, this);
            saleStatusUi.frmRegSaleStatus();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateSaleStatusUi updateSaleStatusUi = new UpdateSaleStatusUi(updateSaleStatusUseCase, this);
            updateSaleStatusUi.frmUpdateSaleStatus();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindSaleStatusByIdUi findSaleStatusUi = new FindSaleStatusByIdUi(findSaleStatusByIdUseCase, this);
            findSaleStatusUi.showFindSaleStatus();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteSaleStatusUi deleteCustomerUi = new DeleteSaleStatusUi(deleteSaleStatusUseCase, this);
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
