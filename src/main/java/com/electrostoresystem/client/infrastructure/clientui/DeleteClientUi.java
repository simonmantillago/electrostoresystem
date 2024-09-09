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
import com.electrostoresystem.client.application.DeleteClientUseCase;
import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.clientphone.application.DeleteClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;


public class DeleteClientUi extends JFrame {
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientUiController clientUiController;
    private JTextField jTextField1;
    private JTextArea resultArea;
    
    public DeleteClientUi(DeleteClientUseCase deleteClientUseCase, ClientUiController clientUiController) {
        this.deleteClientUseCase = deleteClientUseCase;
        this.clientUiController = clientUiController;
    }
    
    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete Client");
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
        
        JLabel titleLabel = new JLabel("Delete Client");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);
        
        JLabel lblId = new JLabel("Client Id:");
        addComponent(lblId, 1, 0);
        
        jTextField1 = new JTextField();
        addComponent(jTextField1, 1, 1);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteClient());
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
            clientUiController.showCrudOptions();
        });
        addComponent(btnClose, 5, 0, 2);
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

    private void deleteClient() {
        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);

        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByIdUseCase findClientTypeByIdUseCase = new FindClientTypeByIdUseCase(clientTypeService);

        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByIdUseCase findIdTypeByIdUseCase = new FindIdTypeByIdUseCase(idTypeService);

        ClientPhoneService clientPhoneService = new ClientPhoneRepository();
        DeleteClientPhonesByClientIdUseCase deleteClientPhonesByClientIdUseCase = new DeleteClientPhonesByClientIdUseCase(clientPhoneService);

        String clientId = jTextField1.getText();
        deleteClientPhonesByClientIdUseCase.execute(clientId);
        Client deletedClient = deleteClientUseCase.execute(clientId);

        Optional<IdType> foundIdType = findIdTypeByIdUseCase.execute(deletedClient.getTypeId());
        Optional<ClientType> foundClientType = findClientTypeByIdUseCase.execute(deletedClient.getClientTypeId());
        Optional<City> foundCity = findCityByIdUseCase.execute(deletedClient.getCityId());

        String message = String.format(
            "Client found successfully:\n\n" +
            "Id: %s\n" +
            "Name: %s\n" +
            "Id Type: %s\n" +
            "Client Type: %s\n"+
            "Email: %s\n"+
            "City: %s\n"+
            "Address: %s\n",
            deletedClient.getId(),
            deletedClient.getName(),
            foundIdType.get().getName(),
            foundClientType.get().getName(),
            deletedClient.getEmail(),
            foundCity.get().getName(),
            deletedClient.getAddressDetails()
        );
        resultArea.setText(message);
    }
}

