package com.electrostoresystem.saledetail.infrastructure.saledetailui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.List;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import com.electrostoresystem.saledetail.application.CreateSaleDetailUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;


import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;

import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;


public class CreateSaleDetailUi extends JFrame {
    private final CreateSaleDetailUseCase createSaleDetailUseCase;
    private final SaleDetailUiController saleDetailUiController; 

    private JComboBox<String> saleBox;
    private JTextField productIdTxt, quantityTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateSaleDetailUi(CreateSaleDetailUseCase createSaleDetailUseCase, SaleDetailUiController saleDetailUiController) { 
        this.createSaleDetailUseCase = createSaleDetailUseCase;
        this.saleDetailUiController = saleDetailUiController; 
    }

    public void frmRegSaleDetail() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create SaleDetail");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create SaleDetail");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        saleBox = new JComboBox<>();
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
        }

        productIdTxt = new JTextField();
        quantityTxt = new JTextField();
        

        
        jButton2 = new JButton("Add Product");
        jButton3 = new JButton("Go back");

        
        jButton2.addActionListener(e -> saveSaleDetail());
        jButton3.addActionListener(e -> {
            dispose();
            saleDetailUiController.showCrudOptions(); // Adjusted to call the method in SaleDetailUiController
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Sale:"), 1, 0);
        addComponent(saleBox, 1, 1);
        addComponent(new JLabel("Product:"), 2, 0);
        addComponent(productIdTxt, 2, 1);
        addComponent(new JLabel("Quantity:"), 3, 0);
        addComponent(quantityTxt, 3, 1);
    

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

    private void saveSaleDetail() {
        try {
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setSaleId(Integer.parseInt(textBeforeDot(saleBox.getSelectedItem().toString())));
            saleDetail.setProductId(Integer.parseInt(productIdTxt.getText()));
            saleDetail.setQuantity(Integer.parseInt(quantityTxt.getText()));

            createSaleDetailUseCase.execute(saleDetail); 
            JOptionPane.showMessageDialog(this, "Sale Detail added successfully!");
           
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
