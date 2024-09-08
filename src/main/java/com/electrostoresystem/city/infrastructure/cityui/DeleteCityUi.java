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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;
import com.electrostoresystem.city.application.DeleteCityUseCase;
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


public class DeleteCityUi extends JFrame {
    private final DeleteCityUseCase deleteCityUseCase;
    private final CityUiController cityUiController;
    private JComboBox<String> countryBox,regionBox,cityBox; 
    private JTextArea resultArea;
    private int regionId, countryId;
    private String regionName, countryName;
    
    public DeleteCityUi(DeleteCityUseCase deleteCityUseCase, CityUiController cityUiController) {
        this.deleteCityUseCase = deleteCityUseCase;
        this.cityUiController = cityUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete City");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Delete City");
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


        addComponent(new JLabel("Country:"), 1, 0, 1);
        addComponent(countryBox, 1, 1, 1);
        addComponent(new JLabel("Region:"), 2, 0, 1);
        addComponent(regionBox, 2, 1, 1);
        addComponent(new JLabel("Cities:"), 3, 0, 1);
        addComponent(cityBox, 3, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteCity());
        addComponent(btnDelete, 4, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            cityUiController.showCrudOptions();
        });
        addComponent(btnClose,6, 0, 2);
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

    private void deleteCity() {
        RegionService regionService = new RegionRepository();
        FindRegionByIdUseCase findRegionByIdUseCase = new FindRegionByIdUseCase(regionService);

        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);

        int cityCode = Integer.parseInt(textBeforeDot(cityBox.getSelectedItem().toString()));
        Optional<City> cityFound = findCityByIdUseCase.execute(cityCode);
        
        City deletedCity = deleteCityUseCase.execute(cityFound.get().getId());
        reloadComboBoxOptions();

        if (deletedCity != null) {

            City city = cityFound.get();
            Optional<Region> regionFind = findRegionByIdUseCase.execute(city.getRegionId());
            String regionName = regionFind.get().getName();

            String message = String.format(
                "City deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n" +
                "Region Id: %s\n",
                deletedCity.getId(),
                deletedCity.getName(),
                regionName
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("City not found or deletion failed.");
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

    private void updateCityBox() {
        RegionService regionService = new RegionRepository();
        FindRegionByIdUseCase findRegionByIdUseCase = new FindRegionByIdUseCase(regionService);

        CityService cityService = new CityRepository();
        FindCitysByRegionUseCase findCitysByRegionUseCase = new FindCitysByRegionUseCase(cityService);
        
        cityBox.removeAllItems();

        this.regionId = Integer.parseInt(textBeforeDot(regionBox.getSelectedItem().toString()));

        Optional<Region> regionFound = findRegionByIdUseCase.execute(regionId);
        if (regionFound.isPresent()) {
            this.regionName = regionFound.get().getName();
            List<City> citys = findCitysByRegionUseCase.execute(regionId);
            for (City cityItem : citys) {
                cityBox.addItem(cityItem.getId() + ". " + cityItem.getName());
            }
        }
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
