package com.electrostoresystem.supplierphone.infrastructure.supplierphoneui;

import java.awt.BorderLayout;
import java.util.List;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.electrostoresystem.supplierphone.application.FindAllSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;

public class FindAllSupplierPhoneUi {
private final FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase;
    private final SupplierPhoneUiController supplierPhoneUiController;
    private JFrame frame;

    public FindAllSupplierPhoneUi(FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase, SupplierPhoneUiController supplierPhoneUiController) {
        this.findAllSupplierPhoneUseCase = findAllSupplierPhoneUseCase;
        this.supplierPhoneUiController = supplierPhoneUiController;
    }

    public void showAllSupplierPhones() {
        frame = new JFrame("All Phones Registered");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All SupplierPhones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable supplierPhoneTable = createSupplierPhoneTable();
        JScrollPane scrollPane = new JScrollPane(supplierPhoneTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            supplierPhoneUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createSupplierPhoneTable() {
        String[] columnNames = {"Phone Number","Supplier Id"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<SupplierPhone> supplierPhones = findAllSupplierPhoneUseCase.execute();
        if (!supplierPhones.isEmpty()) {
            for (SupplierPhone supplierPhone : supplierPhones) {
                Object[] rowData = {
                    supplierPhone.getSupplierId(),
                    supplierPhone.getPhone(),
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No Phones found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}