package com.electrostoresystem.country.infrastructure.countryui;

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

import com.electrostoresystem.country.application.UpdateCountryUseCase;
import com.electrostoresystem.country.application.FindAllCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;  // Added import for FindCountryByIdUseCase
import com.electrostoresystem.country.application.FindCountryByNameUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;

public class UpdateCountryUi extends JFrame {
    private final UpdateCountryUseCase updateCountryUseCase;
    private final CountryUiController countryUiController;

   private JComboBox<String> countryOptions;
   private JTextField countryNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int countryFindId;

    public UpdateCountryUi(UpdateCountryUseCase updateCountryUseCase, CountryUiController countryUiController) {
        this.updateCountryUseCase = updateCountryUseCase;
        this.countryUiController = countryUiController;
    }

    public void frmUpdateCountry() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountryUseCase = new FindAllCountryUseCase(countryService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Country");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update Country");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        countryOptions = new JComboBox<>();
        List<Country> countrys = findAllCountryUseCase.execute();
        for (Country country : countrys) {
            countryOptions.addItem(country.getName());
        }
        countryNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateCountry());
        jButton3.addActionListener(e -> {
            dispose();
            countryUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findCountry());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Country:"), 1, 0);
        addComponent(countryOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Country Name:"), 3, 0);
        addComponent(countryNameField, 3, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

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
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }

    private void updateCountry() {
        try {
            Country country = new Country();
            country.setId(countryFindId);
            country.setName(countryNameField.getText());

            updateCountryUseCase.execute(country);
            JOptionPane.showMessageDialog(this, "Country updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findCountry() {
        CountryService countryService = new CountryRepository();
        FindCountryByNameUseCase findCountryByNameUseCase = new FindCountryByNameUseCase(countryService);
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);

        String countryCode = (String) countryOptions.getSelectedItem();
        Optional<Country> countryFind = findCountryByNameUseCase.execute(countryCode);
        countryFindId = countryFind.get().getId();

        Optional<Country> countryToUpdate = findCountryByIdUseCase.execute(countryFindId);
    
        if (countryToUpdate.isPresent()) {
            Country foundCountry = countryToUpdate.get();
            countryNameField.setText(foundCountry.getName());
            countryOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "Country not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        countryNameField.setText("");
        countryOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        countryNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        countryNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        countryOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountryUseCase = new FindAllCountryUseCase(countryService);
        
        List<Country> countries = findAllCountryUseCase.execute();
        for (Country country : countries) {
            countryOptions.addItem(country.getName()); // Añade los countrys actualizados al JComboBox
        }
    }
}
