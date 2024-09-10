package com.electrostoresystem.order.infrastructure.orderui;

    import java.awt.Component;
    import java.awt.Font;
    import java.awt.GridBagConstraints;
    import java.awt.GridBagLayout;
    import java.awt.Insets;

    import java.util.List;

    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
    import javax.swing.JTextField;
    import javax.swing.SwingConstants;
    import javax.swing.JComboBox;

import com.electrostoresystem.order.application.CreateOrderUseCase;
import com.electrostoresystem.order.domain.entity.Order;

import com.electrostoresystem.orderstatus.application.FindAllOrderStatusUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;

import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;


public class CreateOrderUi extends JFrame {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderUiController orderUiController; 

    private JComboBox<String> supplierBox, statusBox,payBox; 
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateOrderUi(CreateOrderUseCase createOrderUseCase, OrderUiController orderUiController) { 
        this.createOrderUseCase = createOrderUseCase;
        this.orderUiController = orderUiController; 
    }

    public void frmRegOrder() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Order");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create Order");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        supplierBox = new JComboBox<>();
        SupplierService supplierService = new SupplierRepository();
        FindAllSupplierUseCase  findAllSupplierUseCase = new FindAllSupplierUseCase(supplierService);
        List<Supplier> suppliers = findAllSupplierUseCase.execute();
        for (Supplier supplier : suppliers){
            supplierBox.addItem(supplier.getId() + ". " + supplier.getName());
        }

        statusBox = new JComboBox<>();
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindAllOrderStatusUseCase  findAllOrderStatusUseCase = new FindAllOrderStatusUseCase(orderStatusService);
        List<OrderStatus> statuses = findAllOrderStatusUseCase.execute();
        for (OrderStatus orderStatus : statuses){
            statusBox.addItem(orderStatus.getId() + ". " + orderStatus.getName());
        }
        
        
        payBox = new JComboBox<>();
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase  findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(PaymentMethodsService);
        List<PaymentMethods> methods = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods PaymentMethods : methods){
            payBox.addItem(PaymentMethods.getId() + ". " + PaymentMethods.getName());
        }
        

        
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        
        jButton2.addActionListener(e -> saveOrder());
        jButton3.addActionListener(e -> {
            dispose();
            orderUiController.showCrudOptions(); // Adjusted to call the method in OrderUiController
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Supplier:"), 1, 0);
        addComponent(supplierBox, 1, 1);
        addComponent(new JLabel("Status:"), 2, 0);
        addComponent(statusBox, 2, 1);
        addComponent(new JLabel("Payment Method:"), 3, 0);
        addComponent(payBox, 3, 1);
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);
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
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        add(component, gbc);
    }

    private void saveOrder() {
        try {
            Order order = new Order();
            order.setSupplierId(textBeforeDot(supplierBox.getSelectedItem().toString()));
            order.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
            order.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
    

            createOrderUseCase.execute(order); 
            JOptionPane.showMessageDialog(this, "Order added successfully!");
           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
