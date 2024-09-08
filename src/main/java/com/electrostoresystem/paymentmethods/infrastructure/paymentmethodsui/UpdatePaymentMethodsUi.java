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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import com.electrostoresystem.paymentmethods.application.UpdatePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindAllPaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;  // Added import for FindPaymentMethodsByIdUseCase
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByNameUseCase;
import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

public class UpdatePaymentMethodsUi extends JFrame {
    private final UpdatePaymentMethodsUseCase updatePaymentMethodsUseCase;
    private final PaymentMethodsUiController paymentMethodsUiController;

   private JComboBox<String> paymentMethodsOptions;
   private JTextField paymentMethodsNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int paymentMethodsFindId;

    public UpdatePaymentMethodsUi(UpdatePaymentMethodsUseCase updatePaymentMethodsUseCase, PaymentMethodsUiController paymentMethodsUiController) {
        this.updatePaymentMethodsUseCase = updatePaymentMethodsUseCase;
        this.paymentMethodsUiController = paymentMethodsUiController;
    }

    public void frmUpdatePaymentMethods() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(paymentMethodsService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update PaymentMethods");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update PaymentMethods");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        paymentMethodsOptions = new JComboBox<>();
        List<PaymentMethods> paymentMethodss = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods paymentMethods : paymentMethodss) {
            paymentMethodsOptions.addItem(paymentMethods.getName());
        }
        paymentMethodsNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updatePaymentMethods());
        jButton3.addActionListener(e -> {
            dispose();
            paymentMethodsUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findPaymentMethods());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("PaymentMethods:"), 1, 0);
        addComponent(paymentMethodsOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("PaymentMethods Name:"), 3, 0);
        addComponent(paymentMethodsNameField, 3, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

    private void updatePaymentMethods() {
        try {
            PaymentMethods paymentMethods = new PaymentMethods();
            paymentMethods.setId(paymentMethodsFindId);
            paymentMethods.setName(paymentMethodsNameField.getText());

            updatePaymentMethodsUseCase.execute(paymentMethods);
            JOptionPane.showMessageDialog(this, "PaymentMethods updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findPaymentMethods() {
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindPaymentMethodsByNameUseCase findPaymentMethodsByNameUseCase = new FindPaymentMethodsByNameUseCase(paymentMethodsService);
        FindPaymentMethodsByIdUseCase findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(paymentMethodsService);

        String paymentMethodsCode = (String) paymentMethodsOptions.getSelectedItem();
        Optional<PaymentMethods> paymentMethodsFind = findPaymentMethodsByNameUseCase.execute(paymentMethodsCode);
        paymentMethodsFindId = paymentMethodsFind.get().getId();

        Optional<PaymentMethods> paymentMethodsToUpdate = findPaymentMethodsByIdUseCase.execute(paymentMethodsFindId);
    
        if (paymentMethodsToUpdate.isPresent()) {
            PaymentMethods foundPaymentMethods = paymentMethodsToUpdate.get();
            paymentMethodsNameField.setText(foundPaymentMethods.getName());
            paymentMethodsOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "PaymentMethods not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        paymentMethodsNameField.setText("");
        paymentMethodsOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        paymentMethodsNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        paymentMethodsNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        paymentMethodsOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();
        FindAllPaymentMethodsUseCase findAllPaymentMethodsUseCase = new FindAllPaymentMethodsUseCase(paymentMethodsService);
        
        List<PaymentMethods> paymentMethodss = findAllPaymentMethodsUseCase.execute();
        for (PaymentMethods paymentMethods : paymentMethodss) {
            paymentMethodsOptions.addItem(paymentMethods.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
