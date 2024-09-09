package com.electrostoresystem.product.infrastructure.productui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.electrostoresystem.product.application.FindAllProductUseCase;
import com.electrostoresystem.product.domain.entity.Product;

public class FindAllProductUi {
    private final FindAllProductUseCase findAllProductUseCase;
    private final ProductUiController productUiController;
    private JFrame frame;

    public FindAllProductUi(FindAllProductUseCase findAllProductUseCase, ProductUiController productUiController) {
        this.findAllProductUseCase = findAllProductUseCase;
        this.productUiController = productUiController;
    }

    public void showAllProducts() {
        frame = new JFrame("All Products");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable productTable = createProductTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            productUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createProductTable() {
        String[] columnNames = {"id", "name", "description", "sale_price", "stock", "min_stock", "category_id", "brand_id"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Product> products = findAllProductUseCase.execute();
        if (!products.isEmpty()) {
            for (Product product : products) {
                Object[] rowData = {
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getSalePrice(),
                    product.getStock(),
                    product.getMinStock(),
                    product.getCategoryId(),
                    product.getBrandId()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No products found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}
