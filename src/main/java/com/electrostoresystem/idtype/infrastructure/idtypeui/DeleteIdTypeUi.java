package com.electrostoresystem.idtype.infrastructure.idtypeui;

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
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.electrostoresystem.idtype.application.DeleteIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindAllIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByNameUseCase;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;

public class DeleteIdTypeUi extends JFrame {
    private final DeleteIdTypeUseCase deleteIdTypeUseCase;
    private final IdTypeUiController idTypeUiController;
    private JComboBox<String> idTypeOptions; 
    private JTextArea resultArea;
    
    public DeleteIdTypeUi(DeleteIdTypeUseCase deleteIdTypeUseCase, IdTypeUiController idTypeUiController) {
        this.deleteIdTypeUseCase = deleteIdTypeUseCase;
        this.idTypeUiController = idTypeUiController;
    }

    public void showDeleteCustomer() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete IdType");
        setSize(500, 500);

        initComponents();
        reloadComboBoxOptions();
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

        JLabel titleLabel = new JLabel("Delete IdType");
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

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteIdType());
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

    private void deleteIdType() {
        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByNameUseCase findIdTypeByNameUseCase = new FindIdTypeByNameUseCase(idTypeService);

        String idTypeCode = (String) idTypeOptions.getSelectedItem();
        Optional<IdType> idTypeFind = findIdTypeByNameUseCase.execute(idTypeCode);
        int idTypeFindId = idTypeFind.get().getId();
        
        IdType deletedIdType = deleteIdTypeUseCase.execute(idTypeFindId);
        reloadComboBoxOptions();

        if (deletedIdType != null) {
            String message = String.format(
                "IdType deleted successfully:\n\n" +
                "Code: %s\n" +
                "Name: %s\n",
                deletedIdType.getId(),
                deletedIdType.getName()
            );
            resultArea.setText(message);
        } else {
            resultArea.setText("IdType not found or deletion failed.");
        }
    }

    private void reloadComboBoxOptions() {
        idTypeOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        IdTypeService idTypeService = new IdTypeRepository();
        FindAllIdTypeUseCase findAllIdTypeUseCase = new FindAllIdTypeUseCase(idTypeService);
        
        List<IdType> countries = findAllIdTypeUseCase.execute();
        for (IdType idType : countries) {
            idTypeOptions.addItem(idType.getName()); // Añade los idTypes actualizados al JComboBox
        }
    }
    
}
