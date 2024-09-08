package com.electrostoresystem.category.infrastructure.categoryui;

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

import com.electrostoresystem.category.application.UpdateCategoryUseCase;
import com.electrostoresystem.category.application.FindAllCategoryUseCase;
import com.electrostoresystem.category.application.FindCategoryByIdUseCase;  // Added import for FindCategoryByIdUseCase
import com.electrostoresystem.category.application.FindCategoryByNameUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;

public class UpdateCategoryUi extends JFrame {
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final CategoryUiController categoryUiController;

   private JComboBox<String> categoryOptions;
   private JTextField categoryNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int categoryFindId;

    public UpdateCategoryUi(UpdateCategoryUseCase updateCategoryUseCase, CategoryUiController categoryUiController) {
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.categoryUiController = categoryUiController;
    }

    public void frmUpdateCategory() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        CategoryService categoryService = new CategoryRepository();
        FindAllCategoryUseCase findAllCategoryUseCase = new FindAllCategoryUseCase(categoryService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Category");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update Category");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        categoryOptions = new JComboBox<>();
        List<Category> categorys = findAllCategoryUseCase.execute();
        for (Category category : categorys) {
            categoryOptions.addItem(category.getName());
        }
        categoryNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateCategory());
        jButton3.addActionListener(e -> {
            dispose();
            categoryUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findCategory());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Category:"), 1, 0);
        addComponent(categoryOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Category Name:"), 3, 0);
        addComponent(categoryNameField, 3, 1);

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

    private void updateCategory() {
        try {
            Category category = new Category();
            category.setId(categoryFindId);
            category.setName(categoryNameField.getText());

            updateCategoryUseCase.execute(category);
            JOptionPane.showMessageDialog(this, "Category updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findCategory() {
        CategoryService categoryService = new CategoryRepository();
        FindCategoryByNameUseCase findCategoryByNameUseCase = new FindCategoryByNameUseCase(categoryService);
        FindCategoryByIdUseCase findCategoryByIdUseCase = new FindCategoryByIdUseCase(categoryService);

        String categoryCode = (String) categoryOptions.getSelectedItem();
        Optional<Category> categoryFind = findCategoryByNameUseCase.execute(categoryCode);
        categoryFindId = categoryFind.get().getId();

        Optional<Category> categoryToUpdate = findCategoryByIdUseCase.execute(categoryFindId);
    
        if (categoryToUpdate.isPresent()) {
            Category foundCategory = categoryToUpdate.get();
            categoryNameField.setText(foundCategory.getName());
            categoryOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "Category not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        categoryNameField.setText("");
        categoryOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        categoryNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        categoryNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        categoryOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        CategoryService categoryService = new CategoryRepository();
        FindAllCategoryUseCase findAllCategoryUseCase = new FindAllCategoryUseCase(categoryService);
        
        List<Category> categorys = findAllCategoryUseCase.execute();
        for (Category category : categorys) {
            categoryOptions.addItem(category.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
