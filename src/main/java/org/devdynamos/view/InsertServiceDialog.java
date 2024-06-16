package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.RequiredSkill;
import org.devdynamos.models.Service;
import org.devdynamos.listModels.RequiredSkillsListModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class InsertServiceDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtName;
    private JTextField txtSkill;
    private JButton btnAddSkill;
    private JButton btnDeleteSkill;
    private JList lstSkills;
    private JTextField txtUnitPrice;

    private final Service service;
    private final DialogPositiveCallback<Service> positiveCallback;
    private final DialogNegativeCallback<Service> negativeCallback;
    private final RequiredSkillsListModel skillsListModel = new RequiredSkillsListModel();

    public InsertServiceDialog(Service service, DialogPositiveCallback<Service> positiveCallback, DialogNegativeCallback<Service> negativeCallback){
        this.service = service;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setupModalProperties();
        initButtons();
        initSkillsList();
        initInputRestrictions();
        populateFields();
    }

    private void setupModalProperties() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAddSkill);
    }

    private void initButtons() {
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initInputRestrictions() {
        txtUnitPrice.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtUnitPrice.getText().isEmpty()) return;
                checkInputValidity(txtUnitPrice);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void checkInputValidity(JTextField field) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String text = field.getText();
                try{
                    Double.parseDouble(text);
                }catch(Exception ex){
                    String target = !text.isEmpty() ? String.valueOf(text.charAt(text.length() - 1)) : "";
                    if(!target.isEmpty()){
                        field.setText(text.replace(target, ""));
                    }else{
                        field.setText("");
                    }
                }
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    @SuppressWarnings("unchecked")
    private void initSkillsList() {
        lstSkills.setModel(skillsListModel);

        btnAddSkill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSkill();
            }
        });

        btnDeleteSkill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSkill();
            }
        });

        lstSkills.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                handleDeleteEnable();
            }
        });
    }

    private void addSkill() {
        final String skillDescription = txtSkill.getText();

        if(skillDescription.isEmpty()) return;

        RequiredSkill requiredSkill = new RequiredSkill(-1, service.getServiceId(), skillDescription);

        service.addSkill(requiredSkill);
        skillsListModel.addElement(requiredSkill);
        txtSkill.setText("");
    }

    private void deleteSkill() {
        final int selectedIndex = lstSkills.getSelectedIndex();

        if(selectedIndex == -1) return;

        service.removeSkill(selectedIndex);
        skillsListModel.remove(selectedIndex);
    }

    private void handleDeleteEnable() {
        final int selectedIndex = lstSkills.getSelectedIndex();
        btnDeleteSkill.setEnabled(selectedIndex > -1);
    }

    private void populateFields() {
        txtName.setText(service.getServiceName());
        txtUnitPrice.setText(String.valueOf(service.getUnitPrice()));
        skillsListModel.addAll(service.getRequiredSkills());
    }

    private void onOK() {
        if(txtName.getText().isEmpty() || txtUnitPrice.getText().isEmpty()){
            dispose();
            return;
        }

        service.setServiceName(txtName.getText());
        service.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));

        positiveCallback.execute(this, service);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    public void showDialog() {
        setTitle("Insert new service");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
