package com.electrostoresystem.saledetail.infrastructure.saledetailui;

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

import com.electrostoresystem.saledetail.application.CreateSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.DeleteSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailByIdUseCase;
import com.electrostoresystem.saledetail.application.UpdateSaleDetailUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class SaleDetailUiController {
    private final CreateSaleDetailUseCase createSaleDetailUseCase;
    private final FindSaleDetailByIdUseCase findSaleDetailByIdUseCase;
    private final UpdateSaleDetailUseCase updateSaleDetailUseCase;
    private final DeleteSaleDetailUseCase deleteSaleDetailUseCase;
    private JFrame frame;

    



    public SaleDetailUiController(CreateSaleDetailUseCase createSaleDetailUseCase, FindSaleDetailByIdUseCase findSaleDetailByIdUseCase,
            UpdateSaleDetailUseCase updateSaleDetailUseCase, DeleteSaleDetailUseCase deleteSaleDetailUseCase) {
        this.createSaleDetailUseCase = createSaleDetailUseCase;
        this.findSaleDetailByIdUseCase = findSaleDetailByIdUseCase;
        this.updateSaleDetailUseCase = updateSaleDetailUseCase;
        this.deleteSaleDetailUseCase = deleteSaleDetailUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("SaleDetail Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("SaleDetails");
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

        // Botón Create SaleDetail
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateSaleDetailUi saleDetailUi = new CreateSaleDetailUi(createSaleDetailUseCase, this);
            saleDetailUi.frmRegSaleDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateSaleDetailUi updateSaleDetailUi = new UpdateSaleDetailUi(updateSaleDetailUseCase, this);
            updateSaleDetailUi.frmUpdateSaleDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindSaleDetailByIdUi findSaleDetailUi = new FindSaleDetailByIdUi(findSaleDetailByIdUseCase, this);
            findSaleDetailUi.showFindSaleDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteSaleDetailUi deleteCustomerUi = new DeleteSaleDetailUi(deleteSaleDetailUseCase, this);
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
