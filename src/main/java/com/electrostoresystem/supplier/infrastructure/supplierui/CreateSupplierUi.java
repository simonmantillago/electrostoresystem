package com.electrostoresystem.supplier.infrastructure.supplierui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.electrostoresystem.city.application.FindCitysByRegionUseCase;
import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;
import com.electrostoresystem.supplier.application.CreateSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplierphone.application.CreateSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.infrastructure.SupplierPhoneRepository;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;
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

public class CreateSupplierUi  extends JFrame{
    private final CreateSupplierUseCase createSupplierUseCase;
    private final SupplierUiController supplierUiController;

    private JTextField idTxt, emailTxt, addressTxt, phoneNumberTxt, nameTxt;
    private JComboBox<String> countryBox, regionBox, cityBox;
    private int regionId,countryId;
    private String regionName, countryName;


    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    
    public CreateSupplierUi(CreateSupplierUseCase createSupplierUseCase, SupplierUiController supplierUiController) {
        this.createSupplierUseCase = createSupplierUseCase;
        this.supplierUiController = supplierUiController;
    } 

    public void frmRegSupplier() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Supplier");
        setSize(500, 500);
        
        JLabel jLabel1 = new JLabel("Create Supplier");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        idTxt = new JTextField();
        
        nameTxt = new JTextField();

        emailTxt = new JTextField();

        phoneNumberTxt = new JTextField();

        countryBox = new JComboBox<>();
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountriesUseCase = new FindAllCountryUseCase(countryService);

        List<Country> countries = findAllCountriesUseCase.execute();
        for (Country country : countries) {
            countryBox.addItem(country.getId() + ". " + country.getName());
        }
        countryBox.addActionListener(e -> updateRegionBox());

        regionBox = new JComboBox<>();
        regionBox.addActionListener(e -> updateCityBox());

        cityBox = new JComboBox<>();

        addressTxt = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> saveSupplier());
        jButton3.addActionListener(e -> {
            dispose();
            supplierUiController.showCrudOptions();
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Id:"), 1, 0);
        addComponent(idTxt, 1, 1);
        addComponent(new JLabel("Name:"), 2, 0);
        addComponent(nameTxt, 2, 1);
        addComponent(new JLabel("Email:"), 3, 0);
        addComponent(emailTxt, 3, 1);
        addComponent(new JLabel("Phone Number:"), 4, 0);
        addComponent(phoneNumberTxt, 4, 1);
        addComponent(new JLabel("Country:"), 5, 0);
        addComponent(countryBox, 5, 1);
        addComponent(new JLabel("Region:"), 6, 0);
        addComponent(regionBox, 6, 1);
        addComponent(new JLabel("City:"), 7, 0);
        addComponent(cityBox, 7, 1);
        addComponent(new JLabel("Address Detail:"), 8, 0);
        addComponent(addressTxt, 8, 1);
    

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 9;
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

    private void saveSupplier() {
        try {
            Supplier supplier = new Supplier();
            supplier.setId(idTxt.getText());
            supplier.setName(nameTxt.getText());
            supplier.setEmail(emailTxt.getText());
            supplier.setCityId(Integer.parseInt(textBeforeDot(cityBox.getSelectedItem().toString())));
            supplier.setAddressDetails(addressTxt.getText());

            createSupplierUseCase.execute(supplier); 
            savePhone();
            JOptionPane.showMessageDialog(this, "Supplier added successfully!");
            resetFields();
            reloadComboBoxOptions();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePhone(){
        SupplierPhoneService supplierPhoneService = new SupplierPhoneRepository();
        CreateSupplierPhoneUseCase createSupplierPhoneUseCase = new CreateSupplierPhoneUseCase(supplierPhoneService);
        try {
            SupplierPhone supplierPhone = new SupplierPhone();
            supplierPhone.setSupplierId(idTxt.getText());
            supplierPhone.setPhone(phoneNumberTxt.getText());

            createSupplierPhoneUseCase.execute(supplierPhone);
        }catch (Exception ex) {}
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
    private void reloadComboBoxOptions() {
        cityBox.removeAllItems(); 
        regionBox.removeAllItems();
    }

    private void resetFields() {
        idTxt.setText("");
        emailTxt.setText("");
        addressTxt.setText("");
        phoneNumberTxt.setText("");
        cityBox.removeAllItems();
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
