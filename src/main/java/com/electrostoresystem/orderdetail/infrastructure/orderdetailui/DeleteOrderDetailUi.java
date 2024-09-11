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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.orderdetail.application.DeleteOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.DeleteOrderDetailsByOrderIdUseCase;
import com.electrostoresystem.orderdetail.application.FindOrderDetailsByOrderIdUseCase;
import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;
import com.electrostoresystem.orderdetail.infrastructure.OrderDetailRepository;

import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;
import com.electrostoresystem.saledetail.application.DeleteSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;
import com.electrostoresystem.order.application.FindAllOrderUseCase;
import com.electrostoresystem.order.application.FindOrderByIdUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;
import com.electrostoresystem.order.infrastructure.OrderRepository;


public class DeleteOrderDetailUi extends JFrame {
    private final DeleteOrderDetailUseCase deleteOrderDetailUseCase;
    private final OrderDetailUiController orderDetailUiController;
    private JComboBox<String> orderBox,orderDetailOptions; 
    private JTextArea resultArea;
    private int orderId;
    
    public DeleteOrderDetailUi(DeleteOrderDetailUseCase deleteOrderDetailUseCase, OrderDetailUiController orderDetailUiController) {
        this.deleteOrderDetailUseCase = deleteOrderDetailUseCase;
        this.orderDetailUiController = orderDetailUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete OrderDetail");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {



        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete OrderDetail");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Order:");
        addComponent(lblId, 1, 0);

        orderBox = new JComboBox<>();
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase  findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders){
            orderBox.addItem(order.getId() + ". " + order.getSupplierId() + " " + order.getDate());
        }
        addComponent(orderBox, 1, 1);

        orderBox.addActionListener(e -> updateOrderDetailBox());
        
        JLabel lblOrderDetail = new JLabel("Product:");
        addComponent(lblOrderDetail, 2, 0);

        
        orderDetailOptions = new JComboBox<>();
        
        addComponent(orderDetailOptions, 2, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteOrderDetail());
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
            orderDetailUiController.showCrudOptions();
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

    private void deleteOrderDetail() {
        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);

    

        int orderDetailCode = (Integer.parseInt(textBeforeDot(orderDetailOptions.getSelectedItem().toString())));
        OrderDetail deletedOrderDetail = deleteOrderDetailUseCase.execute(orderDetailCode);


        reloadComboBoxOptions();

        if (deletedOrderDetail != null) {
            Optional<Product> foundProdcut = findProductByIdUseCase.execute(deletedOrderDetail.getProductId());
            String productName = foundProdcut.get().getName();
            String message = String.format(
                "OrderDetail deleted successfully:\n\n" +
                "Id: %d\n" +
                "Order Id: %s\n" +
                "Product: %s\n"+
                "Quantity: %s \n"+
                "Price: %s\n"+
                "SubTotal: %s \n",
                deletedOrderDetail.getId(),
                deletedOrderDetail.getOrderId(),
                productName,
                deletedOrderDetail.getQuantity(),
                deletedOrderDetail.getUnitPrice(),
                deletedOrderDetail.getSubTotal()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("OrderDetail not found or deletion failed.");
        }
    }
    private void reloadComboBoxOptions() {
        orderBox.removeAllItems();
        orderDetailOptions.removeAllItems();
        OrderService orderService = new OrderRepository();
        FindAllOrderUseCase  findAllOrderUseCase = new FindAllOrderUseCase(orderService);
        List<Order> orders = findAllOrderUseCase.execute();
        for (Order order : orders){
            orderBox.addItem(order.getId() + ". " + order.getSupplierId() + " " + order.getDate());
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
    
        if (orderBox.getSelectedItem() != null) {
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