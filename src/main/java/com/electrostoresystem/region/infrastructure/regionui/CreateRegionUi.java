package com.electrostoresystem.region.infrastructure.regionui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.util.List;
import java.util.Optional;

import com.electrostoresystem.country.application.FindAllCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByNameUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.region.application.CreateRegionUseCase;
import com.electrostoresystem.region.domain.entity.Region;

public class CreateRegionUi extends JFrame {
    private final CreateRegionUseCase createRegionUseCase;
    private final RegionUiController regionUiController; 

    private JTextField jTextField1; 
    private JComboBox<String> countryOpt;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back

    public CreateRegionUi(CreateRegionUseCase createRegionUseCase, RegionUiController regionUiController) { 
        this.createRegionUseCase = createRegionUseCase;
        this.regionUiController = regionUiController; 
    }

    public void frmRegRegion() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountryUseCase = new FindAllCountryUseCase(countryService);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Region");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create Region");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField1 = new JTextField();
        countryOpt = new JComboBox<>();
        List<Country> countrys = findAllCountryUseCase.execute();
        for (Country country : countrys) {
            countryOpt.addItem(country.getName());
        }


        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveRegion());
        jButton3.addActionListener(e -> {
            dispose();
            regionUiController.showCrudOptions(); // Adjusted to call the method in RegionUiController
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Region name:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(new JLabel("Country:"), 2, 0);
        addComponent(countryOpt, 2, 1);
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 3;
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

    private void saveRegion() {
        CountryService countryService = new CountryRepository();
        FindCountryByNameUseCase findCountryByNameUseCase = new FindCountryByNameUseCase(countryService);

        String countryCode = (String) countryOpt.getSelectedItem();
        Optional<Country> countryFind = findCountryByNameUseCase.execute(countryCode);
        int countryFindId = countryFind.get().getId();

        try {
            
            Region region = new Region();
            region.setName(jTextField1.getText());
            region.setCountryId(countryFindId);
            
    

            createRegionUseCase.execute(region); // Corrected from "customer" to "region"
            JOptionPane.showMessageDialog(this, "Region added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField1.setText("");

    }
}
