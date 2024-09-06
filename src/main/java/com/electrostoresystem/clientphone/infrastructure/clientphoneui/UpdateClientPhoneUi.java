package com.electrostoresystem.clientphone.infrastructure.clientphoneui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhoneByPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.application.UpdateClientPhoneUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class UpdateClientPhoneUi extends JFrame {
    private final UpdateClientPhoneUseCase updateClientPhoneUseCase;
    private final FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase;
    private final ClientPhoneUiController clientPhoneUiController;

    
    
    private JTextField jTextField1; // ClientPhone Name
    private JTextField jTextField2; // ClientPhone Name
    private JTextField jTextField3; // ClientPhone Name
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private String foundId;

    public UpdateClientPhoneUi(UpdateClientPhoneUseCase updateClientPhoneUseCase, FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase, ClientPhoneUiController clientPhoneUiController) {
        this.updateClientPhoneUseCase = updateClientPhoneUseCase;
        this.findClientPhoneByPhoneUseCase = findClientPhoneByPhoneUseCase;
        this.clientPhoneUiController = clientPhoneUiController;
    }

    public void frmUpdateClientPhone() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();
        FindAllClientPhoneUseCase findAllClientPhoneUseCase = new FindAllClientPhoneUseCase(clientPhoneService);
        initComponents(findAllClientPhoneUseCase);
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents(FindAllClientPhoneUseCase findAllClientPhoneUseCase) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update ClientPhone");
        setSize(500, 500);

        // Establecer el layout antes de agregar componentes
        setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Update ClientPhone");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        
 

        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateClientPhone());
        jButton3.addActionListener(e -> {
            dispose();
            clientPhoneUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findClientPhone());

        // Añadir los componentes al contenedor
        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("Phone Number:"), 1, 0);
        addComponent(jTextField1, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("Phone Number:"), 3, 0);
        addComponent(jTextField2, 3, 1);
        addComponent(new JLabel("Client Id:"), 4, 0);
        addComponent(jTextField2, 4, 1);
        

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        addComponent(buttonPanel, 5, 0, 2);

        setLocationRelativeTo(null);

        hideComponents();
    }

    private void addComponent(Component component, int row, int col) {
        addComponent(component, row, col, 1);
    }

    private void addComponent(Component component, int row, int col, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;  // La columna en la que se agregará el componente
        gbc.gridy = row;  // La fila en la que se agregará el componente
        gbc.gridwidth = width;  // Número de celdas de ancho que ocupará el componente
        gbc.fill = GridBagConstraints.HORIZONTAL;  // El componente se estirará horizontalmente
        gbc.insets = new Insets(5, 5, 5, 5);  // Márgenes alrededor del componente
        gbc.anchor = GridBagConstraints.CENTER; // Centro del componente

        add(component, gbc);  // Añade el componente con las restricciones especificadas
    }

    private void updateClientPhone() {
        try {
            ClientPhone clientPhone = new ClientPhone();
            clientPhone.setPhone(jTextField2.getText());
            clientPhone.setClientId(jTextField3.getText());
    
            updateClientPhoneUseCase.execute(clientPhone);
            JOptionPane.showMessageDialog(this, "ClientPhone updated successfully!");
            resetFields();
        } catch (Exception ex) {
            // Imprime el mensaje de error completo en la consola
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findClientPhone() {
        String phoneToUpdate = jTextField1.getText();  // Obtener el número de teléfono a buscar
        Optional<ClientPhone> clientPhoneToUpdate = findClientPhoneByPhoneUseCase.execute(phoneToUpdate);
    
        if (clientPhoneToUpdate.isPresent()) {
            ClientPhone foundClientPhone = clientPhoneToUpdate.get();
            foundId = foundClientPhone.getPhone();  // Asignar el ID encontrado
            jTextField2.setText(foundClientPhone.getPhone());  // Mostrar el número de teléfono en el campo correspondiente
            jTextField3.setText(foundClientPhone.getClientId());  // Mostrar el client_id en el campo correspondiente
            showComponents();  // Mostrar los campos y botones relevantes
            revalidate();  // Asegurar que el layout se actualice
            repaint();  // Redibujar la ventana
        } else {
            JOptionPane.showMessageDialog(this, "ClientPhone not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        hideComponents();
    }

    private void hideComponents() {
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }



    
}

