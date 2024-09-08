package com.electrostoresystem.city.infrastructure.cityui;

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
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;
import com.electrostoresystem.city.application.CreateCityUseCase;
import com.electrostoresystem.city.domain.entity.City;

public class CreateCityUi extends JFrame {
    private final CreateCityUseCase createCityUseCase;
    private final CityUiController cityUiController; 

    private JTextField jTextField1; 
    private JComboBox<String> regionBox, countryBox;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private int countryId, regionId;
    private String countryName;

    public CreateCityUi(CreateCityUseCase createCityUseCase, CityUiController cityUiController) { 
        this.createCityUseCase = createCityUseCase;
        this.cityUiController = cityUiController; 
    }

    public void frmRegCity() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create City");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Create City");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jTextField1 = new JTextField();

        countryBox = new JComboBox<>();

        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountriesUseCase = new FindAllCountryUseCase(countryService);
        List<Country> countries = findAllCountriesUseCase.execute();
        for (Country country : countries) {
            countryBox.addItem(country.getId() + ". " + country.getName());
        }
        countryBox.addActionListener(e -> updateRegionBox());

        regionBox = new JComboBox<>();
        
        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveCity());
        jButton3.addActionListener(e -> {
            dispose();
            cityUiController.showCrudOptions(); 
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Country:"), 1, 0);
        addComponent(countryBox, 1, 1);
        addComponent(new JLabel("Region:"), 2, 0);
        addComponent(regionBox, 2, 1);
        addComponent(new JLabel("City name:"), 3, 0);
        addComponent(jTextField1, 3, 1);
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

    private void saveCity() {
        this.regionId = Integer.parseInt(textBeforeDot(regionBox.getSelectedItem().toString()));

        try {
            City city = new City();
            city.setName(jTextField1.getText().trim());
            city.setRegionId(regionId);

            createCityUseCase.execute(city); 
            JOptionPane.showMessageDialog(this, "City added successfully!");
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField1.setText("");

    }

    private void updateRegionBox() {
        CountryService countryService = new CountryRepository();
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);

        RegionService regionService = new RegionRepository();
        FindRegionsByCountryUseCase findRegionsByCountryUseCase = new FindRegionsByCountryUseCase(regionService);
        
        regionBox.removeAllItems();

        this.countryId = Integer.parseInt(textBeforeDot(countryBox.getSelectedItem().toString()));

        Optional<Country> countryFound = findCountryByIdUseCase.execute(countryId);
        if (countryFound.isPresent()) {
            this.countryName = countryFound.get().getName();
            List<Region> regions = findRegionsByCountryUseCase.execute(countryId);
            for (Region regionItem : regions) {
                regionBox.addItem(regionItem.getId() + ". " + regionItem.getName());
            }
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
