package com.electrostoresystem.order.infrastructure.orderui;

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

import com.electrostoresystem.order.application.DeleteOrderUseCase;
import com.electrostoresystem.order.application.FindAllOrderUseCase;
import com.electrostoresystem.order.application.FindOrderByIdUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;
import com.electrostoresystem.order.infrastructure.OrderRepository;
import com.electrostoresystem.orderstatus.application.FindAllOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindOrderStatusByIdUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;
import com.electrostoresystem.orderstatus.infrastructure.orderstatusui.FindOrderStatusByIdUi;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.paymentmethods.infrastructure.paymentmethodsui.FindPaymentMethodsByIdUi;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;


public class DeleteOrderUi extends JFrame {
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final OrderUiController orderUiController;
    private JComboBox<String> orderOptions; 
    private JTextArea resultArea;
    
    public DeleteOrderUi(DeleteOrderUseCase deleteOrderUseCase, OrderUiController orderUiController) {
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.orderUiController = orderUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Order");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase  findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Order");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Order:");
        addComponent(lblId, 1, 0);

        orderOptions = new JComboBox<>();
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders) {

            Optional<Supplier> foundSupplier =  findSupplierByIdUseCase.execute(order.getSupplierId());
            String supplierName = foundSupplier.get().getName();

            orderOptions.addItem(order.getId() + ". " + supplierName + " " + order.getDate());
        }
        addComponent(orderOptions, 1, 1);

        JLabel lblReminder = new JLabel("Reminder: Delete All the details Related to this order first");
        addComponent(lblReminder, 2, 0, 2);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteOrder());
        addComponent(btnDelete, 3, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            orderUiController.showCrudOptions();
        });
        addComponent(btnClose, 5, 0, 2);
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

    private void deleteOrder() {
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindOrderStatusByIdUseCase  findOrderStatusByIdUseCase = new FindOrderStatusByIdUseCase(orderStatusService);
        
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase  findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);

        int orderCode = (Integer.parseInt(textBeforeDot(orderOptions.getSelectedItem().toString())));
        Order deletedOrder = deleteOrderUseCase.execute(orderCode);

        Optional<OrderStatus> foundOrderStatus = findOrderStatusByIdUseCase.execute(deletedOrder.getStatusId());
        Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(deletedOrder.getPayMethod());

        reloadComboBoxOptions();

        if (deletedOrder != null) {
            String message = String.format(
                "Order deleted successfully:\n\n" +
                "Id: %s\n" +
                "Order Date: %s\n"+
                "Supplier Id: %s \n"+
                "Status: %s\n"+
                "Payment Method: %s \n"+
                "Total: %.2f \n",
                deletedOrder.getId(),
                deletedOrder.getDate(),
                deletedOrder.getSupplierId(),
                foundOrderStatus.get().getName(),
                foundPaymentMethods.get().getName(),
                deletedOrder.getTotal()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Order not found or deletion failed.");
        }
    }
    private void reloadComboBoxOptions() {
        orderOptions.removeAllItems();
    
        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase  findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders) {

            Optional<Supplier> foundSupplier =  findSupplierByIdUseCase.execute(order.getSupplierId());
            String supplierName = foundSupplier.get().getName();

            orderOptions.addItem(order.getId() + ". " + supplierName + " " + order.getDate());
        }
    }

    private String textBeforeDot(String text) {
        int position = text.indexOf('.');
        if (position != -1) {
            return text.substring(0, position);
        } else {
            return text;
        }
    }
}
