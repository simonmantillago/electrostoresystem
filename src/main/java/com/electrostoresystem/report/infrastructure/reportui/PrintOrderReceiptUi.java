package com.electrostoresystem.report.infrastructure.reportui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;
import com.electrostoresystem.order.application.FindOrderByIdUseCase;
import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.infrastructure.OrderRepository;
import com.electrostoresystem.orderdetail.application.FindOrderDetailsByOrderIdUseCase;
import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;
import com.electrostoresystem.orderdetail.infrastructure.OrderDetailRepository;
import com.electrostoresystem.orderstatus.application.FindOrderStatusByIdUseCase;
import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;

public class PrintOrderReceiptUi extends JFrame {
    float totalOrder;

    public void showReceiptOrder(Order orderData) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Receipts");
        setSize(500, 500);

        initComponents(orderData);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(Order orderData) {
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase  findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);
        OrderStatusService orderStatusService = new OrderStatusRepository();
        FindOrderStatusByIdUseCase  findOrderStatusByIdUseCase = new FindOrderStatusByIdUseCase(orderStatusService);
        OrderDetailService orderDetailService = new OrderDetailRepository();
        FindOrderDetailsByOrderIdUseCase findOrderDetailsByOrderUseCase = new FindOrderDetailsByOrderIdUseCase(orderDetailService);
        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        FindOrderByIdUseCase findOrderByIdUseCase = new FindOrderByIdUseCase(new OrderRepository());
        
        Optional<Order> foundOrder = findOrderByIdUseCase.execute(orderData.getId());
        Order newFoundOrder = foundOrder.get();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Order Ticket Number: " + orderData.getId() );
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel dateLbl = new JLabel("Date: " + orderData.getDate());
        addComponent(dateLbl, 1, 1);

        JLabel clientIdLbl = new JLabel("Supplier Id: " + orderData.getSupplierId());
        addComponent(clientIdLbl, 2, 1);
 
        JLabel totalLbl = new JLabel("Total: " + newFoundOrder.getTotal());
        addComponent(totalLbl, 3, 1);

        Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(orderData.getPayMethod());
        JLabel payMethodLbl = new JLabel("Payment Method: " + foundPaymentMethods.get().getName());
        addComponent(payMethodLbl, 4, 1);

        Optional<OrderStatus> foundOrderStatus = findOrderStatusByIdUseCase.execute(orderData.getStatusId());
        JLabel statusLbl = new JLabel("Status: " + foundOrderStatus.get().getName());
        addComponent(statusLbl, 5, 1);
        
        int row = 6; 
        int labelCounter = 1;  
        
        List<OrderDetail> orderDetails = findOrderDetailsByOrderUseCase.execute(orderData.getId());
        for (OrderDetail orderDetailItem : orderDetails) {
            Optional<Product> foundProduct = findProductByIdUseCase.execute(orderDetailItem.getProductId());
            String productName = foundProduct.get().getName();
            
            JLabel productLbl = new JLabel("Product: " + productName + " (" + orderDetailItem.getQuantity() + ") / " + "Unit price: " + orderDetailItem.getUnitPrice());
            
            productLbl.setName("productLbl" + labelCounter++);  
            
            addComponent(productLbl, row++, 1);  
        }
        



        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
        });
        addComponent(btnClose, 100, 0, 2);
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
}

