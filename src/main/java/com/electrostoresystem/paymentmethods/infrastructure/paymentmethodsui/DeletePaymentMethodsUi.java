package com.electrostoresystem.paymentmethods.infrastructure.paymentmethodsui;

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


import com.electrostoresystem.paymentmethods.application.DeletePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByNameUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

public class DeletePaymentMethodsUi extends JFrame {
    private final DeletePaymentMethodsUseCase deletePaymentMethodsUseCase;
    private final PaymentMethodsUiController paymentMethodsUiController;
    private JComboBox<String> paymentMethodsOptions; 
    private JTextArea resultArea;
    
    public DeletePaymentMethodsUi(DeletePaymentMethodsUseCase deletePaymentMethodsUseCase, PaymentMethodsUiController paymentMethodsUiController) {
        this.deletePaymentMethodsUseCase = deletePaymentMethodsUseCase;
        this.paymentMethodsUiController = paymentMethodsUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete PaymentMethods");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(paymentMethodsService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete PaymentMethods");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("PaymentMethods:");
        addComponent(lblId, 1, 0);

        paymentMethodsOptions = new JComboBox<>();
        List<PaymentMethods> paymentMethodss = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods paymentMethods : paymentMethodss) {
            paymentMethodsOptions.addItem(paymentMethods.getName());
        }
        addComponent(paymentMethodsOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deletePaymentMethods());
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
            paymentMethodsUiController.showCrudOptions();
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

    private void deletePaymentMethods() {
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByNameUseCase findPaymentMethodsByNameUseCase = new FindPaymentMethodsByNameUseCase(paymentMethodsService);

        String paymentMethodsCode = (String) paymentMethodsOptions.getSelectedItem();
        Optional<PaymentMethods> paymentMethodsFind = findPaymentMethodsByNameUseCase.execute(paymentMethodsCode);
        int paymentMethodsFindId = paymentMethodsFind.get().getId();
        
        PaymentMethods deletedPaymentMethods = deletePaymentMethodsUseCase.execute(paymentMethodsFindId);
        reloadComboBoxOptions();

        if (deletedPaymentMethods != null) {
            String message = String.format(
                "PaymentMethods deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedPaymentMethods.getId(),
                deletedPaymentMethods.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("PaymentMethods not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        paymentMethodsOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(paymentMethodsService);
        
        List<PaymentMethods> countries = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods paymentMethods : countries) {
            paymentMethodsOptions.addItem(paymentMethods.getName()); // Añade los paymentMethodss actualizados al JComboBox
        }
    }
    
}
