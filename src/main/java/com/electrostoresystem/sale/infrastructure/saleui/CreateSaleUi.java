package com.electrostoresystem.sale.infrastructure.saleui;

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

import com.electrostoresystem.sale.application.CreateSaleUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;

import com.electrostoresystem.salestatus.application.FindAllSaleStatusUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

public class CreateSaleUi extends JFrame {
    private final CreateSaleUseCase createSaleUseCase;
    private final SaleUiController saleUiController; 

    private JComboBox<String> statusBox,payBox; 
    private JTextField clientIdTxt, discountPercentTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateSaleUi(CreateSaleUseCase createSaleUseCase, SaleUiController saleUiController) { 
        this.createSaleUseCase = createSaleUseCase;
        this.saleUiController = saleUiController; 
    }

    public void frmRegSale() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Sale");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create Sale");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        clientIdTxt = new JTextField();

        payBox = new JComboBox<>();
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase  findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(PaymentMethodsService);
        List<PaymentMethods> methods = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods PaymentMethods : methods){
            payBox.addItem(PaymentMethods.getId() + ". " + PaymentMethods.getName());
        }

        discountPercentTxt = new JTextField();
        discountPercentTxt.setText("0");

        statusBox = new JComboBox<>();
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindAllSaleStatusUseCase  findAllSaleStatusUseCase = new FindAllSaleStatusUseCase(saleStatusService);
        List<SaleStatus> statuses = findAllSaleStatusUseCase.execute();
        for (SaleStatus saleStatus : statuses){
            statusBox.addItem(saleStatus.getId() + ". " + saleStatus.getName());
        }
        
        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveSale());
        jButton3.addActionListener(e -> {
            dispose();
            saleUiController.showCrudOptions(); // Adjusted to call the method in SaleUiController
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
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

    private void saveSale() {
        try {
            Sale sale = new Sale();
            sale.setClientId(clientIdTxt.getText());
            sale.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
            sale.setDiscountPercent(Float.parseFloat(discountPercentTxt.getText()));
            sale.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
    

            createSaleUseCase.execute(sale); 
           
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

    private void resetFields(){
        clientIdTxt.setText("");
        payBox.setSelectedIndex(0);
        discountPercentTxt.setText("0");
        statusBox.setSelectedIndex(0);
    }
}
