package com.electrostoresystem.client.infrastructure.clientui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.electrostoresystem.client.application.FindAllClientUseCase;
import com.electrostoresystem.client.domain.entity.Client;

public class FindAllClientUi {
    private final FindAllClientUseCase findAllClientUseCase;
    private final ClientUiController clientUiController;
    private JFrame frame;

    public FindAllClientUi(FindAllClientUseCase findAllClientUseCase, ClientUiController clientUiController) {
        this.findAllClientUseCase = findAllClientUseCase;
        this.clientUiController = clientUiController;
    }

    public void showAllClients() {
        frame = new JFrame("All Clients");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All Clients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable clientTable = createClientTable();
        JScrollPane scrollPane = new JScrollPane(clientTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            clientUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createClientTable() {
        String[] columnNames = {"id", "name", "type_id", "client_type", "email", "city_id", "address_details"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Client> clients = findAllClientUseCase.execute();
        if (!clients.isEmpty()) {
            for (Client client : clients) {
                Object[] rowData = {
                    client.getId(),
                    client.getName(),
                    client.getTypeId(),
                    client.getClientTypeId(),
                    client.getEmail(),
                    client.getCityId(),
                    client.getAddressDetails()
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No clients found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}
