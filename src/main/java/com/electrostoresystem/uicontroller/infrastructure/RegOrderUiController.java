package com.electrostoresystem.uicontroller.infrastructure;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import com.electrostoresystem.orderstatus.application.FindAllOrderStatusUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.order.application.CreateOrderUseCase;
import com.electrostoresystem.order.application.FindLastOrderUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.infrastructure.OrderRepository;
import com.electrostoresystem.orderdetail.application.CreateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.infrastructure.OrderDetailRepository;
import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;

import java.awt.Dimension;
public class RegOrderUiController extends JFrame {
    private JComboBox<String> statusBox, payBox, supplierBox;

    private JButton jButton2; 
    private JButton jButton3; 
    private JButton addProductBtn;
    private JPanel productPanel; 
    private Order lastOrder;

    public RegOrderUiController() {
    }

    public void frmRegOrder() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Order");
        setSize(500, 550);
    
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
    
        addProductBtn = new JButton("+");
        addProductBtn.addActionListener(e -> addProductRow());
    
        // Panel de productos y scroll pane
        productPanel = new JPanel(new GridBagLayout());
        
        // Agregar etiquetas de "Product Id" y "Quantity"
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        productPanel.add(new JLabel("Product Id"), gbc);
        
        gbc.gridx = 1;
        productPanel.add(new JLabel("Quantity"), gbc);
        
        gbc.gridx = 2;
        productPanel.add(new JLabel("Unit Price"), gbc);
        
        gbc.gridx = 3;
        productPanel.add(new JLabel(" "), gbc);
        
    
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 200));
    
        jButton2 = new JButton("Order");
        jButton3 = new JButton("Go back");
    
        jButton2.addActionListener(e -> regFullOrder());
    
        jButton3.addActionListener(e -> dispose());
        jButton3.addActionListener(e -> MainUiController.createAndShowMainUI());
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton3);
        buttonPanel.add(jButton2);
    
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Supplier:"), 1, 0);
        addComponent(supplierBox, 1, 1);
        addComponent(new JLabel("Status:"), 2, 0);
        addComponent(statusBox, 2, 1);
        addComponent(new JLabel("Payment Method:"), 3, 0);
        addComponent(payBox, 3, 1);
        addComponent(new JLabel("Add product:"), 4, 0);
        addComponent(addProductBtn, 4, 1);
        addComponent(scrollPane, 5, 0, 2);
        addComponent(buttonPanel, 6, 0, 2);
    
        setLocationRelativeTo(null);
    }

    private void addProductRow() {
   
        JTextField productIdField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField unitPriceField = new JTextField();

        JButton removeBtn = new JButton("-");
        removeBtn.addActionListener( e -> removeProductRow(productIdField, quantityField,unitPriceField, removeBtn));
     

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        productPanel.add(productIdField, gbc);

        gbc.gridx = 1;
        productPanel.add(quantityField, gbc);

        gbc.gridx = 2;
        productPanel.add(unitPriceField, gbc);
        
        gbc.gridx = 3;
        productPanel.add(removeBtn, gbc);

        productPanel.revalidate();
        productPanel.repaint();
    }

    private void removeProductRow(JTextField productIdField, JTextField quantityField,JTextField  unitPriceField, JButton removeBtn) {

        productPanel.remove(productIdField);
        productPanel.remove(quantityField);
        productPanel.remove(unitPriceField);
        productPanel.remove(removeBtn);
        productPanel.revalidate();
        productPanel.repaint();
   
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

        private void regFullOrder() {
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(new OrderRepository());
        FindLastOrderUseCase findLastOrderUseCase = new FindLastOrderUseCase(new OrderRepository());
        CreateOrderDetailUseCase createOrderDetailUseCase = new CreateOrderDetailUseCase(new OrderDetailRepository());
    
        try {
            Order order = new Order();
            order.setSupplierId(textBeforeDot(supplierBox.getSelectedItem().toString()));
            order.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
            order.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
    
            createOrderUseCase.execute(order);
            lastOrder = findLastOrderUseCase.execute();
            int lastOrderId = lastOrder.getId();
    
            Component[] components = productPanel.getComponents();

            for (int i = 0; i < components.length; i += 4) {
                if (components[i] instanceof JTextField && components[i + 1] instanceof JTextField && components[i + 2] instanceof JTextField) {
                    JTextField productIdField = (JTextField) components[i];
                    JTextField quantityField = (JTextField) components[i + 1];
                    JTextField unitPricField = (JTextField) components[i + 2];

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(lastOrderId);
                    orderDetail.setProductId(Integer.parseInt(productIdField.getText()));
                    orderDetail.setQuantity(Integer.parseInt(quantityField.getText()));
                    orderDetail.setUnitPrice(Float.parseFloat(unitPricField.getText()));

                    createOrderDetailUseCase.execute(orderDetail);
                }
            }

    
            resetFields();
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

    private void resetFields() {
        supplierBox.setSelectedIndex(0);
        payBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
    
        for (Component component : productPanel.getComponents()) {
            if (component instanceof JTextField || component instanceof JButton) {
                productPanel.remove(component);
            }
        }
    
        productPanel.revalidate();
        productPanel.repaint();
    }

}