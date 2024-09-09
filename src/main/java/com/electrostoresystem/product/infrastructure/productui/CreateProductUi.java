package com.electrostoresystem.product.infrastructure.productui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.electrostoresystem.brand.application.FindAllBrandUseCase;
import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;


import com.electrostoresystem.category.application.FindAllCategoryUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;
import com.electrostoresystem.product.application.CreateProductUseCase;
import com.electrostoresystem.product.domain.entity.Product;

public class CreateProductUi  extends JFrame{
    private final CreateProductUseCase createProductUseCase;
    private final ProductUiController productUiController;

    private JTextField nameTxt, descriptionTxt,salePriceTxt, minStockTxt;
    private JComboBox<String> brandBox, categoryBox;
    private int regionId,countryId;
    private String regionName, countryName;


    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    
    public CreateProductUi(CreateProductUseCase createProductUseCase, ProductUiController productUiController) {
        this.createProductUseCase = createProductUseCase;
        this.productUiController = productUiController;
    } 

    public void frmRegProduct() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Product");
        setSize(500, 500);
        
        JLabel jLabel1 = new JLabel("Create Product");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        

        
        nameTxt = new JTextField();
        descriptionTxt = new JTextField();
        salePriceTxt = new JTextField();
        minStockTxt = new JTextField();

        categoryBox = new JComboBox<>();
        CategoryService categoryService = new CategoryRepository();
        FindAllCategoryUseCase  findAllCategoryUseCase = new FindAllCategoryUseCase(categoryService);
        List<Category> categories = findAllCategoryUseCase.execute();
        for (Category category : categories){
            categoryBox.addItem(category.getId() + ". " + category.getName());
        }

        brandBox = new JComboBox<>();
        BrandService brandService = new BrandRepository();
        FindAllBrandUseCase  findAllBrandUseCase = new FindAllBrandUseCase(brandService);
        List<Brand> brands = findAllBrandUseCase.execute();
        for (Brand brand : brands){
            brandBox.addItem(brand.getId() + ". " + brand.getName());
        }





        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveProduct());
        jButton3.addActionListener(e -> {
            dispose();
            productUiController.showCrudOptions();
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Name:"), 1, 0);
        addComponent(nameTxt, 1, 1);
        addComponent(new JLabel("Description:"), 2, 0);
        addComponent(descriptionTxt, 2, 1);
        addComponent(new JLabel("Sale Price:"), 3, 0);
        addComponent(salePriceTxt, 3, 1);
        addComponent(new JLabel("Minimum Stock:"), 4, 0);
        addComponent(minStockTxt, 4, 1);
        addComponent(new JLabel("Category:"), 5, 0);
        addComponent(categoryBox, 5, 1);
        addComponent(new JLabel("Brand:"), 6, 0);
        addComponent(brandBox, 6, 1);

    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        setLocationRelativeTo(null);
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
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        add(component, gbc);
    }

    private void saveProduct() {
        try {
            Product product = new Product();
            product.setName(nameTxt.getText());
            product.setDescription(descriptionTxt.getText());
            product.setSalePrice(Float.parseFloat(salePriceTxt.getText()));
            product.setMinStock(Integer.parseInt(minStockTxt.getText()));
            product.setCategoryId(Integer.parseInt(textBeforeDot(categoryBox.getSelectedItem().toString())));
            product.setBrandId(Integer.parseInt(textBeforeDot(brandBox.getSelectedItem().toString())));

            createProductUseCase.execute(product); 
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    
   
    private void resetFields() {
        nameTxt.setText("");
        descriptionTxt.setText("");
        salePriceTxt.setText("");
        minStockTxt.setText("");
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
