package com.electrostoresystem.orderdetail.infrastructure.orderdetailui;

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

import com.electrostoresystem.orderdetail.application.CreateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.DeleteOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.FindOrderDetailByIdUseCase;
import com.electrostoresystem.orderdetail.application.UpdateOrderDetailUseCase;
import com.electrostoresystem.uicontroller.infrastructure.CrudUiController;

public class OrderDetailUiController {
    private final CreateOrderDetailUseCase createOrderDetailUseCase;
    private final FindOrderDetailByIdUseCase findOrderDetailByIdUseCase;
    private final UpdateOrderDetailUseCase updateOrderDetailUseCase;
    private final DeleteOrderDetailUseCase deleteOrderDetailUseCase;
    private JFrame frame;

    



    public OrderDetailUiController(CreateOrderDetailUseCase createOrderDetailUseCase, FindOrderDetailByIdUseCase findOrderDetailByIdUseCase,
            UpdateOrderDetailUseCase updateOrderDetailUseCase, DeleteOrderDetailUseCase deleteOrderDetailUseCase) {
        this.createOrderDetailUseCase = createOrderDetailUseCase;
        this.findOrderDetailByIdUseCase = findOrderDetailByIdUseCase;
        this.updateOrderDetailUseCase = updateOrderDetailUseCase;
        this.deleteOrderDetailUseCase = deleteOrderDetailUseCase;
    }

    public void showCrudOptions() {
        frame = new JFrame("OrderDetail Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear un panel principal con BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir título grande
        JLabel titleLabel = new JLabel("OrderDetails");
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

        // Botón Create OrderDetail
        JButton btnCreate = createStyledButton("Create", buttonSize, buttonFont);
        btnCreate.addActionListener(e -> {
            CreateOrderDetailUi orderDetailUi = new CreateOrderDetailUi(createOrderDetailUseCase, this);
            orderDetailUi.frmRegOrderDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnCreate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnUpdate = createStyledButton("Update", buttonSize, buttonFont);
        btnUpdate.addActionListener(e -> {
            UpdateOrderDetailUi updateOrderDetailUi = new UpdateOrderDetailUi(updateOrderDetailUseCase, this);
            updateOrderDetailUi.frmUpdateOrderDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnFind = createStyledButton("Find", buttonSize, buttonFont);
        btnFind.addActionListener(e -> {
            FindOrderDetailByIdUi findOrderDetailUi = new FindOrderDetailByIdUi(findOrderDetailByIdUseCase, this);
            findOrderDetailUi.showFindOrderDetail();
            frame.setVisible(false);
        });
        buttonPanel.add(btnFind);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnDelete = createStyledButton("Delete", buttonSize, buttonFont);
        btnDelete.addActionListener(e -> {
            DeleteOrderDetailUi deleteCustomerUi = new DeleteOrderDetailUi(deleteOrderDetailUseCase, this);
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
