package com.electrostoresystem.orderstatus.infrastructure.orderstatusui;

import java.util.List;
import java.util.Optional;
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


import com.electrostoresystem.orderstatus.application.DeleteOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindAllOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindOrderStatusByNameUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;

public class DeleteOrderStatusUi extends JFrame {
    private final DeleteOrderStatusUseCase deleteOrderStatusUseCase;
    private final OrderStatusUiController orderStatusUiController;
    private JComboBox<String> orderStatusOptions; 
    private JTextArea resultArea;
    
    public DeleteOrderStatusUi(DeleteOrderStatusUseCase deleteOrderStatusUseCase, OrderStatusUiController orderStatusUiController) {
        this.deleteOrderStatusUseCase = deleteOrderStatusUseCase;
        this.orderStatusUiController = orderStatusUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete OrderStatus");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindAllOrderStatusUseCase findAllOrderStatusUseCase = new FindAllOrderStatusUseCase(orderStatusService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete OrderStatus");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("OrderStatus:");
        addComponent(lblId, 1, 0);

        orderStatusOptions = new JComboBox<>();
        List<OrderStatus> orderStatuss = findAllOrderStatusUseCase.execute();
        for (OrderStatus orderStatus : orderStatuss) {
            orderStatusOptions.addItem(orderStatus.getName());
        }
        addComponent(orderStatusOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteOrderStatus());
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
            orderStatusUiController.showCrudOptions();
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

    private void deleteOrderStatus() {
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindOrderStatusByNameUseCase findOrderStatusByNameUseCase = new FindOrderStatusByNameUseCase(orderStatusService);

        String orderStatusCode = (String) orderStatusOptions.getSelectedItem();
        Optional<OrderStatus> orderStatusFind = findOrderStatusByNameUseCase.execute(orderStatusCode);
        int orderStatusFindId = orderStatusFind.get().getId();
        
        OrderStatus deletedOrderStatus = deleteOrderStatusUseCase.execute(orderStatusFindId);
        reloadComboBoxOptions();

        if (deletedOrderStatus != null) {
            String message = String.format(
                "OrderStatus deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedOrderStatus.getId(),
                deletedOrderStatus.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("OrderStatus not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        orderStatusOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindAllOrderStatusUseCase findAllOrderStatusUseCase = new FindAllOrderStatusUseCase(orderStatusService);
        
        List<OrderStatus> countries = findAllOrderStatusUseCase.execute();
        for (OrderStatus orderStatus : countries) {
            orderStatusOptions.addItem(orderStatus.getName()); // Añade los orderStatuss actualizados al JComboBox
        }
    }
    
}
