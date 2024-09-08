package com.electrostoresystem.salestatus.infrastructure.salestatusui;

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

import com.electrostoresystem.salestatus.application.UpdateSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindAllSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;  // Added import for FindSaleStatusByIdUseCase
import com.electrostoresystem.salestatus.application.FindSaleStatusByNameUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

public class UpdateSaleStatusUi extends JFrame {
    private final UpdateSaleStatusUseCase updateSaleStatusUseCase;
    private final SaleStatusUiController saleStatusUiController;

   private JComboBox<String> saleStatusOptions;
   private JTextField saleStatusNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int saleStatusFindId;

    public UpdateSaleStatusUi(UpdateSaleStatusUseCase updateSaleStatusUseCase, SaleStatusUiController saleStatusUiController) {
        this.updateSaleStatusUseCase = updateSaleStatusUseCase;
        this.saleStatusUiController = saleStatusUiController;
    }

    public void frmUpdateSaleStatus() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindAllSaleStatusUseCase findAllSaleStatusUseCase = new FindAllSaleStatusUseCase(saleStatusService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update SaleStatus");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update SaleStatus");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        saleStatusOptions = new JComboBox<>();
        List<SaleStatus> saleStatuss = findAllSaleStatusUseCase.execute();
        for (SaleStatus saleStatus : saleStatuss) {
            saleStatusOptions.addItem(saleStatus.getName());
        }
        saleStatusNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateSaleStatus());
        jButton3.addActionListener(e -> {
            dispose();
            saleStatusUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findSaleStatus());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("SaleStatus:"), 1, 0);
        addComponent(saleStatusOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("SaleStatus Name:"), 3, 0);
        addComponent(saleStatusNameField, 3, 1);

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

    private void updateSaleStatus() {
        try {
            SaleStatus saleStatus = new SaleStatus();
            saleStatus.setId(saleStatusFindId);
            saleStatus.setName(saleStatusNameField.getText());

            updateSaleStatusUseCase.execute(saleStatus);
            JOptionPane.showMessageDialog(this, "SaleStatus updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findSaleStatus() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByNameUseCase findSaleStatusByNameUseCase = new FindSaleStatusByNameUseCase(saleStatusService);
        FindSaleStatusByIdUseCase findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);

        String saleStatusCode = (String) saleStatusOptions.getSelectedItem();
        Optional<SaleStatus> saleStatusFind = findSaleStatusByNameUseCase.execute(saleStatusCode);
        saleStatusFindId = saleStatusFind.get().getId();

        Optional<SaleStatus> saleStatusToUpdate = findSaleStatusByIdUseCase.execute(saleStatusFindId);
    
        if (saleStatusToUpdate.isPresent()) {
            SaleStatus foundSaleStatus = saleStatusToUpdate.get();
            saleStatusNameField.setText(foundSaleStatus.getName());
            saleStatusOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "SaleStatus not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        saleStatusNameField.setText("");
        saleStatusOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        saleStatusNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        saleStatusNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        saleStatusOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindAllSaleStatusUseCase findAllSaleStatusUseCase = new FindAllSaleStatusUseCase(saleStatusService);
        
        List<SaleStatus> saleStatuss = findAllSaleStatusUseCase.execute();
        for (SaleStatus saleStatus : saleStatuss) {
            saleStatusOptions.addItem(saleStatus.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
