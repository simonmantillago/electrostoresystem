package com.electrostoresystem.supplierphone.infrastructure.supplierphoneui;

import java.util.List;
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
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import com.electrostoresystem.supplierphone.application.FindSupplierPhonesBySupplierIdUseCase;
import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;
import com.electrostoresystem.supplierphone.infrastructure.SupplierPhoneRepository;

public class FindSupplierPhonesBySupplierIdUi extends JFrame {
    private final FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase;
    private final SupplierPhoneUiController supplierPhoneUiController;
    private JTextField idTxtBox;
    private JComboBox<String> supplierPhoneOptions; 
    
    public FindSupplierPhonesBySupplierIdUi(FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase, SupplierPhoneUiController supplierPhoneUiController) {
        this.findSupplierPhonesBySupplierIdUseCase = findSupplierPhonesBySupplierIdUseCase;
        this.supplierPhoneUiController = supplierPhoneUiController;
    }
    
    public void showFoundSupplierPhones() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Search Phones");
        setSize(500, 500);
        

        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Título
        JLabel titleLabel = new JLabel("Search Phones");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 1, 1);
    
        // Etiqueta de Supplier Id
        JLabel lblId = new JLabel("Supplier Id:");
        addComponent(lblId, 1, 0);
    
        // Campo de texto para Supplier Id
        idTxtBox = new JTextField();
        addComponent(idTxtBox, 1, 1);  // Corrige la adición de idTxtBox en vez de titleLabel
    
        // Botón para buscar los teléfonos
        JButton findPhonesBtn = new JButton("Find");
        findPhonesBtn.addActionListener(e -> findPhonesComboBox());
        addComponent(findPhonesBtn, 1, 2);  // Colocar el botón en la columna 2
    
        JLabel lblphones = new JLabel("Related Phones:");
        addComponent(lblphones, 2, 0);
        // ComboBox para mostrar los teléfonos asociados al Supplier Id
        supplierPhoneOptions = new JComboBox<>();
        addComponent(supplierPhoneOptions, 2, 1, 2);  // Abarcar dos columnas
        supplierPhoneOptions.setVisible(false);
    

    
        // Botón para cerrar la ventana
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            supplierPhoneUiController.showCrudOptions();
        });
        addComponent(btnClose, 4, 0, 3);
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


    private void findPhonesComboBox() {
        SupplierPhoneService supplierPhoneService = new SupplierPhoneRepository();
        FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase = new FindSupplierPhonesBySupplierIdUseCase(supplierPhoneService);

        supplierPhoneOptions.setVisible(true);
        String supplierIdText = idTxtBox.getText();
    
        if (supplierIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Supplier Id", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            String supplierId = supplierIdText.trim();
    
            // Buscar los teléfonos asociados al supplierId
            List<SupplierPhone> supplierPhones = findSupplierPhonesBySupplierIdUseCase.execute(supplierId);
    
            // Limpiar las opciones anteriores del JComboBox
            supplierPhoneOptions.removeAllItems();
    
            if (!supplierPhones.isEmpty()) {
              
                for (SupplierPhone supplierPhone : supplierPhones) {
                    supplierPhoneOptions.addItem(supplierPhone.getPhone());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No phones found for the specified Supplier Id.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while fetching supplier phones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}


