package com.electrostoresystem.country.infrastructure.countryui;

import java.util.Optional;


import java.util.List;
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
import com.electrostoresystem.country.application.FindCountryByNameUseCase;
import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.infrastructure.CountryRepository;

public class FindCountryByIdUi extends JFrame {
    private final FindCountryByIdUseCase findCountryByIdUseCase;
    private final CountryUiController countryUiController;
    private JComboBox<String> countryOptions; 
    private JTextArea resultArea;



    public FindCountryByIdUi(FindCountryByIdUseCase findCountryByIdUseCase, CountryUiController countryUiController) {
        this.findCountryByIdUseCase = findCountryByIdUseCase;
        this.countryUiController = countryUiController;
    }

    public void showFindCountry() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Country");
        setSize(500, 500);

        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initComponents() {
        CountryService countryService = new CountryRepository();
        FindAllCountryUseCase findAllCountryUseCase = new FindAllCountryUseCase(countryService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Country");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Country:");
        addComponent(lblId, 1, 0);

        countryOptions = new JComboBox<>();
        List<Country> countrys = findAllCountryUseCase.execute();
        for (Country country : countrys) {
            countryOptions.addItem(country.getName());
        }
        addComponent(countryOptions, 1, 1);

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findCountry());
        addComponent(btnDelete, 2, 0, 2);

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
        btnClose.addActionListener(e -> {
            dispose();
            countryUiController.showCrudOptions();
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


    private void findCountry() {
        CountryService countryService = new CountryRepository();
        FindCountryByNameUseCase findCountryByNameUseCase = new FindCountryByNameUseCase(countryService);

        String countryCode = (String) countryOptions.getSelectedItem();
        Optional<Country> countryFind = findCountryByNameUseCase.execute(countryCode);
        int countryFindId = countryFind.get().getId();


        Optional<Country> countryOpt = findCountryByIdUseCase.execute(countryFindId);
        if (countryOpt.isPresent()) {
            Country country = countryOpt.get();
            String message = String.format(
                "Country found:\n\n" +
                "Id: %d\n" +
                "Country Name: %s\n",
                country.getId(),
                country.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Country not found!");
        }
    }
}
