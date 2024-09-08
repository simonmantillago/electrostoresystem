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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import com.electrostoresystem.orderstatus.application.UpdateOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindAllOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindOrderStatusByIdUseCase;  // Added import for FindOrderStatusByIdUseCase
import com.electrostoresystem.orderstatus.application.FindOrderStatusByNameUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;

public class UpdateOrderStatusUi extends JFrame {
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final OrderStatusUiController orderStatusUiController;

   private JComboBox<String> orderStatusOptions;
   private JTextField orderStatusNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int orderStatusFindId;

    public UpdateOrderStatusUi(UpdateOrderStatusUseCase updateOrderStatusUseCase, OrderStatusUiController orderStatusUiController) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.orderStatusUiController = orderStatusUiController;
    }

    public void frmUpdateOrderStatus() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindAllOrderStatusUseCase findAllOrderStatusUseCase = new FindAllOrderStatusUseCase(orderStatusService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update OrderStatus");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update OrderStatus");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        orderStatusOptions = new JComboBox<>();
        List<OrderStatus> orderStatuss = findAllOrderStatusUseCase.execute();
        for (OrderStatus orderStatus : orderStatuss) {
            orderStatusOptions.addItem(orderStatus.getName());
        }
        orderStatusNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateOrderStatus());
        jButton3.addActionListener(e -> {
            dispose();
            orderStatusUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findOrderStatus());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("OrderStatus:"), 1, 0);
        addComponent(orderStatusOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("OrderStatus Name:"), 3, 0);
        addComponent(orderStatusNameField, 3, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);

        hideComponents();
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

    private void updateOrderStatus() {
        try {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(orderStatusFindId);
            orderStatus.setName(orderStatusNameField.getText());

            updateOrderStatusUseCase.execute(orderStatus);
            JOptionPane.showMessageDialog(this, "OrderStatus updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findOrderStatus() {
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindOrderStatusByNameUseCase findOrderStatusByNameUseCase = new FindOrderStatusByNameUseCase(orderStatusService);
        FindOrderStatusByIdUseCase findOrderStatusByIdUseCase = new FindOrderStatusByIdUseCase(orderStatusService);

        String orderStatusCode = (String) orderStatusOptions.getSelectedItem();
        Optional<OrderStatus> orderStatusFind = findOrderStatusByNameUseCase.execute(orderStatusCode);
        orderStatusFindId = orderStatusFind.get().getId();

        Optional<OrderStatus> orderStatusToUpdate = findOrderStatusByIdUseCase.execute(orderStatusFindId);
    
        if (orderStatusToUpdate.isPresent()) {
            OrderStatus foundOrderStatus = orderStatusToUpdate.get();
            orderStatusNameField.setText(foundOrderStatus.getName());
            orderStatusOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "OrderStatus not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        orderStatusNameField.setText("");
        orderStatusOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        orderStatusNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        orderStatusNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        orderStatusOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindAllOrderStatusUseCase findAllOrderStatusUseCase = new FindAllOrderStatusUseCase(orderStatusService);
        
        List<OrderStatus> orderStatuss = findAllOrderStatusUseCase.execute();
        for (OrderStatus orderStatus : orderStatuss) {
            orderStatusOptions.addItem(orderStatus.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
