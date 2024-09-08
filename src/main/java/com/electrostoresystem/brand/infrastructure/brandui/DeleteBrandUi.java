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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.electrostoresystem.brand.application.DeleteBrandUseCase;
import com.electrostoresystem.brand.application.FindAllBrandUseCase;
import com.electrostoresystem.brand.application.FindBrandByNameUseCase;
import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;

public class DeleteBrandUi extends JFrame {
    private final DeleteBrandUseCase deleteBrandUseCase;
    private final BrandUiController BrandUiController;
    private JComboBox<String> BrandOptions; 
    private JTextArea resultArea;
    
    public DeleteBrandUi(DeleteBrandUseCase deleteBrandUseCase, BrandUiController BrandUiController) {
        this.deleteBrandUseCase = deleteBrandUseCase;
        this.BrandUiController = BrandUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Brand");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        BrandService BrandService = new BrandRepository();
        FindAllBrandUseCase findAllBrandUseCase = new FindAllBrandUseCase(BrandService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Brand");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Brand:");
        addComponent(lblId, 1, 0);

        BrandOptions = new JComboBox<>();
        List<Brand> Brands = findAllBrandUseCase.execute();
        for (Brand Brand : Brands) {
            BrandOptions.addItem(Brand.getName());
        }
        addComponent(BrandOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteBrand());
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
            BrandUiController.showCrudOptions();
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

    private void deleteBrand() {
        BrandService BrandService = new BrandRepository();
        FindBrandByNameUseCase findBrandByNameUseCase = new FindBrandByNameUseCase(BrandService);

        String BrandCode = (String) BrandOptions.getSelectedItem();
        Optional<Brand> BrandFind = findBrandByNameUseCase.execute(BrandCode);
        int BrandFindId = BrandFind.get().getId();
        
        Brand deletedBrand = deleteBrandUseCase.execute(BrandFindId);
        reloadComboBoxOptions();

        if (deletedBrand != null) {
            String message = String.format(
                "Brand deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedBrand.getId(),
                deletedBrand.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Brand not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        BrandOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        BrandService BrandService = new BrandRepository();
        FindAllBrandUseCase findAllBrandUseCase = new FindAllBrandUseCase(BrandService);
        
        List<Brand> countries = findAllBrandUseCase.execute();
        for (Brand Brand : countries) {
            BrandOptions.addItem(Brand.getName()); // Añade los Brands actualizados al JComboBox
        }
    }
    
}
