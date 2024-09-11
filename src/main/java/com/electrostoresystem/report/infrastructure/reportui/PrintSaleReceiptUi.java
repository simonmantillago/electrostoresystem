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
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.saledetail.application.FindSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

public class PrintSaleReceiptUi extends JFrame {
    float totalSale;

    public void showReceiptSale(Sale saleData) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Receipts");
        setSize(500, 500);

        initComponents(saleData);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(Sale saleData) {
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase  findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByIdUseCase  findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);
        SaleDetailService saleDetailService = new SaleDetailRepository();
        FindSaleDetailsBySaleIdUseCase findSaleDetailsBySaleUseCase = new FindSaleDetailsBySaleIdUseCase(saleDetailService);
        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(new SaleRepository());
        
        Optional<Sale> foundSale = findSaleByIdUseCase.execute(saleData.getId());
        Sale newFoundSale = foundSale.get();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Sale Ticket Number: " + saleData.getId() );
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel dateLbl = new JLabel("Date: " + saleData.getDate());
        addComponent(dateLbl, 1, 1);

        JLabel clientIdLbl = new JLabel("Client Id: " + saleData.getClientId());
        addComponent(clientIdLbl, 2, 1);
 
        JLabel totalLbl = new JLabel("Total: " + newFoundSale.getTotal());
        addComponent(totalLbl, 3, 1);

        Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(saleData.getPayMethod());
        JLabel payMethodLbl = new JLabel("Payment Method: " + foundPaymentMethods.get().getName());
        addComponent(payMethodLbl, 4, 1);

        JLabel discountedLbl = new JLabel("Discount Amount: " + newFoundSale.getDiscountAmount());
        addComponent(discountedLbl, 5, 1);

        JLabel discountLbl = new JLabel("Discount Percent: " + saleData.getDiscountPercent());
        addComponent(discountLbl, 6, 1);

        Optional<SaleStatus> foundSaleStatus = findSaleStatusByIdUseCase.execute(saleData.getStatusId());
        JLabel statusLbl = new JLabel("Status: " + foundSaleStatus.get().getName());
        addComponent(statusLbl, 7, 1);
        
        int row = 8; 
        int labelCounter = 1;  
        
        List<SaleDetail> saleDetails = findSaleDetailsBySaleUseCase.execute(saleData.getId());
        for (SaleDetail saleDetailItem : saleDetails) {
            Optional<Product> foundProduct = findProductByIdUseCase.execute(saleDetailItem.getProductId());
            String productName = foundProduct.get().getName();
            
            JLabel productLbl = new JLabel("Product: " + productName + " (" + saleDetailItem.getQuantity() + ") / " + "Unit price: " + saleDetailItem.getUnitPrice());
            
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

