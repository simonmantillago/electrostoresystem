package com.electrostoresystem.idtype.infrastructure.idtypeui;

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

import com.electrostoresystem.idtype.application.FindAllIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByNameUseCase;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;

public class FindIdTypeByIdUi extends JFrame {
    private final FindIdTypeByIdUseCase findIdTypeByIdUseCase;
    private final IdTypeUiController idTypeUiController;
    private JComboBox<String> idTypeOptions; 
    private JTextArea resultArea;



    public FindIdTypeByIdUi(FindIdTypeByIdUseCase findIdTypeByIdUseCase, IdTypeUiController idTypeUiController) {
        this.findIdTypeByIdUseCase = findIdTypeByIdUseCase;
        this.idTypeUiController = idTypeUiController;
    }

    public void showFindIdType() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Find IdType");
        setSize(500, 500);

        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initComponents() {
        IdTypeService idTypeService = new IdTypeRepository();
        FindAllIdTypeUseCase findAllIdTypeUseCase = new FindAllIdTypeUseCase(idTypeService);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Find IdType");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(titleLabel, 0, 0, 2);

        JLabel lblId = new JLabel("IdType:");
        addComponent(lblId, 1, 0);

        idTypeOptions = new JComboBox<>();
        List<IdType> idTypes = findAllIdTypeUseCase.execute();
        for (IdType idType : idTypes) {
            idTypeOptions.addItem(idType.getName());
        }
        addComponent(idTypeOptions, 1, 1);

        JButton btnDelete = new JButton("Find");
        btnDelete.addActionListener(e -> findIdType());
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
            idTypeUiController.showCrudOptions();
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


    private void findIdType() {
        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByNameUseCase findIdTypeByNameUseCase = new FindIdTypeByNameUseCase(idTypeService);

        String idTypeCode = (String) idTypeOptions.getSelectedItem();
        Optional<IdType> idTypeFind = findIdTypeByNameUseCase.execute(idTypeCode);
        int idTypeFindId = idTypeFind.get().getId();


        Optional<IdType> idTypeOpt = findIdTypeByIdUseCase.execute(idTypeFindId);
        if (idTypeOpt.isPresent()) {
            IdType idType = idTypeOpt.get();
            String message = String.format(
                "IdType found:\n\n" +
                "Id: %d\n" +
                "IdType Name: %s\n",
                idType.getId(),
                idType.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("IdType not found!");
        }
    }
}
