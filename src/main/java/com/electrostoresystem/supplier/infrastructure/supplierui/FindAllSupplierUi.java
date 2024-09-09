package com.electrostoresystem.supplier.infrastructure.supplierui;

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

import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;

public class FindAllSupplierUi {
    private final FindAllSupplierUseCase findAllSupplierUseCase;
    private final SupplierUiController supplierUiController;
    private JFrame frame;

    public FindAllSupplierUi(FindAllSupplierUseCase findAllSupplierUseCase, SupplierUiController supplierUiController) {
        this.findAllSupplierUseCase = findAllSupplierUseCase;
        this.supplierUiController = supplierUiController;
    }

    public void showAllSuppliers() {
        frame = new JFrame("All Suppliers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All Suppliers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable supplierTable = createSupplierTable();
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            supplierUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createSupplierTable() {
        String[] columnNames = {"id", "name", "email", "city_id", "address_details"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Supplier> suppliers = findAllSupplierUseCase.execute();
        if (!suppliers.isEmpty()) {
            for (Supplier supplier : suppliers) {
                Object[] rowData = {
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getEmail(),
                    supplier.getCityId(),
                    supplier.getAddressDetails()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No suppliers found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}
