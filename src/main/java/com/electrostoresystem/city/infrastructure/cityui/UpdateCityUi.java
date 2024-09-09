package com.electrostoresystem.city.infrastructure.cityui;

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

import com.electrostoresystem.city.application.UpdateCityUseCase;
import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;
import com.electrostoresystem.city.application.FindCityByIdUseCase;
import com.electrostoresystem.city.application.FindCitysByRegionUseCase;
import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;
import com.electrostoresystem.country.application.FindAllCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;

public class UpdateCityUi extends JFrame {
    private final UpdateCityUseCase updateCityUseCase;
    private final FindCityByIdUseCase findCityByIdUseCase;
    private final CityUiController cityUiController;

    private JComboBox<String> countryBox,cityBox, regionBox, updateRegionBox;
    private JTextField cityNameField;
    private JButton findButton, saveButton, resetButton, goBackButton;
    private int regionId, cityId,countryId;
    private String regionName, countryName;

    public UpdateCityUi(UpdateCityUseCase updateCityUseCase, FindCityByIdUseCase findCityByIdUseCase, CityUiController cityUiController) {
        this.updateCityUseCase = updateCityUseCase;
        this.findCityByIdUseCase = findCityByIdUseCase;
        this.cityUiController = cityUiController;
    }

    public void frmUpdateCity() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update City");
        setSize(500, 500);

        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Update City");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountriesUseCase = new FindAllCountryUseCase(countryService);

        countryBox = new JComboBox<>();
        List<Country> countries = findAllCountriesUseCase.execute();
        for (Country country : countries) {
            countryBox.addItem(country.getId() + ". " + country.getName());
        }
        countryBox.addActionListener(e -> updateRegionBox());

        regionBox = new JComboBox<>();
        regionBox.addActionListener(e -> updateCityBox());

        cityBox = new JComboBox<>();

        cityNameField = new JTextField();

        updateRegionBox = new JComboBox<>();


        
        
        findButton = new JButton("Find");
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset");
        goBackButton = new JButton("Go back");
        
        resetButton.addActionListener(e -> resetFields());
        saveButton.addActionListener(e -> updateCity());
        goBackButton.addActionListener(e -> {
            dispose();
            cityUiController.showCrudOptions();
        });
        findButton.addActionListener(e -> findCity());
        
        addComponent(new JLabel("Country:"), 1, 0, 1);
        addComponent(countryBox, 1, 1, 1);
        addComponent(new JLabel("Region:"), 2, 0, 1);
        addComponent(regionBox, 2, 1, 1);
        addComponent(new JLabel("Cities:"), 3, 0, 1);
        addComponent(cityBox, 3, 1,1);
        addComponent(findButton, 4, 0, 2);
        addComponent(new JLabel("Region:"), 5, 0, 1);
        addComponent(updateRegionBox, 5, 1, 1);
        addComponent(new JLabel("City Name:"), 6, 0, 1);
        addComponent(cityNameField, 6, 1, 1);

        
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

    private void updateCity() {
        try {
            City city = new City();
            city.setId(this.cityId);
            city.setName(cityNameField.getText());
            city.setRegionId(Integer.parseInt(textBeforeDot(updateRegionBox.getSelectedItem().toString())));
            updateCityUseCase.execute(city);
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findCity() {
        updateNewRegionBox();
        int cityId = Integer.parseInt(textBeforeDot(cityBox.getSelectedItem().toString()));
        Optional<City> cityToUpdate = findCityByIdUseCase.execute(cityId);

        if (cityToUpdate.isPresent()) {
            City foundCity = cityToUpdate.get();

            this.cityId = foundCity.getId();
            cityNameField.setText(foundCity.getName());
            updateRegionBox.setSelectedItem(foundCity.getRegionId() + ". " + regionName);
            regionBox.setEditable(false);
            cityBox.setEditable(false);
            showComponents();
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "City not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void updateRegionBox() {
        CountryService countryService = new CountryRepository();
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);

        RegionService regionService = new RegionRepository();
        FindRegionsByCountryUseCase findRegionsByCountryUseCase = new FindRegionsByCountryUseCase(regionService);
        
        regionBox.removeAllItems();
        try{
            this.countryId = Integer.parseInt(textBeforeDot(countryBox.getSelectedItem().toString()));
            
            Optional<Country> countryFound = findCountryByIdUseCase.execute(countryId);
            if (countryFound.isPresent()) {
                this.countryName = countryFound.get().getName();
                List<Region> regions = findRegionsByCountryUseCase.execute(countryId);
                for (Region regionItem : regions) {
                    regionBox.addItem(regionItem.getId() + ". " + regionItem.getName());
                }
            }
        }catch(Exception e){
          
        }
    }

    private void updateCityBox() {
        RegionService regionService = new RegionRepository();
        FindRegionByIdUseCase findRegionByIdUseCase = new FindRegionByIdUseCase(regionService);

        CityService cityService = new CityRepository();
        FindCitysByRegionUseCase findCitysByRegionUseCase = new FindCitysByRegionUseCase(cityService);
        
        cityBox.removeAllItems();
        try{
            this.regionId = Integer.parseInt(textBeforeDot(regionBox.getSelectedItem().toString()));
    
            Optional<Region> regionFound = findRegionByIdUseCase.execute(regionId);
            if (regionFound.isPresent()) {
                this.regionName = regionFound.get().getName();
                List<City> citys = findCitysByRegionUseCase.execute(regionId);
                for (City cityItem : citys) {
                    cityBox.addItem(cityItem.getId() + ". " + cityItem.getName());
                }
            }
        }catch(Exception e){    

        }
    }

    private void updateNewRegionBox() {
        updateRegionBox.removeAllItems(); 
        
        CountryService countryService = new CountryRepository();
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);
        
        Optional<Country> countryFound = findCountryByIdUseCase.execute(this.countryId);
        
        if (countryFound.isPresent()){
            RegionService chapterService = new RegionRepository();
            FindRegionsByCountryUseCase findRegionsByCountryUseCase = new FindRegionsByCountryUseCase(chapterService);
            
            List<Region> Regionstoupdate = findRegionsByCountryUseCase.execute(this.countryId);
            for(Region Regionitem : Regionstoupdate){
                updateRegionBox.addItem(Regionitem.getId()+". "+ Regionitem.getName());
            };
        }
    }

    private void resetFields() {
        cityNameField.setText("");
        cityBox.removeAllItems();
        regionBox.removeAllItems();
        hideComponents();
    }
    
    private void hideComponents() {
        cityNameField.setVisible(false);
        updateRegionBox.setVisible(false);
        saveButton.setVisible(false);
        resetButton.setVisible(false);
    }
    
    private void showComponents() {
        cityNameField.setVisible(true);
        updateRegionBox.setVisible(true);
        saveButton.setVisible(true);
        resetButton.setVisible(true);
    }
    
    private void reloadComboBoxOptions() {
        cityBox.removeAllItems();
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
