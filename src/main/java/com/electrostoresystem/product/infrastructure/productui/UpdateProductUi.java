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
import com.electrostoresystem.brand.application.FindBrandByIdUseCase;
import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;

import com.electrostoresystem.category.application.FindAllCategoryUseCase;
import com.electrostoresystem.category.application.FindCategoryByIdUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;

import com.electrostoresystem.product.application.FindAllProductUseCase;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.application.UpdateProductUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;

public class UpdateProductUi  extends JFrame{
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductUiController productUiController;

    private JTextField nameTxt, descriptionTxt,salePriceTxt, minStockTxt, stockTxt;
    private JComboBox<String> brandBox, categoryBox, productBox;
    private int productId;


    private JButton findButton, saveButton, resetButton, goBackButton;
    
    public UpdateProductUi(UpdateProductUseCase updateProductUseCase, ProductUiController productUiController) {
        this.updateProductUseCase = updateProductUseCase;
        this.productUiController = productUiController;
    } 

    public void frmUpdateProduct() {
        initComponents();
        reloadProductBox();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Product");
        setSize(500, 500);
        
        JLabel jLabel1 = new JLabel("Update Product");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        productBox = new JComboBox<>();


        nameTxt = new JTextField();
        descriptionTxt = new JTextField();
        salePriceTxt = new JTextField();
        stockTxt = new JTextField();
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

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        findButton = new JButton("Find");
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset");
        goBackButton = new JButton("Go back");

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Product:"), 1, 0);
        addComponent(productBox, 1, 1);
        addComponent(findButton, 2, 0,2);
        addComponent(new JLabel("Name:"), 3, 0);
        addComponent(nameTxt, 3, 1);
        addComponent(new JLabel("Description:"), 4 , 0);
        addComponent(descriptionTxt, 4, 1);
        addComponent(new JLabel("Sale Price:"), 5, 0);
        addComponent(salePriceTxt, 5, 1);
        addComponent(new JLabel("Stock:"), 6, 0);
        addComponent(stockTxt, 6, 1);
        addComponent(new JLabel("Minimun Stock:"), 7, 0);
        addComponent(minStockTxt, 7, 1);
        addComponent(new JLabel("Category:"), 8, 0);
        addComponent(categoryBox, 8, 1);
        addComponent(new JLabel("Brand:"), 9, 0);
        addComponent(brandBox, 9, 1);
        
        
        resetButton.addActionListener(e -> resetFields());
        saveButton.addActionListener(e -> updateProduct());
        goBackButton.addActionListener(e -> {
            dispose();
            productUiController.showCrudOptions();
        });
        findButton.addActionListener(e -> findProduct());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(goBackButton);
        addComponent(buttonPanel, 10, 0, 2);
        


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
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between components
        add(component, gbc);
    }

    private void updateProduct(){
        try{
            Product product = new Product();
            product.setId(this.productId);
            product.setName(nameTxt.getText());
            product.setDescription(descriptionTxt.getText());
            product.setSalePrice(Float.parseFloat(salePriceTxt.getText()));
            product.setStock(Integer.parseInt(stockTxt.getText()));
            product.setMinStock(Integer.parseInt(minStockTxt.getText()));
            product.setCategoryId(Integer.parseInt(textBeforeDot(categoryBox.getSelectedItem().toString())));
            product.setBrandId(Integer.parseInt(textBeforeDot(brandBox.getSelectedItem().toString())));

            updateProductUseCase.execute(product);
            reloadProductBox();
            resetFields();
        }catch(Exception e){

        }
    }

    private void findProduct() {
        ProductService productService = new ProductRepository();
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);

        CategoryService categoryService = new CategoryRepository();
        FindCategoryByIdUseCase findCategoryByIdUseCase = new FindCategoryByIdUseCase(categoryService);
        
        BrandService brandService = new BrandRepository();
        FindBrandByIdUseCase findBrandByIdUseCase = new FindBrandByIdUseCase(brandService);



        int productId = (Integer.parseInt(textBeforeDot(productBox.getSelectedItem().toString())));
        Optional<Product> productToUpdate = findProductByIdUseCase.execute(productId);
        
        if (productToUpdate.isPresent()) {
            Product foundProduct = productToUpdate.get();
            
            Optional<Category> foundCategory = findCategoryByIdUseCase.execute(foundProduct.getCategoryId());
            Optional<Brand> foundBrand = findBrandByIdUseCase.execute(foundProduct.getBrandId());
            
            if (foundCategory.isPresent() && foundBrand.isPresent()) {
                this.productId = foundProduct.getId();
                nameTxt.setText(foundProduct.getName());
                descriptionTxt.setText(foundProduct.getDescription());
                salePriceTxt.setText(String.valueOf(foundProduct.getSalePrice()));
                stockTxt.setText(String.valueOf(foundProduct.getStock()));
                minStockTxt.setText(String.valueOf(foundProduct.getMinStock()));
                categoryBox.setSelectedItem(foundProduct.getCategoryId() + ". " + foundCategory.get().getName());
                brandBox.setSelectedItem(foundProduct.getBrandId() + ". " + foundBrand.get().getName());

                
       
       
                productBox.setEditable(false);
              
                showComponents();
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Product data is incomplete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("No se encontró el producte con el ID proporcionado");
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

    private void resetFields() {
        nameTxt.setText("");
        descriptionTxt.setText("");
        salePriceTxt.setText("");
        stockTxt.setText("");
        minStockTxt.setText("");
        hideComponents();
    }

    
    
    private void hideComponents() {
        nameTxt.setVisible(false);
        descriptionTxt.setVisible(false);
        salePriceTxt.setVisible(false);
        stockTxt.setVisible(false);
        minStockTxt.setVisible(false);
        categoryBox.setVisible(false);
        brandBox.setVisible(false);
        saveButton.setVisible(false);
        resetButton.setVisible(false);
    }
    
    private void showComponents() {
        nameTxt.setVisible(true);
        descriptionTxt.setVisible(true);
        salePriceTxt.setVisible(true);
        stockTxt.setVisible(true);
        minStockTxt.setVisible(true);
        categoryBox.setVisible(true);
        brandBox.setVisible(true);
        saveButton.setVisible(true);
        resetButton.setVisible(true);
    }

    private void reloadProductBox(){
        productBox.removeAllItems();
        ProductService productService = new ProductRepository();
        FindAllProductUseCase  findAllProductUseCase = new FindAllProductUseCase(productService);
        List<Product> products = findAllProductUseCase.execute();
        for (Product product : products){
            productBox.addItem(product.getId() + ". " + product.getName());
        }
    }
}
