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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.electrostoresystem.category.application.DeleteCategoryUseCase;
import com.electrostoresystem.category.application.FindAllCategoryUseCase;
import com.electrostoresystem.category.application.FindCategoryByNameUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;

public class DeleteCategoryUi extends JFrame {
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final CategoryUiController categoryUiController;
    private JComboBox<String> categoryOptions; 
    private JTextArea resultArea;
    
    public DeleteCategoryUi(DeleteCategoryUseCase deleteCategoryUseCase, CategoryUiController categoryUiController) {
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.categoryUiController = categoryUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Category");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        CategoryService categoryService = new CategoryRepository();
        FindAllCategoryUseCase findAllCategoryUseCase = new FindAllCategoryUseCase(categoryService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete Category");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Category:");
        addComponent(lblId, 1, 0);

        categoryOptions = new JComboBox<>();
        List<Category> categorys = findAllCategoryUseCase.execute();
        for (Category category : categorys) {
            categoryOptions.addItem(category.getName());
        }
        addComponent(categoryOptions, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteCategory());
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
            categoryUiController.showCrudOptions();
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

    private void deleteCategory() {
        CategoryService categoryService = new CategoryRepository();
        FindCategoryByNameUseCase findCategoryByNameUseCase = new FindCategoryByNameUseCase(categoryService);

        String categoryCode = (String) categoryOptions.getSelectedItem();
        Optional<Category> categoryFind = findCategoryByNameUseCase.execute(categoryCode);
        int categoryFindId = categoryFind.get().getId();
        
        Category deletedCategory = deleteCategoryUseCase.execute(categoryFindId);
        reloadComboBoxOptions();

        if (deletedCategory != null) {
            String message = String.format(
                "Category deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedCategory.getId(),
                deletedCategory.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Category not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        categoryOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        CategoryService categoryService = new CategoryRepository();
        FindAllCategoryUseCase findAllCategoryUseCase = new FindAllCategoryUseCase(categoryService);
        
        List<Category> countries = findAllCategoryUseCase.execute();
        for (Category category : countries) {
            categoryOptions.addItem(category.getName()); // Añade los categorys actualizados al JComboBox
        }
    }
    
}
