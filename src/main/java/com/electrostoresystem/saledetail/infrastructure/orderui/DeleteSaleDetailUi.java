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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.saledetail.application.DeleteSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindAllSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailByIdUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;
import com.electrostoresystem.paymentmethods.infrastructure.paymentmethodsui.FindPaymentMethodsByIdUi;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;
import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;


public class DeleteSaleDetailUi extends JFrame {
    private final DeleteSaleDetailUseCase deleteSaleDetailUseCase;
    private final SaleDetailUiController saleDetailUiController;
    private JComboBox<String> saleBox,saleDetailOptions; 
    private JTextArea resultArea;
    private int saleId, clientId;
    
    public DeleteSaleDetailUi(DeleteSaleDetailUseCase deleteSaleDetailUseCase, SaleDetailUiController saleDetailUiController) {
        this.deleteSaleDetailUseCase = deleteSaleDetailUseCase;
        this.saleDetailUiController = saleDetailUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete SaleDetail");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        SaleDetailService saleDetailService = new SaleDetailRepository();
        FindAllSaleDetailUseCase findAllSaleDetailUseCase = new FindAllSaleDetailUseCase(saleDetailService);


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete SaleDetail");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Sale:");
        addComponent(lblId, 1, 0);

        saleBox = new JComboBox<>();
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
        }
        addComponent(saleBox, 1, 1);

        saleBox.addActionListener(e -> updateSaleDetailBox());
        
        JLabel lblSaleDetail = new JLabel("Product:");
        addComponent(lblId, 2, 0);

        saleDetailOptions = new JComboBox<>();
        

        addComponent(saleDetailOptions, 2, 1);

        JButton btnDelete = new JButton("Delete");
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
            saleDetailUiController.showCrudOptions();
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
        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);

        int saleDetailCode = (Integer.parseInt(textBeforeDot(saleDetailOptions.getSelectedItem().toString())));
        SaleDetail deletedSaleDetail = deleteSaleDetailUseCase.execute(saleDetailCode);


        reloadComboBoxOptions();

        if (deletedSaleDetail != null) {
            Optional<Product> foundProdcut = findProductByIdUseCase.execute(deletedSaleDetail.getProductId());
            String productName = foundProdcut.get().getName();
            String message = String.format(
                "SaleDetail deleted successfully:\n\n" +
                "Id: %d\n" +
                "Sale Id: %s\n" +
                "Product: %s\n"+
                "Quantity: %s \n"+
                "Price: %s\n"+
                "SubTotal: %s \n",
                deletedSaleDetail.getId(),
                deletedSaleDetail.getSaleId(),
                productName,
                deletedSaleDetail.getQuantity(),
                deletedSaleDetail.getUnitPrice(),
                deletedSaleDetail.getSubTotal()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("SaleDetail not found or deletion failed.");
        }
    }
    private void reloadComboBoxOptions() {
        saleBox.removeAllItems();
        saleDetailOptions.removeAllItems();
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase  findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales){
            saleBox.addItem(sale.getId() + ". " + sale.getClientId() + " " + sale.getDate());
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
