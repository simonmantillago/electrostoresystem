package com.electrostoresystem.orderdetail.infrastructure.orderdetailui;

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

import com.electrostoresystem.orderdetail.application.CreateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;



import com.electrostoresystem.order.application.FindAllOrderUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;
import com.electrostoresystem.order.infrastructure.OrderRepository;



public class CreateOrderDetailUi extends JFrame {
    private final CreateOrderDetailUseCase createOrderDetailUseCase;
    private final OrderDetailUiController orderDetailUiController; 

    private JComboBox<String> orderBox;
    private JTextField productIdTxt, quantityTxt, unitPriceTxt;
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateOrderDetailUi(CreateOrderDetailUseCase createOrderDetailUseCase, OrderDetailUiController orderDetailUiController) { 
        this.createOrderDetailUseCase = createOrderDetailUseCase;
        this.orderDetailUiController = orderDetailUiController; 
    }

    public void frmRegOrderDetail() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create OrderDetail");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create OrderDetail");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        orderBox = new JComboBox<>();
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase  findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders){
            orderBox.addItem(order.getId() + ". Supplier Id: " + order.getSupplierId() + " / " + order.getDate());
        }

        productIdTxt = new JTextField();
        quantityTxt = new JTextField();
        unitPriceTxt = new JTextField();
        

      
        jButton2 = new JButton("Add Product");
        jButton3 = new JButton("Go back");

        
        jButton2.addActionListener(e -> saveOrderDetail());
        jButton3.addActionListener(e -> {
            dispose();
            orderDetailUiController.showCrudOptions(); // Adjusted to call the method in OrderDetailUiController
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Order:"), 1, 0);
        addComponent(orderBox, 1, 1);
        addComponent(new JLabel("Product:"), 2, 0);
        addComponent(productIdTxt, 2, 1);
        addComponent(new JLabel("Quantity:"), 3, 0);
        addComponent(quantityTxt, 3, 1);
        addComponent(new JLabel("Unit Price:"), 4, 0);
        addComponent(unitPriceTxt, 4, 1);
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 5;
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

    private void saveOrderDetail() {
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(Integer.parseInt(textBeforeDot(orderBox.getSelectedItem().toString())));
            orderDetail.setProductId(Integer.parseInt(productIdTxt.getText()));
            orderDetail.setQuantity(Integer.parseInt(quantityTxt.getText()));
            orderDetail.setUnitPrice(Float.parseFloat(unitPriceTxt.getText()));

            createOrderDetailUseCase.execute(orderDetail); 
            resetFields();
      
           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields(){
        productIdTxt.setText("");
        quantityTxt.setText("");
        unitPriceTxt.setText("");
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
