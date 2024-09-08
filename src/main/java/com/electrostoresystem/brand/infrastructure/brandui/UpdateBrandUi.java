package com.electrostoresystem.brand.infrastructure.brandui;

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

import com.electrostoresystem.brand.application.UpdateBrandUseCase;
import com.electrostoresystem.brand.application.FindAllBrandUseCase;
import com.electrostoresystem.brand.application.FindBrandByIdUseCase;  // Added import for FindBrandByIdUseCase
import com.electrostoresystem.brand.application.FindBrandByNameUseCase;
import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;

public class UpdateBrandUi extends JFrame {
    private final UpdateBrandUseCase updateBrandUseCase;
    private final BrandUiController BrandUiController;

   private JComboBox<String> BrandOptions;
   private JTextField BrandNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int BrandFindId;

    public UpdateBrandUi(UpdateBrandUseCase updateBrandUseCase, BrandUiController BrandUiController) {
        this.updateBrandUseCase = updateBrandUseCase;
        this.BrandUiController = BrandUiController;
    }

    public void frmUpdateBrand() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        BrandService BrandService = new BrandRepository();
        FindAllBrandUseCase findAllBrandUseCase = new FindAllBrandUseCase(BrandService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Brand");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update Brand");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        BrandOptions = new JComboBox<>();
        List<Brand> Brands = findAllBrandUseCase.execute();
        for (Brand Brand : Brands) {
            BrandOptions.addItem(Brand.getName());
        }
        BrandNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateBrand());
        jButton3.addActionListener(e -> {
            dispose();
            BrandUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findBrand());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Brand:"), 1, 0);
        addComponent(BrandOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Brand Name:"), 3, 0);
        addComponent(BrandNameField, 3, 1);

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

    private void updateBrand() {
        try {
            Brand Brand = new Brand();
            Brand.setId(BrandFindId);
            Brand.setName(BrandNameField.getText());

            updateBrandUseCase.execute(Brand);
            JOptionPane.showMessageDialog(this, "Brand updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findBrand() {
        BrandService BrandService = new BrandRepository();
        FindBrandByNameUseCase findBrandByNameUseCase = new FindBrandByNameUseCase(BrandService);
        FindBrandByIdUseCase findBrandByIdUseCase = new FindBrandByIdUseCase(BrandService);

        String BrandCode = (String) BrandOptions.getSelectedItem();
        Optional<Brand> BrandFind = findBrandByNameUseCase.execute(BrandCode);
        BrandFindId = BrandFind.get().getId();

        Optional<Brand> BrandToUpdate = findBrandByIdUseCase.execute(BrandFindId);
    
        if (BrandToUpdate.isPresent()) {
            Brand foundBrand = BrandToUpdate.get();
            BrandNameField.setText(foundBrand.getName());
            BrandOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "Brand not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        BrandNameField.setText("");
        BrandOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        BrandNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        BrandNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        BrandOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        BrandService BrandService = new BrandRepository();
        FindAllBrandUseCase findAllBrandUseCase = new FindAllBrandUseCase(BrandService);
        
        List<Brand> Brands = findAllBrandUseCase.execute();
        for (Brand Brand : Brands) {
            BrandOptions.addItem(Brand.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
