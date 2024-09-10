package com.electrostoresystem.sale.infrastructure.saleui;

import java.util.Optional;


import java.util.List;
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

import com.electrostoresystem.sale.application.FindAllSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;

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


public class FindSaleByIdUi extends JFrame {
    private final FindSaleByIdUseCase findSaleByIdUseCase;
    private final SaleUiController saleUiController;
    private JComboBox<String> saleOptions; 
    private JTextArea resultArea;



    public FindSaleByIdUi(FindSaleByIdUseCase findSaleByIdUseCase, SaleUiController saleUiController) {
        this.findSaleByIdUseCase = findSaleByIdUseCase;
        this.saleUiController = saleUiController;
    }

    public void showFindSale() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Sale");
        setSize(500, 500);

        initComponents();
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

        JLabel titleLabel = new JLabel("Find Sale");
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

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findSale());
        addComponent(btnDelete, 2, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            saleUiController.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 2);
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


    private void findSale() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByIdUseCase  findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);
        
        PaymentMethodsService PaymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByIdUseCase  findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(PaymentMethodsService);

        int saleCode = (Integer.parseInt(textBeforeDot(saleOptions.getSelectedItem().toString())));
        Optional<Sale> saleOpt = findSaleByIdUseCase.execute(saleCode);

        Optional<SaleStatus> foundSaleStatus = findSaleStatusByIdUseCase.execute(saleOpt.get().getStatusId());
        Optional<PaymentMethods> foundPaymentMethods = findPaymentMethodsByIdUseCase.execute(saleOpt.get().getPayMethod());

        if (saleOpt.isPresent()) {
            Sale sale = saleOpt.get();
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
                sale.getId(),
                sale.getDate(),
                sale.getClientId(),
                sale.getTotal(),
                foundPaymentMethods.get().getName(),
                sale.getDiscountAmount(),
                sale.getDiscountPercent(),
                foundSaleStatus.get().getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Sale not found!");
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
