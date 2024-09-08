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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import com.electrostoresystem.idtype.application.UpdateIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindAllIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;  // Added import for FindIdTypeByIdUseCase
import com.electrostoresystem.idtype.application.FindIdTypeByNameUseCase;
import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;

public class UpdateIdTypeUi extends JFrame {
    private final UpdateIdTypeUseCase updateIdTypeUseCase;
    private final IdTypeUiController idTypeUiController;

   private JComboBox<String> idTypeOptions;
   private JTextField idTypeNameField;
    private JButton jButton1; // Reset
    private JButton jButton2; // Save
    private JButton jButton3; // Go back
    private JButton jButton4; // Find
    private int idTypeFindId;

    public UpdateIdTypeUi(UpdateIdTypeUseCase updateIdTypeUseCase, IdTypeUiController idTypeUiController) {
        this.updateIdTypeUseCase = updateIdTypeUseCase;
        this.idTypeUiController = idTypeUiController;
    }

    public void frmUpdateIdType() {
        initComponents();
        reloadComboBoxOptions();
        setVisible(true);
    }

    private void initComponents() {
        IdTypeService idTypeService = new IdTypeRepository();
        FindAllIdTypeUseCase findAllIdTypeUseCase = new FindAllIdTypeUseCase(idTypeService);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update IdType");
        setSize(500, 500);

        JLabel jLabel1 = new JLabel("Update IdType");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        idTypeOptions = new JComboBox<>();
        List<IdType> idTypes = findAllIdTypeUseCase.execute();
        for (IdType idType : idTypes) {
            idTypeOptions.addItem(idType.getName());
        }
        idTypeNameField = new JTextField();

        jButton1 = new JButton("Reset");
        jButton2 = new JButton("Save");
        jButton3 = new JButton("Go back");
        jButton4 = new JButton("Find");

        jButton1.addActionListener(e -> resetFields());
        jButton2.addActionListener(e -> updateIdType());
        jButton3.addActionListener(e -> {
            dispose();
            idTypeUiController.showCrudOptions();
        });
        jButton4.addActionListener(e -> findIdType());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComponent(jLabel1, 0, 0, 2);
        addComponent(new JLabel("IdType:"), 1, 0);
        addComponent(idTypeOptions, 1, 1);
        addComponent(jButton4, 2, 0, 2);
        addComponent(new JLabel("IdType Name:"), 3, 0);
        addComponent(idTypeNameField, 3, 1);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton3);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

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
        gbc.insets = new Insets(5, 5, 5, 5);
        add(component, gbc);
    }

    private void updateIdType() {
        try {
            IdType idType = new IdType();
            idType.setId(idTypeFindId);
            idType.setName(idTypeNameField.getText());

            updateIdTypeUseCase.execute(idType);
            JOptionPane.showMessageDialog(this, "IdType updated successfully!");
            reloadComboBoxOptions();
            resetFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findIdType() {
        IdTypeService idTypeService = new IdTypeRepository();
        FindIdTypeByNameUseCase findIdTypeByNameUseCase = new FindIdTypeByNameUseCase(idTypeService);
        FindIdTypeByIdUseCase findIdTypeByIdUseCase = new FindIdTypeByIdUseCase(idTypeService);

        String idTypeCode = (String) idTypeOptions.getSelectedItem();
        Optional<IdType> idTypeFind = findIdTypeByNameUseCase.execute(idTypeCode);
        idTypeFindId = idTypeFind.get().getId();

        Optional<IdType> idTypeToUpdate = findIdTypeByIdUseCase.execute(idTypeFindId);
    
        if (idTypeToUpdate.isPresent()) {
            IdType foundIdType = idTypeToUpdate.get();
            idTypeNameField.setText(foundIdType.getName());
            idTypeOptions.setEditable(false);
            showComponents();
            revalidate(); 
            repaint(); 
        } else {
            JOptionPane.showMessageDialog(this, "IdType not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        idTypeNameField.setText("");
        idTypeOptions.setEditable(false);
        hideComponents();
    }

    private void hideComponents() {
        idTypeNameField.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
    }

    private void showComponents() {
        idTypeNameField.setVisible(true);
        jButton1.setVisible(true);
        jButton2.setVisible(true);
    }

    private void reloadComboBoxOptions() {
        idTypeOptions.removeAllItems(); // Elimina todos los elementos actuales del JComboBox
    
        IdTypeService idTypeService = new IdTypeRepository();
        FindAllIdTypeUseCase findAllIdTypeUseCase = new FindAllIdTypeUseCase(idTypeService);
        
        List<IdType> idTypes = findAllIdTypeUseCase.execute();
        for (IdType idType : idTypes) {
            idTypeOptions.addItem(idType.getName()); // Añade los roles actualizados al JComboBox
        }
    }
}
