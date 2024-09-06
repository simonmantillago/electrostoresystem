package com.electrostoresystem.clientphone.infrastructure.clientphoneui;

import java.awt.BorderLayout;
import java.util.List;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.domain.entity.ClientPhone;

public class FindAllClientPhoneUi {
private final FindAllClientPhoneUseCase findAllClientPhoneUseCase;
    private final ClientPhoneUiController clientPhoneUiController;
    private JFrame frame;

    public FindAllClientPhoneUi(FindAllClientPhoneUseCase findAllClientPhoneUseCase, ClientPhoneUiController clientPhoneUiController) {
        this.findAllClientPhoneUseCase = findAllClientPhoneUseCase;
        this.clientPhoneUiController = clientPhoneUiController;
    }

    public void showAllClientPhones() {
        frame = new JFrame("All Phones Registered");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All ClientPhones");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable clientPhoneTable = createClientPhoneTable();
        JScrollPane scrollPane = new JScrollPane(clientPhoneTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            clientPhoneUiController.showCrudOptions();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JTable createClientPhoneTable() {
        String[] columnNames = {"Phone Number","Client Id"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<ClientPhone> clientPhones = findAllClientPhoneUseCase.execute();
        if (!clientPhones.isEmpty()) {
            for (ClientPhone clientPhone : clientPhones) {
                Object[] rowData = {
                    clientPhone.getClientId(),
                    clientPhone.getPhone(),
                };
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No Phones found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }
}