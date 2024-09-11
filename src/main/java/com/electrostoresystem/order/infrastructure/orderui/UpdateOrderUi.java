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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import com.electrostoresystem.order.application.UpdateOrderUseCase;
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
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;

public class UpdateOrderUi extends JFrame {
    private final UpdateOrderUseCase updateOrderUseCase;
    private final OrderUiController orderUiController;

   private JComboBox<String> orderOptions;
   private JComboBox<String> supplierBox,statusBox,payBox;
   private JTextField totalTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int orderFindId;
    private String dateFound;

    public UpdateOrderUi(UpdateOrderUseCase updateOrderUseCase, OrderUiController orderUiController) {
        this.updateOrderUseCase = updateOrderUseCase;
        this.orderUiController = orderUiController;
    }

    public void frmUpdateOrder() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase  findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Order");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update Order");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        orderOptions = new JComboBox<>();
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders) {

            Optional<Supplier> foundSupplier =  findSupplierByIdUseCase.execute(order.getSupplierId());
            String supplierName = foundSupplier.get().getName();

            orderOptions.addItem(order.getId() + ". " + supplierName + " " + order.getDate());
        }

        supplierBox = new JComboBox<>();
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

        totalTxt = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateOrder());
        jButton3.addActionListener(e -> {
            dispose();
            orderUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findOrder());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Order:"), 1, 0);
        addComponent(orderOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Supplier:"), 3, 0);
        addComponent(supplierBox, 3, 1);
        addComponent(new JLabel("Status:"), 4, 0);
        addComponent(statusBox, 4, 1);
        addComponent(new JLabel("Payment Method:"), 5, 0);
        addComponent(payBox, 5, 1);
        addComponent(new JLabel("Total:"), 6, 0);
        addComponent(totalTxt, 6, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 7;
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

    private void updateOrder() {
        try {
            Order order = new Order();
            order.setId(orderFindId);
            order.setDate(dateFound);
            order.setSupplierId(textBeforeDot(supplierBox.getSelectedItem().toString()));
            order.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
            order.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
            order.setTotal(Float.parseFloat(totalTxt.getText()));
        

            updateOrderUseCase.execute(order);
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findOrder() {
        OrderService orderService = new OrderRepository();
        FindOrderByIdUseCase findOrderByIdUseCase = new FindOrderByIdUseCase(orderService);

        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);

        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindOrderStatusByIdUseCase findOrderStatusByIdUseCase = new FindOrderStatusByIdUseCase(orderStatusService);

        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);

        int orderCode = (Integer.parseInt(textBeforeDot(orderOptions.getSelectedItem().toString())));
        Optional<Order> orderFind = findOrderByIdUseCase.execute(orderCode);
        orderFindId = orderFind.get().getId();
        dateFound = orderFind.get().getDate();

        
        if (orderFind.isPresent()) {
            Order foundOrder = orderFind.get();
            
            Optional<Supplier> foundSupplier = findSupplierByIdUseCase.execute(foundOrder.getSupplierId());
            Optional<OrderStatus> foundOrderStatus = findOrderStatusByIdUseCase.execute(foundOrder.getStatusId());
            Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(foundOrder.getPayMethod());
            
            supplierBox.setSelectedItem(foundOrder.getSupplierId() + ". " + foundSupplier.get().getName());
            statusBox.setSelectedItem(foundOrder.getStatusId() + ". " + foundOrderStatus.get().getName());
            payBox.setSelectedItem(foundOrder.getPayMethod() + ". " + foundPaymentMethods.get().getName());
            totalTxt.setText(String.valueOf(foundOrder.getTotal()));

            orderOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "Order not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        orderOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        supplierBox.setVisible(false);
        statusBox.setVisible(false);
        payBox.setVisible(false);
        totalTxt.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        supplierBox.setVisible(true);
        statusBox.setVisible(true);
        payBox.setVisible(true);
        totalTxt.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        orderOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase  findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);
        
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
