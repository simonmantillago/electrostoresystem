package com.electrostoresystem.saledetail.infrastructure.saledetailui;

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

import com.electrostoresystem.saledetail.application.UpdateSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailByIdUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;

import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;

import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;

public class UpdateSaleDetailUi extends JFrame {
    private final UpdateSaleDetailUseCase updateSaleDetailUseCase;
    private final SaleDetailUiController saleDetailUiController;

    private JComboBox<String> saleBox,saleDetailOptions;

    private JTextField quantityTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int saleId;


    public UpdateSaleDetailUi(UpdateSaleDetailUseCase updateSaleDetailUseCase, SaleDetailUiController saleDetailUiController) {
        this.updateSaleDetailUseCase = updateSaleDetailUseCase;
        this.saleDetailUiController = saleDetailUiController;
    }

    public void frmUpdateSaleDetail() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update SaleDetail");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update SaleDetail");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);


        saleBox = new JComboBox<>();
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
        }

        saleBox.addActionListener(e -> updateSaleDetailBox());
        

        saleDetailOptions = new JComboBox<>();

        quantityTxt = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateSaleDetail());
        jButton3.addActionListener(e -> {
            dispose();
            saleDetailUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findSaleDetail());


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Sale:"), 1, 0);
        addComponent(saleBox, 1, 1);
        addComponent(new JLabel("Product:"), 2, 0);
        addComponent(saleDetailOptions, 2, 1);
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

    private void updateSaleDetail() {
        try {
            SaleDetailService saleDetailService = new SaleDetailRepository();
            FindSaleDetailByIdUseCase findSaleDetailByIdUseCase = new FindSaleDetailByIdUseCase(saleDetailService);
    
            int saleDetailCode = (Integer.parseInt(textBeforeDot(saleDetailOptions.getSelectedItem().toString())));
            Optional<SaleDetail> saleDetailFind = findSaleDetailByIdUseCase.execute(saleDetailCode);

            SaleDetail foundSaleDetail = saleDetailFind.get();

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setId(saleDetailCode);
            saleDetail.setSaleId(foundSaleDetail.getSaleId());
            saleDetail.setProductId(foundSaleDetail.getProductId());
            saleDetail.setQuantity(Integer.parseInt(quantityTxt.getText()));
            saleDetail.setUnitPrice(foundSaleDetail.getUnitPrice());
            saleDetail.setUnitPrice(foundSaleDetail.getUnitPrice());
      

        

            updateSaleDetailUseCase.execute(saleDetail);
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findSaleDetail() {
        SaleDetailService saleDetailService = new SaleDetailRepository();
        FindSaleDetailByIdUseCase findSaleDetailByIdUseCase = new FindSaleDetailByIdUseCase(saleDetailService);

        int saleDetailCode = (Integer.parseInt(textBeforeDot(saleDetailOptions.getSelectedItem().toString())));
        Optional<SaleDetail> saleDetailFind = findSaleDetailByIdUseCase.execute(saleDetailCode);

        
        if (saleDetailFind.isPresent()) {
            SaleDetail foundSaleDetail = saleDetailFind.get();
            
            quantityTxt.setText(String.valueOf(foundSaleDetail.getQuantity()));
            saleDetailOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "SaleDetail not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        saleDetailOptions.setEditable(false);
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
        saleDetailOptions.removeAllItems(); 
    
        SaleDetailService saleDetailService = new SaleDetailRepository();
        FindSaleDetailsBySaleIdUseCase findSaleDetailsBySaleUseCase = new FindSaleDetailsBySaleIdUseCase(saleDetailService);

        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        
        List<SaleDetail> saleDetails = findSaleDetailsBySaleUseCase.execute(saleId);
        for (SaleDetail saleDetailItem : saleDetails) {

            Optional<Product> foundProdcut = findProductByIdUseCase.execute(saleDetailItem.getProductId());
            String productName = foundProdcut.get().getName();

            saleDetailOptions.addItem(saleDetailItem.getId() + ". " + productName + " (" + saleDetailItem.getQuantity() + ")");
        }
    }

    private void updateSaleDetailBox(){
        SaleService saleService = new SaleRepository();
        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(saleService);

        SaleDetailService saleDetailService = new SaleDetailRepository();
        FindSaleDetailsBySaleIdUseCase findSaleDetailsBySaleUseCase = new FindSaleDetailsBySaleIdUseCase(saleDetailService);

        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        
        saleDetailOptions.removeAllItems();

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

    private String textBeforeDot(String text) {
        int position = text.indexOf('.');
        if (position != -1) {
            return text.substring(0, position);
        } else {
            return text;
        }
    }
}
