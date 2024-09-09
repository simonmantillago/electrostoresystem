package com.electrostoresystem.client.infrastructure.clientui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.electrostoresystem.city.application.FindCityByIdUseCase;
import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;
import com.electrostoresystem.client.application.FindClientByIdUseCase;
import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;


public class FindClientByIdUi extends JFrame {
    private final FindClientByIdUseCase findClientByIdUseCase;
    private final ClientUiController clientUiController;
    private JTextField jTextField1;
    private JTextArea resultArea;



    public FindClientByIdUi(FindClientByIdUseCase findClientByIdUseCase, ClientUiController clientUiController) {
        this.findClientByIdUseCase = findClientByIdUseCase;
        this.clientUiController = clientUiController;
    }

    public void showFindClient() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find Client");
        setSize(500, 500);


        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find Client");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("Client to find:");
        addComponent(lblId, 1, 0);

        jTextField1 = new JTextField();
        addComponent(jTextField1, 1, 1);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(e -> findClient());
        addComponent(btnFind, 2, 0, 2);

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
        btnClose.addActionListener(e -> {  dispose();
            clientUiController.showCrudOptions();
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

    private void findClient() {
        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);

        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByIdUseCase findClientTypeByIdUseCase = new FindClientTypeByIdUseCase(clientTypeService);

        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByIdUseCase findIdTypeByIdUseCase = new FindIdTypeByIdUseCase(idTypeService);

        String clientId = jTextField1.getText();
        Optional<Client> clientOpt = findClientByIdUseCase.execute(clientId);

        Optional<IdType> foundIdType = findIdTypeByIdUseCase.execute(clientOpt.get().getTypeId());
        Optional<ClientType> foundClientType = findClientTypeByIdUseCase.execute(clientOpt.get().getClientTypeId());
        Optional<City> foundCity = findCityByIdUseCase.execute(clientOpt.get().getCityId());

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            String message = String.format(
                "Client found successfully:\n\n" +
                "Id: %s\n" +
                "Name: %s\n" +
                "Id Type: %s\n" +
                "Client Type: %s\n"+
                "Email: %s\n"+
                "City: %s\n"+
                "Address: %s\n",
                client.getId(),
                client.getName(),
                foundIdType.get().getName(),
                foundClientType.get().getName(),
                client.getEmail(),
                foundCity.get().getName(),
                client.getAddressDetails()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("Client not found!");
        }
    }
}
