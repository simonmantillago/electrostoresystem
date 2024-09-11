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

import java.awt.Dimension;

import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.report.infrastructure.reportui.PrintSaleReceiptUi;
import com.electrostoresystem.sale.application.CreateSaleUseCase;
import com.electrostoresystem.sale.application.FindLastSaleUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.saledetail.application.CreateSaleDetailUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;
import com.electrostoresystem.salestatus.application.FindAllSaleStatusUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

import javax.swing.JComboBox;

public class RegSaleUiController extends JFrame {
    private JComboBox<String> statusBox, payBox;
    private JTextField clientIdTxt, discountPercentTxt;

    private JButton jButton2; 
    private JButton jButton3; 
    private JButton addProductBtn;
    private JPanel productPanel; 
    private Sale lastSale;

    public RegSaleUiController() {
    }

    public void frmRegSale() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sale Register");
        setSize(500, 550);
    
        JLabel jLabel1 = new JLabel("Sale Register");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    
        clientIdTxt = new JTextField();
    
        payBox = new JComboBox<>();
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(paymentMethodsService);
        List<PaymentMethods> methods = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods paymentMethod : methods) {
            payBox.addItem(paymentMethod.getId() + ". " + paymentMethod.getName());
        }
    
        discountPercentTxt = new JTextField();
        discountPercentTxt.setText("0");
    
        statusBox = new JComboBox<>();
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindAllSaleStatusUseCase findAllSaleStatusUseCase = new FindAllSaleStatusUseCase(saleStatusService);
        List<SaleStatus> statuses = findAllSaleStatusUseCase.execute();
        for (SaleStatus saleStatus : statuses) {
            statusBox.addItem(saleStatus.getId() + ". " + saleStatus.getName());
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
        productPanel.add(new JLabel(" "), gbc);
        
    
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 200));
    
        jButton2 = new JButton("Pay");
        jButton3 = new JButton("Go back");
    
        jButton2.addActionListener(e -> regFullSale());
    
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
        addComponent(new JLabel("Client Id:"), 1, 0);
        addComponent(clientIdTxt, 1, 1);
        addComponent(new JLabel("Payment Method:"), 2, 0);
        addComponent(payBox, 2, 1);
        addComponent(new JLabel("Discount Percent:"), 3, 0);
        addComponent(discountPercentTxt, 3, 1);
        addComponent(new JLabel("Status:"), 4, 0);
        addComponent(statusBox, 4, 1);
        addComponent(new JLabel("Add product:"), 5, 0);
        addComponent(addProductBtn, 5, 1);
        addComponent(scrollPane, 6, 0, 2);
        addComponent(buttonPanel, 7, 0, 2);
    
        setLocationRelativeTo(null);
    }

    private void addProductRow() {
   
        JTextField productIdField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton removeBtn = new JButton("-");
        removeBtn.addActionListener( e -> removeProductRow(productIdField, quantityField, removeBtn));
     

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        productPanel.add(productIdField, gbc);

        gbc.gridx = 1;
        productPanel.add(quantityField, gbc);

        gbc.gridx = 2;
    
        productPanel.add(removeBtn, gbc);

        productPanel.revalidate();
        productPanel.repaint();
    }

    private void removeProductRow(JTextField productIdField, JTextField quantityField, JButton removeBtn) {
        productPanel.remove(productIdField);
        productPanel.remove(quantityField);
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

    private void regFullSale() {
        CreateSaleUseCase createSaleUseCase = new CreateSaleUseCase(new SaleRepository());
        FindLastSaleUseCase findLastSaleUseCase = new FindLastSaleUseCase(new SaleRepository());
        CreateSaleDetailUseCase createSaleDetailUseCase = new CreateSaleDetailUseCase(new SaleDetailRepository());
    
        try {
            Sale sale = new Sale();
            sale.setClientId(clientIdTxt.getText());
            sale.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
            sale.setDiscountPercent(Float.parseFloat(discountPercentTxt.getText()));
            sale.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
    
            createSaleUseCase.execute(sale);
            lastSale = findLastSaleUseCase.execute();
            int lastSaleId = lastSale.getId();
    
            Component[] components = productPanel.getComponents();

            for (int i = 0; i < components.length; i += 3) {
                if (components[i] instanceof JTextField && components[i + 1] instanceof JTextField) {
                    JTextField productIdField = (JTextField) components[i];
                    JTextField quantityField = (JTextField) components[i + 1];

                    SaleDetail saleDetail = new SaleDetail();
                    saleDetail.setSaleId(lastSaleId);
                    saleDetail.setProductId(Integer.parseInt(productIdField.getText()));
                    saleDetail.setQuantity(Integer.parseInt(quantityField.getText()));

                    createSaleDetailUseCase.execute(saleDetail);
                }
            }

            printReceipt(lastSale);

    
            
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

    private static void printReceipt(Sale sale) {
        PrintSaleReceiptUi printSaleReceiptUi = new PrintSaleReceiptUi();
        printSaleReceiptUi.showReceiptSale(sale);
    }

    private void resetFields() {
        clientIdTxt.setText("");
        payBox.setSelectedIndex(0);
        discountPercentTxt.setText("0");
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
