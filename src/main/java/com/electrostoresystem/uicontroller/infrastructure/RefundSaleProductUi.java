package com.electrostoresystem.uicontroller.infrastructure;

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

import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.infrastructure.ProductRepository;
import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.saledetail.application.DeleteSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
public class RefundSaleProductUi extends JFrame {

    private JComboBox<String> saleBox,saleDetailOptions; 
    private JTextArea resultArea;
    private int saleId;
    
    public RefundSaleProductUi() {

    }

    public void showRefundUi() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Refund");
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

        JLabel titleLabel = new JLabel("Refunds");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Sale:");
        addComponent(lblId, 1, 0);

        saleBox = new JComboBox<>();
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(new SaleRepository());
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
        }
        addComponent(saleBox, 1, 1);

        saleBox.addActionListener(e -> updateSaleDetailBox());
        
        JLabel lblSaleDetail = new JLabel("Product:");
        addComponent(lblSaleDetail, 2, 0);

        
        saleDetailOptions = new JComboBox<>();
        
        addComponent(saleDetailOptions, 2, 1);

        JButton btnDelete = new JButton("Refund Product");
        btnDelete.addActionListener(e -> deleteSaleDetail());
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
            MainUiController.createAndShowMainUI();
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

    private void deleteSaleDetail() {

        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(new ProductRepository());

        DeleteSaleDetailUseCase deleteSaleDetailUseCase = new DeleteSaleDetailUseCase(new SaleDetailRepository());

        int saleDetailCode = (Integer.parseInt(textBeforeDot(saleDetailOptions.getSelectedItem().toString())));
        SaleDetail deletedSaleDetail = deleteSaleDetailUseCase.execute(saleDetailCode);


        reloadComboBoxOptions();

        if (deletedSaleDetail != null) {
            Optional<Product> foundProdcut = findProductByIdUseCase.execute(deletedSaleDetail.getProductId());
            String productName = foundProdcut.get().getName();
            String message = String.format(
                "Refund successfully:\n\n" +
                "Sale Id: %s\n" +
                "Product: %s\n"+
                "Quantity: %s \n"+
                "Unit Price: %s\n"+
                "SubTotal refunded: %s \n",
                deletedSaleDetail.getSaleId(),
                productName,
                deletedSaleDetail.getQuantity(),
                deletedSaleDetail.getUnitPrice(),
                deletedSaleDetail.getSubTotal()
            );
            resultArea.setText(message);
        } 
    }
    private void reloadComboBoxOptions() {
        saleBox.removeAllItems();
        saleDetailOptions.removeAllItems();
   
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(new SaleRepository());
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
        }
    }

    private void updateSaleDetailBox(){
        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(new SaleRepository());
    
        FindSaleDetailsBySaleIdUseCase findSaleDetailsBySaleUseCase = new FindSaleDetailsBySaleIdUseCase(new SaleDetailRepository());
    
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(new ProductRepository());
        
        saleDetailOptions.removeAllItems();
    
        if (saleBox.getSelectedItem() != null) {
            this.saleId = Integer.parseInt(textBeforeDot(saleBox.getSelectedItem().toString()));
    
            Optional<Sale> saleFound = findSaleByIdUseCase.execute(saleId);
            if (saleFound.isPresent()) {
                List<SaleDetail> saleDetails = findSaleDetailsBySaleUseCase.execute(saleId);
                for (SaleDetail saleDetailItem : saleDetails) {
    
                    Optional<Product> foundProdcut = findProductByIdUseCase.execute(saleDetailItem.getProductId());
                    String productName = foundProdcut.get().getName();
    
                    saleDetailOptions.addItem(saleDetailItem.getId() + ". " + productName + " (" + saleDetailItem.getQuantity() + ")");
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