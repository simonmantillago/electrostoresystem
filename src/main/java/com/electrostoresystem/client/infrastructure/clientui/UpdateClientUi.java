package com.electrostoresystem.client.infrastructure.clientui;

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
import com.electrostoresystem.client.application.FindClientByIdUseCase;
import com.electrostoresystem.client.application.UpdateClientUseCase;
import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;
import com.electrostoresystem.client.infrastructure.ClientRepository;
import com.electrostoresystem.clienttype.application.FindAllClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;
import com.electrostoresystem.idtype.application.FindAllIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;

public class UpdateClientUi  extends JFrame{
    private final UpdateClientUseCase updateClientUseCase;
    private final ClientUiController clientUiController;

    private JTextField idTxt, emailTxt, addressTxt, nameTxt;
    private JComboBox<String>cityBox, typeIdBox, clientTypeIdBox;
    private String clientId;


    private JButton findButton, saveButton, resetButton, goBackButton;
    
    public UpdateClientUi(UpdateClientUseCase updateClientUseCase, ClientUiController clientUiController) {
        this.updateClientUseCase = updateClientUseCase;
        this.clientUiController = clientUiController;
    } 

    public void frmUpdateClient() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Client");
        setSize(500, 500);
        
        JLabel jLabel1 = new JLabel("Update Client");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        idTxt = new JTextField();
        
        nameTxt = new JTextField();

        typeIdBox = new JComboBox<>();
        IdTypeService idTypeService = new IdTypeRepository();
        FindAllIdTypeUseCase findAllIdTypeUseCase = new FindAllIdTypeUseCase(idTypeService);
        List<IdType> idTypes = findAllIdTypeUseCase.execute();
        for (IdType idType : idTypes) {
            typeIdBox.addItem(idType.getId() + ". " + idType.getName());
        }

        clientTypeIdBox = new JComboBox<>();
        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindAllClientTypeUseCase findAllClientTypeUseCase = new FindAllClientTypeUseCase(clientTypeService);
        List<ClientType> clientTypes = findAllClientTypeUseCase.execute();
        for (ClientType clientType : clientTypes) {
            clientTypeIdBox.addItem(clientType.getId() + ". " + clientType.getName());
        }

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
        addComponent(new JLabel("Id Type:"), 4 , 0);
        addComponent(typeIdBox, 4, 1);
        addComponent(new JLabel("Client Type:"), 5, 0);
        addComponent(clientTypeIdBox, 5, 1);
        addComponent(new JLabel("Email:"), 6, 0);
        addComponent(emailTxt, 6, 1);
        addComponent(new JLabel("City:"), 7, 0);
        addComponent(cityBox, 7, 1);
        addComponent(new JLabel("Address Detail:"), 8, 0);
        addComponent(addressTxt, 8, 1);
        
        
        resetButton.addActionListener(e -> resetFields());
        saveButton.addActionListener(e -> updateClient());
        goBackButton.addActionListener(e -> {
            dispose();
            clientUiController.showCrudOptions();
        });
        findButton.addActionListener(e -> findClient());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(goBackButton);
        addComponent(buttonPanel, 9, 0, 2);
        


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

    private void updateClient(){
        try{
            Client client = new Client();
            client.setId(idTxt.getText());
            client.setName(nameTxt.getText());
            client.setTypeId(Integer.parseInt(textBeforeDot(typeIdBox.getSelectedItem().toString())));
            client.setClientTypeId(Integer.parseInt(textBeforeDot(clientTypeIdBox.getSelectedItem().toString())));
            client.setEmail(emailTxt.getText());
            client.setCityId(Integer.parseInt(textBeforeDot(cityBox.getSelectedItem().toString())));
            client.setAddressDetails(addressTxt.getText());

            updateClientUseCase.execute(client);
            reloadComboBoxOptions();
            resetFields();
        }catch(Exception e){

        }
    }

    private void findClient() {
        ClientService clientService = new ClientRepository();
        FindClientByIdUseCase findClientByIdUseCase = new FindClientByIdUseCase(clientService);

        CityService cityService = new CityRepository();
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);
        FindCitysByRegionUseCase  findCitysByRegionUseCase = new FindCitysByRegionUseCase(cityService);

        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByIdUseCase findIdTypeByIdUseCase = new FindIdTypeByIdUseCase(idTypeService);

        ClientTypeService clientTypeService = new ClientTypeRepository();
        FindClientTypeByIdUseCase findClientTypeByIdUseCase = new FindClientTypeByIdUseCase(clientTypeService);



        String clientId = idTxt.getText();
        Optional<Client> clientToUpdate = findClientByIdUseCase.execute(clientId);
        
        if (clientToUpdate.isPresent()) {
            Client foundClient = clientToUpdate.get();
            
            Optional<IdType> foundIdType = findIdTypeByIdUseCase.execute(foundClient.getTypeId());
            Optional<ClientType> foundClientType = findClientTypeByIdUseCase.execute(foundClient.getClientTypeId());
            Optional<City> foundCity = findCityByIdUseCase.execute(foundClient.getCityId());
            
            if (foundIdType.isPresent() && foundClientType.isPresent() && foundCity.isPresent()) {
                List<City> citiesFound = findCitysByRegionUseCase.execute(foundCity.get().getRegionId());
        
                this.clientId = foundClient.getId();
                nameTxt.setText(foundClient.getName());
                typeIdBox.setSelectedItem(foundClient.getTypeId() + ". " + foundIdType.get().getName());
                clientTypeIdBox.setSelectedItem(foundClient.getClientTypeId() +  ". " + foundClientType.get().getName());
                emailTxt.setText(foundClient.getEmail());
                cityBox.removeAllItems();
                
                for (City cityItem : citiesFound) {
                    cityBox.addItem(cityItem.getId() + ". " + cityItem.getName());
                }
                
                cityBox.setSelectedItem(foundClient.getCityId() + ". " + foundCity.get().getName());
                addressTxt.setText(foundClient.getAddressDetails());
                idTxt.setEditable(false);
              
                showComponents();
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Client data is incomplete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Client not found!", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("No se encontró el cliente con el ID proporcionado");
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
        typeIdBox.setVisible(false);
        clientTypeIdBox.setVisible(false);
        emailTxt.setVisible(false);
        cityBox.setVisible(false);
        addressTxt.setVisible(false);
        saveButton.setVisible(false);
        resetButton.setVisible(false);
    }
    
    private void showComponents() {
        nameTxt.setVisible(true);
        typeIdBox.setVisible(true);
        clientTypeIdBox.setVisible(true);
        emailTxt.setVisible(true);
        cityBox.setVisible(true);
        addressTxt.setVisible(true);
        saveButton.setVisible(true);
        resetButton.setVisible(true);
    }
}
