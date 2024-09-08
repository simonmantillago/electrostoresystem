package com.electrostoresystem.region.infrastructure.regionui;

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

import com.electrostoresystem.region.application.UpdateRegionUseCase;
import com.electrostoresystem.country.application.FindAllCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;

public class UpdateRegionUi extends JFrame {
    private final UpdateRegionUseCase updateRegionUseCase;
    private final FindRegionByIdUseCase findRegionByIdUseCase;
    private final RegionUiController regionUiController;

    private JComboBox<String> regionBox, countryBox, updateCountryBox;
    private JTextField regionNameField;
    private JButton findButton, saveButton, resetButton, goBackButton;
    private int countryId, regionId;
    private String countryName;

    public UpdateRegionUi(UpdateRegionUseCase updateRegionUseCase, FindRegionByIdUseCase findRegionByIdUseCase, RegionUiController regionUiController) {
        this.updateRegionUseCase = updateRegionUseCase;
        this.findRegionByIdUseCase = findRegionByIdUseCase;
        this.regionUiController = regionUiController;
    }

    public void frmUpdateRegion() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Region");
        setSize(500, 500);

        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Update Region");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        countryBox = new JComboBox<>();
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountriesUseCase = new FindAllCountryUseCase(countryService);
        List<Country> countries = findAllCountriesUseCase.execute();
        for (Country country : countries) {
            countryBox.addItem(country.getId() + ". " + country.getName());
        }
        
        updateCountryBox = new JComboBox<>();
        for (Country country : countries) {
            updateCountryBox.addItem(country.getId() + ". " + country.getName());
        }

        regionBox = new JComboBox<>();
        
        countryBox.addActionListener(e -> updateRegionBox());
        
        regionNameField = new JTextField();
        
        findButton = new JButton("Find");
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset");
        goBackButton = new JButton("Go back");
        
        resetButton.addActionListener(e -> resetFields());
        saveButton.addActionListener(e -> updateRegion());
        goBackButton.addActionListener(e -> {
            dispose();
            regionUiController.showCrudOptions();
        });
        findButton.addActionListener(e -> findRegion());
        
        addComponent(titleLabel, 0, 0, 2);
        addComponent(new JLabel("Country:"), 1, 0, 1);
        addComponent(countryBox, 1, 1, 1);
        addComponent(new JLabel("Region:"), 2, 0, 1);
        addComponent(regionBox, 2, 1, 1);
        addComponent(findButton, 3, 0, 2);
        addComponent(new JLabel("Country:"), 4, 0, 1);
        addComponent(updateCountryBox, 4, 1, 1);
        addComponent(new JLabel("Region Name:"), 5, 0, 1);
        addComponent(regionNameField, 5, 1, 1);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(goBackButton);
        addComponent(buttonPanel, 7, 0, 2);

        setLocationRelativeTo(null);

        hideComponents();
    }

    private void addComponent(Component component, int row, int col, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        add(component, gbc);
    }

    private void updateRegion() {
        try {
            Region region = new Region();
            region.setId(this.regionId);
            region.setName(regionNameField.getText());
            region.setCountryId(Integer.parseInt(textBeforeDot(updateCountryBox.getSelectedItem().toString())));
            updateRegionUseCase.execute(region);
            JOptionPane.showMessageDialog(this, "Region updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findRegion() {
        int regionCode = Integer.parseInt(textBeforeDot(regionBox.getSelectedItem().toString()));
        Optional<Region> regionToUpdate = findRegionByIdUseCase.execute(regionCode);

        if (regionToUpdate.isPresent()) {
            Region foundRegion = regionToUpdate.get();

            this.regionId = foundRegion.getId();
            regionNameField.setText(foundRegion.getName());
            updateCountryBox.setSelectedItem(foundRegion.getCountryId() + ". " + countryName);
            countryBox.setEditable(false);
            regionBox.setEditable(false);
            showComponents();
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Region not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void resetFields() {
        regionNameField.setText("");
        regionBox.removeAllItems();
        hideComponents();
    }
    
    private void hideComponents() {
        regionNameField.setVisible(false);
        updateCountryBox.setVisible(false);
        saveButton.setVisible(false);
        resetButton.setVisible(false);
    }
    
    private void showComponents() {
        regionNameField.setVisible(true);
        updateCountryBox.setVisible(true);
        saveButton.setVisible(true);
        resetButton.setVisible(true);
    }
    
    private void reloadComboBoxOptions() {
        regionBox.removeAllItems();
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
