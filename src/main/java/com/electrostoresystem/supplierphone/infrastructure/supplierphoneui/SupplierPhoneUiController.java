package com.electrostoresystem.supplierphone.infrastructure.supplierphoneui;


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

import com.electrostoresystem.supplierphone.application.CreateSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.DeleteSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindAllSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhoneByPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhonesBySupplierIdUseCase;
import com.electrostoresystem.supplierphone.application.UpdateSupplierPhoneUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class SupplierPhoneUiController {
    private final CreateSupplierPhoneUseCase createSupplierPhoneUseCase;
    private final FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase;
    private final UpdateSupplierPhoneUseCase updateSupplierPhoneUseCase;
    private final DeleteSupplierPhoneUseCase deleteSupplierPhoneUseCase;
    private final FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase;
    private final FindSupplierPhoneByPhoneUseCase findSupplierPhoneByPhoneUseCase;


    private JFrame frame;

    public SupplierPhoneUiController(CreateSupplierPhoneUseCase createSupplierPhoneUseCase, FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase,
            UpdateSupplierPhoneUseCase updateSupplierPhoneUseCase, DeleteSupplierPhoneUseCase deleteSupplierPhoneUseCase, FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase, FindSupplierPhoneByPhoneUseCase findSupplierPhoneByPhoneUseCase) {
        this.createSupplierPhoneUseCase = createSupplierPhoneUseCase;
        this.findSupplierPhonesBySupplierIdUseCase = findSupplierPhonesBySupplierIdUseCase;
        this.updateSupplierPhoneUseCase = updateSupplierPhoneUseCase;
        this.deleteSupplierPhoneUseCase = deleteSupplierPhoneUseCase;
        this.findAllSupplierPhoneUseCase = findAllSupplierPhoneUseCase;
        this.findSupplierPhoneByPhoneUseCase = findSupplierPhoneByPhoneUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Supplier Phones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Supplier Phones");
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

        // Botón Create SupplierPhone
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateSupplierPhoneUi supplierPhoneUi = new CreateSupplierPhoneUi(createSupplierPhoneUseCase, this);
            supplierPhoneUi.frmRegSupplierPhone();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateSupplierPhoneUi updateSupplierPhoneUi = new UpdateSupplierPhoneUi(updateSupplierPhoneUseCase, findSupplierPhoneByPhoneUseCase, this);
            updateSupplierPhoneUi.frmUpdateSupplierPhone();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllSupplierPhoneUi findAllSupplierPhoneUi = new FindAllSupplierPhoneUi(findAllSupplierPhoneUseCase, this);
            findAllSupplierPhoneUi.showAllSupplierPhones();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindSupplierPhonesBySupplierIdUi findSupplierPhonesBySupplierIdUi = new FindSupplierPhonesBySupplierIdUi(findSupplierPhonesBySupplierIdUseCase, this);
            findSupplierPhonesBySupplierIdUi.showFoundSupplierPhones();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteSupplierPhoneUi deleteCustomerUi = new DeleteSupplierPhoneUi(deleteSupplierPhoneUseCase, this);
            deleteCustomerUi.showDeleteSupplierPhone();
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
