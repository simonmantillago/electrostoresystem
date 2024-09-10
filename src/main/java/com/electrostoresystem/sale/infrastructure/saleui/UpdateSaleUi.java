package com.electrostoresystem.sale.infrastructure.saleui;

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

import com.electrostoresystem.sale.application.UpdateSaleUseCase;
import com.electrostoresystem.client.application.FindClientByIdUseCase;
import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;
import com.electrostoresystem.client.infrastructure.ClientRepository;
import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase; 
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.salestatus.application.FindAllSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

public class UpdateSaleUi extends JFrame {
    private final UpdateSaleUseCase updateSaleUseCase;
    private final SaleUiController saleUiController;

   private JComboBox<String> saleOptions;
   private JComboBox<String> statusBox,payBox;
  
   private JTextField clientIdTxt, discountPercentTxt;
   private JTextField totalTxt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int saleFindId;
    private String dateFound;
    

    public UpdateSaleUi(UpdateSaleUseCase updateSaleUseCase, SaleUiController saleUiController) {
        this.updateSaleUseCase = updateSaleUseCase;
        this.saleUiController = saleUiController;
    }

    public void frmUpdateSale() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        ClientService clientService = new ClientRepository();
        FindClientByIdUseCase  findClientByIdUseCase = new FindClientByIdUseCase(clientService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Sale");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update Sale");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        saleOptions = new JComboBox<>();
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales) {

            Optional<Client> foundClient =  findClientByIdUseCase.execute(sale.getClientId());
            String clientName = foundClient.get().getName();

            saleOptions.addItem(sale.getId() + ". " + clientName + " " + sale.getDate());
        }

        clientIdTxt = new JTextField();

        totalTxt = new JTextField();

        payBox = new JComboBox<>();
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase  findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(PaymentMethodsService);
        List<PaymentMethods> methods = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods PaymentMethods : methods){
            payBox.addItem(PaymentMethods.getId() + ". " + PaymentMethods.getName());
        }

        discountPercentTxt = new JTextField();

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
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateSale());
        jButton3.addActionListener(e -> {
            dispose();
            saleUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findSale());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Sale:"), 1, 0);
        addComponent(saleOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Client Id:"), 3, 0);
        addComponent(clientIdTxt, 3, 1);
        addComponent(new JLabel("Total:"), 4, 0);
        addComponent(totalTxt, 4, 1);
        addComponent(new JLabel("Payment Methods:"), 5, 0);
        addComponent(payBox, 5, 1);
        addComponent(new JLabel("Discount:"), 6, 0);
        addComponent(discountPercentTxt, 6, 1);
        addComponent(new JLabel("Sale Status:"), 7, 0);
        addComponent(statusBox, 7, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 8;
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

    private void updateSale() {
        try {
            Sale sale = new Sale();
            sale.setId(saleFindId);
            sale.setDate(dateFound);
            sale.setClientId(clientIdTxt.getText());
            sale.setTotal(Float.parseFloat(totalTxt.getText()));
            sale.setPayMethod(Integer.parseInt(textBeforeDot(payBox.getSelectedItem().toString())));
            sale.setDiscountPercent(Float.parseFloat(discountPercentTxt.getText()));
            sale.setStatusId(Integer.parseInt(textBeforeDot(statusBox.getSelectedItem().toString())));
        

            updateSaleUseCase.execute(sale);
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findSale() {
        SaleService saleService = new SaleRepository();
        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(saleService);



        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByIdUseCase findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);

        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);

        int saleCode = (Integer.parseInt(textBeforeDot(saleOptions.getSelectedItem().toString())));
        Optional<Sale> saleFind = findSaleByIdUseCase.execute(saleCode);
        saleFindId = saleFind.get().getId();
        dateFound = saleFind.get().getDate();

        
        if (saleFind.isPresent()) {
            Sale foundSale = saleFind.get();
            
            Optional<SaleStatus> foundSaleStatus = findSaleStatusByIdUseCase.execute(foundSale.getStatusId());
            Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(foundSale.getPayMethod());
            
            clientIdTxt.setText(foundSale.getClientId());
            totalTxt.setText(String.valueOf(foundSale.getTotal()));
            payBox.setSelectedItem(foundSale.getPayMethod() + ". " + foundPaymentMethods.get().getName());
            discountPercentTxt.setText(String.valueOf(foundSale.getDiscountPercent()));
            statusBox.setSelectedItem(foundSale.getStatusId() + ". " + foundSaleStatus.get().getName());

            saleOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "Sale not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        saleOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        clientIdTxt.setVisible(false);
        totalTxt.setVisible(false);
        payBox.setVisible(false);
        discountPercentTxt.setVisible(false);
        statusBox.setVisible(false);
        payBox.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        clientIdTxt.setVisible(true);
        totalTxt.setVisible(true);
        payBox.setVisible(true);
        discountPercentTxt.setVisible(true);
        statusBox.setVisible(true);
        payBox.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        saleOptions.removeAllItems(); 
    
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        ClientService clientService = new ClientRepository();
        FindClientByIdUseCase  findClientByIdUseCase = new FindClientByIdUseCase(clientService);
        
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales) {

            Optional<Client> foundClient =  findClientByIdUseCase.execute(sale.getClientId());
            String clientName = foundClient.get().getName();

            saleOptions.addItem(sale.getId() + ". " + clientName + " " + sale.getDate());
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
