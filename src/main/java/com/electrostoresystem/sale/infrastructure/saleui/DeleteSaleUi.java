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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.sale.application.DeleteSaleUseCase;
import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;
import com.electrostoresystem.saledetail.application.DeleteSaleDetailsBySaleIdUseCase;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;
import com.electrostoresystem.client.application.FindClientByIdUseCase;
import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;
import com.electrostoresystem.client.infrastructure.ClientRepository;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;


public class DeleteSaleUi extends JFrame {
    private final DeleteSaleUseCase deleteSaleUseCase;
    private final SaleUiController saleUiController;
    private JComboBox<String> saleOptions; 
    private JTextArea resultArea;
    
    public DeleteSaleUi(DeleteSaleUseCase deleteSaleUseCase, SaleUiController saleUiController) {
        this.deleteSaleUseCase = deleteSaleUseCase;
        this.saleUiController = saleUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Sale");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        ClientService clientService = new ClientRepository();
        FindClientByIdUseCase  findClientByIdUseCase = new FindClientByIdUseCase(clientService);


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Sale");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Sale:");
        addComponent(lblId, 1, 0);

        saleOptions = new JComboBox<>();
        List<Sale> sales = findAllSaleUseCase.execute();
        for (Sale sale : sales) {

            Optional<Client> foundClient =  findClientByIdUseCase.execute(sale.getClientId());
            String clientName = foundClient.get().getName();

            saleOptions.addItem(sale.getId() + ". " + clientName + " " + sale.getDate());
        }
        addComponent(saleOptions, 1, 1);

        JLabel lblReminder = new JLabel("Reminder: This will delete all the sale details relate to this sale");
        addComponent(lblReminder, 2, 0, 2);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSale());
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
            saleUiController.showCrudOptions();
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

    private void deleteSale() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByIdUseCase  findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);
        
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase  findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);

        SaleDetailService saleDetailService = new SaleDetailRepository();
        DeleteSaleDetailsBySaleIdUseCase  deleteSaleDetailsBySaleIdUseCase = new DeleteSaleDetailsBySaleIdUseCase(saleDetailService);

        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(new SaleRepository());

        int saleCode = (Integer.parseInt(textBeforeDot(saleOptions.getSelectedItem().toString())));
        Optional<Sale> salefound = findSaleByIdUseCase.execute(saleCode);
        float totalFound = salefound.get().getTotal();
        deleteSaleDetailsBySaleIdUseCase.execute(saleCode);
        Sale deletedSale = deleteSaleUseCase.execute(saleCode);

        Optional<SaleStatus> foundSaleStatus = findSaleStatusByIdUseCase.execute(deletedSale.getStatusId());
        Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(deletedSale.getPayMethod());

        reloadComboBoxOptions();

        String message = String.format(
            "Sale deleted successfully:\n\n" +
            "Id: %s\n" +
            "Sale Date: %s\n"+
            "Client Id: %s \n"+
            "Total: %.2f \n"+
            "Payment Method: %s \n"+
            "Discounted Amount: %.2f \n"+
            "Discounted Percent: %.2f \n"+
            "Status: %s\n",
            deletedSale.getId(),
            deletedSale.getDate(),
            deletedSale.getClientId(),
            totalFound,
            foundPaymentMethods.get().getName(),
            deletedSale.getDiscountAmount(),
            deletedSale.getDiscountPercent(),
            foundSaleStatus.get().getName()
        );
        resultArea.setText(message);
    }
    private void reloadComboBoxOptions() {
        saleOptions.removeAllItems();
    
        ClientService clientService = new ClientRepository();
        FindClientByIdUseCase  findClientByIdUseCase = new FindClientByIdUseCase(clientService);
        SaleService saleService = new SaleRepository();
        FindAllSaleUseCase findAllSaleUseCase = new FindAllSaleUseCase(saleService);
        
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
