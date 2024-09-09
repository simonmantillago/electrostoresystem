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

import com.electrostoresystem.city.application.FindCityByIdUseCase;
import com.electrostoresystem.city.application.FindCitysByRegionUseCase;
import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.application.UpdateSupplierUseCase;
import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;

public class UpdateSupplierUi  extends JFrame{
    private final UpdateSupplierUseCase updateSupplierUseCase;
    private final SupplierUiController supplierUiController;

    private JTextField idTxt, emailTxt, addressTxt, nameTxt;
    private JComboBox<String>cityBox;
    private String supplierId;


    private JButton findButton, saveButton, resetButton, goBackButton;
    
    public UpdateSupplierUi(UpdateSupplierUseCase updateSupplierUseCase, SupplierUiController supplierUiController) {
        this.updateSupplierUseCase = updateSupplierUseCase;
        this.supplierUiController = supplierUiController;
    } 

    public void frmUpdateSupplier() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Supplier");
        setSize(500, 500);
        
        JLabel jLabel1 = new JLabel("Update Supplier");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        idTxt = new JTextField();
        
        nameTxt = new JTextField();

        emailTxt = new JTextField();

        cityBox = new JComboBox<>();

        addressTxt = new JTextField();

        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        findButton = new JButton("Find");
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset");
        goBackButton = new JButton("Go back");

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Id:"), 1, 0);
        addComponent(idTxt, 1, 1);
        addComponent(findButton, 2, 0,2);
        addComponent(new JLabel("Name:"), 3, 0);
        addComponent(nameTxt, 3, 1);
        addComponent(new JLabel("Email:"), 4, 0);
        addComponent(emailTxt, 4, 1);
        addComponent(new JLabel("City:"), 5, 0);
        addComponent(cityBox, 5, 1);
        addComponent(new JLabel("Address Detail:"), 6, 0);
        addComponent(addressTxt, 6, 1);
        
        
        resetButton.addActionListener(e -> resetFields());
        saveButton.addActionListener(e -> updateSupplier());
        goBackButton.addActionListener(e -> {
            dispose();
            supplierUiController.showCrudOptions();
        });
        findButton.addActionListener(e -> findSupplier());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(goBackButton);
        addComponent(buttonPanel, 7, 0, 2);
        


        setLocationRelativeTo(null);
        hideComponents();
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

    private void updateSupplier(){
        try{
            Supplier supplier = new Supplier();
            supplier.setId(idTxt.getText());
            supplier.setName(nameTxt.getText());
            supplier.setEmail(emailTxt.getText());
            supplier.setCityId(Integer.parseInt(textBeforeDot(cityBox.getSelectedItem().toString())));
            supplier.setAddressDetails(addressTxt.getText());

            updateSupplierUseCase.execute(supplier);
            reloadComboBoxOptions();
            resetFields();
        }catch(Exception e){

        }
    }

    private void findSupplier() {
        SupplierService supplierService = new SupplierRepository();
        FindSupplierByIdUseCase findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);

        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);
        FindCitysByRegionUseCase  findCitysByRegionUseCase = new FindCitysByRegionUseCase(cityService);



        String supplierId = idTxt.getText();
        Optional<Supplier> supplierToUpdate = findSupplierByIdUseCase.execute(supplierId);
        
        if (supplierToUpdate.isPresent()) {
            Supplier foundSupplier = supplierToUpdate.get();
            
            Optional<City> foundCity = findCityByIdUseCase.execute(foundSupplier.getCityId());
            
            if (foundCity.isPresent()) {
                List<City> citiesFound = findCitysByRegionUseCase.execute(foundCity.get().getRegionId());
        
                this.supplierId = foundSupplier.getId();
                nameTxt.setText(foundSupplier.getName());
                emailTxt.setText(foundSupplier.getEmail());
                cityBox.removeAllItems();
                
                for (City cityItem : citiesFound) {
                    cityBox.addItem(cityItem.getId() + ". " + cityItem.getName());
                }
                
                cityBox.setSelectedItem(foundSupplier.getCityId() + ". " + foundCity.get().getName());
                addressTxt.setText(foundSupplier.getAddressDetails());
                idTxt.setEditable(false);
              
                showComponents();
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Supplier data is incomplete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Supplier not found!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void resetFields() {
        idTxt.setText("");
        nameTxt.setText("");
        emailTxt.setText("");
        cityBox.removeAllItems();
        addressTxt.setText("");
        idTxt.setEditable(true);
        hideComponents();
    }

    private void reloadComboBoxOptions() {

        cityBox.removeAllItems();
    }
    
    private void hideComponents() {
        nameTxt.setVisible(false);
        emailTxt.setVisible(false);
        cityBox.setVisible(false);
        addressTxt.setVisible(false);
        saveButton.setVisible(false);
        resetButton.setVisible(false);
    }
    
    private void showComponents() {
        nameTxt.setVisible(true);
        emailTxt.setVisible(true);
        cityBox.setVisible(true);
        addressTxt.setVisible(true);
        saveButton.setVisible(true);
        resetButton.setVisible(true);
    }
}
