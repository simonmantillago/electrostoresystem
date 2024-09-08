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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.supplierphone.application.DeleteSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhonesBySupplierIdUseCase;
import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;
import com.electrostoresystem.supplierphone.infrastructure.SupplierPhoneRepository;

public class DeleteSupplierPhoneUi extends JFrame {
    private final DeleteSupplierPhoneUseCase deleteSupplierPhoneUseCase;
    private final SupplierPhoneUiController supplierPhoneUiController;
    private JTextField idTxtBox;
    private JComboBox<String> supplierPhoneOptions; 
    private JTextArea resultArea;
    
    public DeleteSupplierPhoneUi(DeleteSupplierPhoneUseCase deleteSupplierPhoneUseCase, SupplierPhoneUiController supplierPhoneUiController) {
        this.deleteSupplierPhoneUseCase = deleteSupplierPhoneUseCase;
        this.supplierPhoneUiController = supplierPhoneUiController;
    }
    
    public void showDeleteSupplierPhone() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Phone");
        setSize(600, 600);
        

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
        JLabel titleLabel = new JLabel("Delete Phone");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 1);
    
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
    
        // ComboBox para mostrar los teléfonos asociados al Supplier Id
        supplierPhoneOptions = new JComboBox<>();
        addComponent(supplierPhoneOptions, 2, 1, 1);  // Abarcar dos columnas
    
        // Botón para eliminar el teléfono seleccionado
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSupplierPhone());
        addComponent(btnDelete, 3, 1, 1);
    
        // Área de resultados para mostrar mensajes de éxito o error
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
    
        // Botón para cerrar la ventana
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            supplierPhoneUiController.showCrudOptions();
        });
        addComponent(btnClose, 5, 1, 1);
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

    private void deleteSupplierPhone() {
        String supplierPhoneName = (String) supplierPhoneOptions.getSelectedItem();
        SupplierPhone deletedSupplierPhone = deleteSupplierPhoneUseCase.execute(supplierPhoneName);

        if (deletedSupplierPhone != null) {
            String message = String.format(
                "SupplierPhone deleted successfully:\n\n" +
                "Supplier Id: %s\n" +
                "Phone: %s\n",
                deletedSupplierPhone.getSupplierId(),
                deletedSupplierPhone.getPhone()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("SupplierPhone deletion failed. SupplierPhone not found.");
        }
    }

    private void findPhonesComboBox() {
        SupplierPhoneService supplierPhoneService = new SupplierPhoneRepository();
        FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase = new FindSupplierPhonesBySupplierIdUseCase(supplierPhoneService);

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

