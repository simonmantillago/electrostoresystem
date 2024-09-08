package com.electrostoresystem.salestatus.infrastructure.salestatusui;

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

import com.electrostoresystem.salestatus.application.FindAllSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByNameUseCase;
import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

public class FindSaleStatusByIdUi extends JFrame {
    private final FindSaleStatusByIdUseCase findSaleStatusByIdUseCase;
    private final SaleStatusUiController saleStatusUiController;
    private JComboBox<String> saleStatusOptions; 
    private JTextArea resultArea;



    public FindSaleStatusByIdUi(FindSaleStatusByIdUseCase findSaleStatusByIdUseCase, SaleStatusUiController saleStatusUiController) {
        this.findSaleStatusByIdUseCase = findSaleStatusByIdUseCase;
        this.saleStatusUiController = saleStatusUiController;
    }

    public void showFindSaleStatus() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find SaleStatus");
        setSize(500, 500);

        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initComponents() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindAllSaleStatusUseCase findAllSaleStatusUseCase = new FindAllSaleStatusUseCase(saleStatusService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find SaleStatus");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("SaleStatus:");
        addComponent(lblId, 1, 0);

        saleStatusOptions = new JComboBox<>();
        List<SaleStatus> saleStatuss = findAllSaleStatusUseCase.execute();
        for (SaleStatus saleStatus : saleStatuss) {
            saleStatusOptions.addItem(saleStatus.getName());
        }
        addComponent(saleStatusOptions, 1, 1);

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findSaleStatus());
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
            saleStatusUiController.showCrudOptions();
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


    private void findSaleStatus() {
        SaleStatusService saleStatusService = new SaleStatusRepository();
        FindSaleStatusByNameUseCase findSaleStatusByNameUseCase = new FindSaleStatusByNameUseCase(saleStatusService);

        String saleStatusCode = (String) saleStatusOptions.getSelectedItem();
        Optional<SaleStatus> saleStatusFind = findSaleStatusByNameUseCase.execute(saleStatusCode);
        int saleStatusFindId = saleStatusFind.get().getId();


        Optional<SaleStatus> saleStatusOpt = findSaleStatusByIdUseCase.execute(saleStatusFindId);
        if (saleStatusOpt.isPresent()) {
            SaleStatus saleStatus = saleStatusOpt.get();
            String message = String.format(
                "SaleStatus found:\n\n" +
                "Id: %d\n" +
                "SaleStatus Name: %s\n",
                saleStatus.getId(),
                saleStatus.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("SaleStatus not found!");
        }
    }
}
