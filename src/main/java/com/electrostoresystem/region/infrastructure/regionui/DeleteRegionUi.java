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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.electrostoresystem.country.application.FindAllCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.region.application.DeleteRegionUseCase;
import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.FindRegionsByCountryUseCase;
import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;


public class DeleteRegionUi extends JFrame {
    private final DeleteRegionUseCase deleteRegionUseCase;
    private final RegionUiController regionUiController;
    private JComboBox<String> countryBox,regionBox; 
    private JTextArea resultArea;
    private int countryId;
    private String countryName;
    
    public DeleteRegionUi(DeleteRegionUseCase deleteRegionUseCase, RegionUiController regionUiController) {
        this.deleteRegionUseCase = deleteRegionUseCase;
        this.regionUiController = regionUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Region");
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

        JLabel titleLabel = new JLabel("Delete Region");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        countryBox = new JComboBox<>();
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountriesUseCase = new FindAllCountryUseCase(countryService);
        List<Country> countries = findAllCountriesUseCase.execute();
        for (Country country : countries) {
            countryBox.addItem(country.getId() + ". " + country.getName());
        }
        countryBox.addActionListener(e -> updateRegionBox());

        regionBox = new JComboBox<>();


        addComponent(new JLabel("Country:"), 1, 0, 1);
        addComponent(countryBox, 1, 1, 1);
        addComponent(new JLabel("Regions:"), 2, 0, 1);
        addComponent(regionBox, 2, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteRegion());
        addComponent(btnDelete, 3, 0, 2);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            dispose();
            regionUiController.showCrudOptions();
        });
        addComponent(btnClose, 5    , 0, 2);
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

    private void deleteRegion() {
        CountryService countryService = new CountryRepository();
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);

        RegionService regionService = new RegionRepository();
        FindRegionByIdUseCase findRegionByIdUseCase = new FindRegionByIdUseCase(regionService);

        int regionCode = Integer.parseInt(textBeforeDot(regionBox.getSelectedItem().toString()));
        Optional<Region> regionFound = findRegionByIdUseCase.execute(regionCode);
        
        Region deletedRegion = deleteRegionUseCase.execute(regionFound.get().getId());
        reloadComboBoxOptions();

        if (deletedRegion != null) {

            Region region = regionFound.get();
            Optional<Country> countryFind = findCountryByIdUseCase.execute(region.getCountryId());
            String countryName = countryFind.get().getName();

            String message = String.format(
                "Region deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n" +
                "Country Id: %s\n",
                deletedRegion.getId(),
                deletedRegion.getName(),
                countryName
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Region not found or deletion failed.");
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
