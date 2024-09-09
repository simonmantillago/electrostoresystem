package com.electrostoresystem.supplier.infrastructure.supplierui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.electrostoresystem.city.application.FindCityByIdUseCase;
import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;


public class FindSupplierByIdUi extends JFrame {
    private final FindSupplierByIdUseCase findSupplierByIdUseCase;
    private final SupplierUiController supplierUiController;
    private JTextField jTextField1;
    private JTextArea resultArea;



    public FindSupplierByIdUi(FindSupplierByIdUseCase findSupplierByIdUseCase, SupplierUiController supplierUiController) {
        this.findSupplierByIdUseCase = findSupplierByIdUseCase;
        this.supplierUiController = supplierUiController;
    }

    public void showFindSupplier() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Supplier");
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

        JLabel titleLabel = new JLabel("Find Supplier");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Supplier to find:");
        addComponent(lblId, 1, 0);

        jTextField1 = new JTextField();
        addComponent(jTextField1, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findSupplier());
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
            supplierUiController.showCrudOptions();
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

    private void findSupplier() {
        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);

        String supplierId = jTextField1.getText();
        Optional<Supplier> supplierOpt = findSupplierByIdUseCase.execute(supplierId);

        Optional<City> foundCity = findCityByIdUseCase.execute(supplierOpt.get().getCityId());

        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            String message = String.format(
                "Supplier found successfully:\n\n" +
                "Id: %s\n" +
                "Name: %s\n" +
                "Email: %s\n"+
                "City: %s\n"+
                "Address: %s\n",
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                foundCity.get().getName(),
                supplier.getAddressDetails()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Supplier not found!");
        }
    }
}
