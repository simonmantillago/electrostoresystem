package com.electrostoresystem.category.infrastructure.categoryui;

import java.util.Optional;


import java.util.List;
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

import com.electrostoresystem.category.application.FindAllCategoryUseCase;
import com.electrostoresystem.category.application.FindCategoryByIdUseCase;
import com.electrostoresystem.category.application.FindCategoryByNameUseCase;
import com.electrostoresystem.category.domain.entity.Category;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;

public class FindCategoryByIdUi extends JFrame {
    private final FindCategoryByIdUseCase findCategoryByIdUseCase;
    private final CategoryUiController categoryUiController;
    private JComboBox<String> categoryOptions; 
    private JTextArea resultArea;



    public FindCategoryByIdUi(FindCategoryByIdUseCase findCategoryByIdUseCase, CategoryUiController categoryUiController) {
        this.findCategoryByIdUseCase = findCategoryByIdUseCase;
        this.categoryUiController = categoryUiController;
    }

    public void showFindCategory() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Category");
        setSize(500, 500);

        initComponents();
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

        JLabel titleLabel = new JLabel("Find Category");
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

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findCategory());
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


    private void findCategory() {
        CategoryService categoryService = new CategoryRepository();
        FindCategoryByNameUseCase findCategoryByNameUseCase = new FindCategoryByNameUseCase(categoryService);

        String categoryCode = (String) categoryOptions.getSelectedItem();
        Optional<Category> categoryFind = findCategoryByNameUseCase.execute(categoryCode);
        int categoryFindId = categoryFind.get().getId();


        Optional<Category> categoryOpt = findCategoryByIdUseCase.execute(categoryFindId);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            String message = String.format(
                "Category found:\n\n" +
                "Id: %d\n" +
                "Category Name: %s\n",
                category.getId(),
                category.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Category not found!");
        }
    }
}
