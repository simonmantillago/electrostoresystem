package com.electrostoresystem.product.infrastructure.productui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import com.electrostoresystem.brand.application.FindBrandByIdUseCase;
import com.electrostoresystem.brand.domain.entity.Brand;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;
import com.electrostoresystem.category.application.FindCategoryByIdUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;
import com.electrostoresystem.product.application.FindAllProductUseCase;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.domain.entity.Product;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;


public class FindProductByIdUi extends JFrame {
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final ProductUiController productUiController;
    private JComboBox<String> productBox;
    private JTextArea resultArea;



    public FindProductByIdUi(FindProductByIdUseCase findProductByIdUseCase, ProductUiController productUiController) {
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.productUiController = productUiController;
    }

    public void showFindProduct() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Product");
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

        JLabel titleLabel = new JLabel("Find Product");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Product:");
        addComponent(lblId, 1, 0);

        productBox = new JComboBox<>();
        ProductService productService = new ProductRepository();
        FindAllProductUseCase  findAllProductUseCase = new FindAllProductUseCase(productService);
        List<Product> products = findAllProductUseCase.execute();
        for (Product product : products){
            productBox.addItem(product.getId() + ". " + product.getName());
        }
        addComponent(productBox, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findProduct());
        addComponent(btnFind, 2, 0, 2);

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
        btnClose.addActionListener(e -> {  dispose();
            productUiController.showCrudOptions();
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

    private void findProduct() {
        CategoryService categoryService = new CategoryRepository();
        FindCategoryByIdUseCase findCategoryByIdUseCase = new FindCategoryByIdUseCase(categoryService);

        BrandService brandService = new BrandRepository();
        FindBrandByIdUseCase findBrandByIdUseCase = new FindBrandByIdUseCase(brandService);
;

        int productId = (Integer.parseInt(textBeforeDot(productBox.getSelectedItem().toString())));
        Optional<Product> productOpt = findProductByIdUseCase.execute(productId);

        Optional<Category> foundCategory = findCategoryByIdUseCase.execute(productOpt.get().getCategoryId());
        Optional<Brand> foundBrand = findBrandByIdUseCase.execute(productOpt.get().getBrandId());

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            String message = String.format(
                "Product found successfully:\n\n" +
                "Id: %s\n" +
                "Name: %s\n" +
                "Description: %s\n" +
                "Sale Price: %.2f\n"+
                "Stock: %d\n"+
                "Minimum Stock: %d\n"+
                "Category: %s\n" +
                "Brand: %s\n",
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSalePrice(),
                product.getStock(),
                product.getMinStock(),
                foundCategory.get().getName(),
                foundBrand.get().getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Product not found!");
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
}
