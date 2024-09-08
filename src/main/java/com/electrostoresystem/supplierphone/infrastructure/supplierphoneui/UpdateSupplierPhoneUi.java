package com.electrostoresystem.supplierphone.infrastructure.supplierphoneui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.electrostoresystem.supplierphone.application.FindAllSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhoneByPhoneUseCase;
import com.electrostoresystem.supplierphone.application.UpdateSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;
import com.electrostoresystem.supplierphone.infrastructure.SupplierPhoneRepository;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class UpdateSupplierPhoneUi extends JFrame {
    private final UpdateSupplierPhoneUseCase updateSupplierPhoneUseCase;
    private final FindSupplierPhoneByPhoneUseCase findSupplierPhoneByPhoneUseCase;
    private final SupplierPhoneUiController supplierPhoneUiController;

    
    
    private JTextField jTextField1; // SupplierPhone Name
    private JTextField jTextField2; // SupplierPhone Name
    private JTextField jTextField3; // SupplierPhone Name
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private String foundPhone;

    public UpdateSupplierPhoneUi(UpdateSupplierPhoneUseCase updateSupplierPhoneUseCase, FindSupplierPhoneByPhoneUseCase findSupplierPhoneByPhoneUseCase, SupplierPhoneUiController supplierPhoneUiController) {
        this.updateSupplierPhoneUseCase = updateSupplierPhoneUseCase;
        this.findSupplierPhoneByPhoneUseCase = findSupplierPhoneByPhoneUseCase;
        this.supplierPhoneUiController = supplierPhoneUiController;
    }

    public void frmUpdateSupplierPhone() {
        SupplierPhoneService supplierPhoneService = new SupplierPhoneRepository();
        FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase = new FindAllSupplierPhoneUseCase(supplierPhoneService);
        initComponents(findAllSupplierPhoneUseCase);
        setVisible(true);
    }

    private void initComponents(FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Supplier Phone");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update SupplierPhone");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        
 

        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateSupplierPhone());
        jButton3.addActionListener(e -> {
            dispose();
            supplierPhoneUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findSupplierPhone());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Phone Number:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Supplier Id:"), 3, 0);
        addComponent(jTextField2, 3, 1);
        addComponent(new JLabel("Phone Number:"), 4, 0);
        addComponent(jTextField3, 4, 1);
        

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 5, 0, 2);

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
        gbc.anchor = GridBagConstraints.CENTER; 

        add(component, gbc);  
    }

    private void updateSupplierPhone() {
        try {
            SupplierPhone supplierPhone = new SupplierPhone();
            supplierPhone.setSupplierId(jTextField2.getText()); 
            supplierPhone.setPhone(jTextField3.getText());  
        
            updateSupplierPhoneUseCase.execute(supplierPhone,foundPhone);  
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();  
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findSupplierPhone() {
        String phoneToUpdate = jTextField1.getText();  // Obtener el número de teléfono a buscar
        Optional<SupplierPhone> supplierPhoneToUpdate = findSupplierPhoneByPhoneUseCase.execute(phoneToUpdate);
    
        if (supplierPhoneToUpdate.isPresent()) {
            SupplierPhone foundSupplierPhone = supplierPhoneToUpdate.get();
            foundPhone = phoneToUpdate;  
            jTextField2.setText(foundSupplierPhone.getSupplierId());  
            jTextField3.setText(foundSupplierPhone.getPhone());  
            showComponents(); 
            revalidate();  
            repaint();  
        } else {
            JOptionPane.showMessageDialog(this, "SupplierPhone not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void resetFields() {
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        hideComponents();
    }

    private void hideComponents() {
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jTextField1.setEditable(true);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        jTextField1.setEditable(false);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }



    
}

