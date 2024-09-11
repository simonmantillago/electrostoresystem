package com.electrostoresystem.orderdetail.infrastructure.orderdetailui;

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

import com.electrostoresystem.orderdetail.application.UpdateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.FindOrderDetailByIdUseCase;
import com.electrostoresystem.orderdetail.application.FindOrderDetailsByOrderIdUseCase;
import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;
import com.electrostoresystem.orderdetail.infrastructure.OrderDetailRepository;

import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;

import com.electrostoresystem.order.application.FindAllOrderUseCase;
import com.electrostoresystem.order.application.FindOrderByIdUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;
import com.electrostoresystem.order.infrastructure.OrderRepository;

public class UpdateOrderDetailUi extends JFrame {
    private final UpdateOrderDetailUseCase updateOrderDetailUseCase;
    private final OrderDetailUiController orderDetailUiController;

    private JComboBox<String> orderBox,orderDetailOptions;

    private JTextField quantityTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int orderId;


    public UpdateOrderDetailUi(UpdateOrderDetailUseCase updateOrderDetailUseCase, OrderDetailUiController orderDetailUiController) {
        this.updateOrderDetailUseCase = updateOrderDetailUseCase;
        this.orderDetailUiController = orderDetailUiController;
    }

    public void frmUpdateOrderDetail() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update OrderDetail");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update OrderDetail");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);


        orderBox = new JComboBox<>();
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase  findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders){
            orderBox.addItem(order.getId() + ". " + order.getSupplierId() + " " + order.getDate());
        }

        orderBox.addActionListener(e -> updateOrderDetailBox());
        

        orderDetailOptions = new JComboBox<>();

        quantityTxt = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateOrderDetail());
        jButton3.addActionListener(e -> {
            dispose();
            orderDetailUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findOrderDetail());


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Order:"), 1, 0);
        addComponent(orderBox, 1, 1);
        addComponent(new JLabel("Product:"), 2, 0);
        addComponent(orderDetailOptions, 2, 1);
        addComponent(jButton4, 3, 0,2);
        addComponent(new JLabel("Quantity:"), 4, 0);
        addComponent(quantityTxt, 4, 1);




        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 5;
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

    private void updateOrderDetail() {
        try {
            OrderDetailService orderDetailService = new OrderDetailRepository();
            FindOrderDetailByIdUseCase findOrderDetailByIdUseCase = new FindOrderDetailByIdUseCase(orderDetailService);
    
            int orderDetailCode = (Integer.parseInt(textBeforeDot(orderDetailOptions.getSelectedItem().toString())));
            Optional<OrderDetail> orderDetailFind = findOrderDetailByIdUseCase.execute(orderDetailCode);

            OrderDetail foundOrderDetail = orderDetailFind.get();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(orderDetailCode);
            orderDetail.setOrderId(foundOrderDetail.getOrderId());
            orderDetail.setProductId(foundOrderDetail.getProductId());
            orderDetail.setQuantity(Integer.parseInt(quantityTxt.getText()));
            orderDetail.setUnitPrice(foundOrderDetail.getUnitPrice());
            orderDetail.setUnitPrice(foundOrderDetail.getUnitPrice());
      

        

            updateOrderDetailUseCase.execute(orderDetail);
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findOrderDetail() {
        OrderDetailService orderDetailService = new OrderDetailRepository();
        FindOrderDetailByIdUseCase findOrderDetailByIdUseCase = new FindOrderDetailByIdUseCase(orderDetailService);

        int orderDetailCode = (Integer.parseInt(textBeforeDot(orderDetailOptions.getSelectedItem().toString())));
        Optional<OrderDetail> orderDetailFind = findOrderDetailByIdUseCase.execute(orderDetailCode);

        
        if (orderDetailFind.isPresent()) {
            OrderDetail foundOrderDetail = orderDetailFind.get();
            
            quantityTxt.setText(String.valueOf(foundOrderDetail.getQuantity()));
            orderDetailOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "OrderDetail not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        orderDetailOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        quantityTxt.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        quantityTxt.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        orderDetailOptions.removeAllItems(); 
    
        OrderDetailService orderDetailService = new OrderDetailRepository();
        FindOrderDetailsByOrderIdUseCase findOrderDetailsByOrderUseCase = new FindOrderDetailsByOrderIdUseCase(orderDetailService);

        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        
        List<OrderDetail> orderDetails = findOrderDetailsByOrderUseCase.execute(orderId);
        for (OrderDetail orderDetailItem : orderDetails) {

            Optional<Product> foundProdcut = findProductByIdUseCase.execute(orderDetailItem.getProductId());
            String productName = foundProdcut.get().getName();

            orderDetailOptions.addItem(orderDetailItem.getId() + ". " + productName + " (" + orderDetailItem.getQuantity() + ")");
        }
    }

    private void updateOrderDetailBox(){
        OrderService orderService = new OrderRepository();
        FindOrderByIdUseCase findOrderByIdUseCase = new FindOrderByIdUseCase(orderService);

        OrderDetailService orderDetailService = new OrderDetailRepository();
        FindOrderDetailsByOrderIdUseCase findOrderDetailsByOrderUseCase = new FindOrderDetailsByOrderIdUseCase(orderDetailService);

        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        
        orderDetailOptions.removeAllItems();

        this.orderId = Integer.parseInt(textBeforeDot(orderBox.getSelectedItem().toString()));

        Optional<Order> orderFound = findOrderByIdUseCase.execute(orderId);
        if (orderFound.isPresent()) {
            List<OrderDetail> orderDetails = findOrderDetailsByOrderUseCase.execute(orderId);
            for (OrderDetail orderDetailItem : orderDetails) {

                Optional<Product> foundProdcut = findProductByIdUseCase.execute(orderDetailItem.getProductId());
                String productName = foundProdcut.get().getName();

                orderDetailOptions.addItem(orderDetailItem.getId() + ". " + productName + " (" + orderDetailItem.getQuantity() + ")");
            }
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
