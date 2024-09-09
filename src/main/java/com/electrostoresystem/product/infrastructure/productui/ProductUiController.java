package com.electrostoresystem.product.infrastructure.productui;

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

import com.electrostoresystem.product.application.CreateProductUseCase;
import com.electrostoresystem.product.application.DeleteProductUseCase;
import com.electrostoresystem.product.application.FindAllProductUseCase;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.application.UpdateProductUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class ProductUiController {
    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByNameUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final FindAllProductUseCase findAllProductUseCase;

    private JFrame frame;

    
    public ProductUiController(CreateProductUseCase createProductUseCase, FindProductByIdUseCase findProductByNameUseCase, UpdateProductUseCase updateProductUseCase, DeleteProductUseCase deleteProductUseCase, FindAllProductUseCase findAllProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.findProductByNameUseCase = findProductByNameUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.findAllProductUseCase = findAllProductUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("Products");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("Products");
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

        // Botón Create Product
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateProductUi productUi = new CreateProductUi(createProductUseCase, this);
            productUi.frmRegProduct();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateProductUi updateProductUi = new UpdateProductUi(updateProductUseCase,this);
            updateProductUi.frmUpdateProduct();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JButton btnFindAll = createStyledButton("Find All", buttonSize, buttonFont);
        btnFindAll.addActionListener(e -> {
            FindAllProductUi findAllProductUi = new FindAllProductUi(findAllProductUseCase, this);
            findAllProductUi.showAllProducts();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFindAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindProductByIdUi findProductByNameUi = new FindProductByIdUi(findProductByNameUseCase, this);
            findProductByNameUi.showFindProduct();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteProductUi deleteCustomerUi = new DeleteProductUi(deleteProductUseCase, this);
            deleteCustomerUi.showDeleteCustomer();
            frame.setVisible(false);
        });
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBackToMain = createStyledButton("Back to Main Menu", buttonSize, buttonFont);
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