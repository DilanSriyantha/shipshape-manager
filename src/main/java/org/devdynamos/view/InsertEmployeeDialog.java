package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Employee;
import org.devdynamos.models.Skill;
import org.devdynamos.utils.InputValidator;
import org.devdynamos.listModels.SkillsListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

public class InsertEmployeeDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtName;
    private JComboBox cmbJobRole;
    private JList lstSkills;
    private JTextField txtSkill;
    private JButton btnAddSkill;
    private JTextField txtEmail;
    private JTextField txtContactNumber;
    private JTextField txtWorkingArea;
    private JButton btnDeleteSkill;
    private JComboBox cmbWorkArea;

    private final SkillsListModel skillsListModel = new SkillsListModel();
    private final Employee employee;
    private final DialogPositiveCallback<Employee> positiveCallback;
    private final DialogNegativeCallback<Employee> negativeCallback;

    public InsertEmployeeDialog(Employee employee, DialogPositiveCallback<Employee> positiveCallback, DialogNegativeCallback<Employee> negativeCallback) {
        this.employee = employee;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        initButtons();
        initSkillsList();
        populateInputFields();
        setupModal();
    }

    private void setupModal() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(btnConfirm);
        btnConfirm.setFocusable(false);
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

        txtSkill.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    addSkill();
                }
            }
        });
    }

    private void populateInputFields(){
        txtName.setText(employee.getEmpName());
        txtEmail.setText(employee.getEmail());
        txtContactNumber.setText(employee.getContactNumber());

        if(employee.getJobRole() == null)
            cmbJobRole.setSelectedIndex(0);
        else
            cmbJobRole.setSelectedItem(employee.getJobRole());

        if(employee.getWorkArea() == null)
            cmbWorkArea.setSelectedIndex(0);
        else
            cmbWorkArea.setSelectedItem(employee.getWorkArea());

        if(employee.getSkills() == null) return;
        skillsListModel.addAll(employee.getSkills());
    }

    private void onOK() {
        if(!areInputsValid()){
            JOptionPane.showMessageDialog(null, "Invalid inputs are detected. Please check the information provided and try again.", "Invalid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        employee.setEmpName(txtName.getText());
        employee.setEmail(txtEmail.getText());
        employee.setContactNumber(txtContactNumber.getText());
        employee.setJobRole((String)cmbJobRole.getSelectedItem());
        employee.setWorkArea((String)cmbWorkArea.getSelectedItem());

        positiveCallback.execute(this, employee);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    private boolean areInputsValid() {
        boolean validName = !this.txtName.getText().isEmpty();
        boolean validEmail = InputValidator.isValidEmail(this.txtEmail.getText());
        boolean validPhoneNumber = InputValidator.isValidPhoneNumber(this.txtContactNumber.getText());
        boolean validJobRole = this.cmbJobRole.getSelectedIndex() > 0;

        System.out.println(validName);
        System.out.println(validEmail);
        System.out.println(validPhoneNumber);
        System.out.println(validJobRole);

        return validName && validEmail && validPhoneNumber && validJobRole;
    }

    @SuppressWarnings("unchecked")
    private void initSkillsList() {
        this.lstSkills.setModel(this.skillsListModel);

        this.btnAddSkill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSkill();
            }
        });

        this.btnDeleteSkill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSkill();
            }
        });

        this.lstSkills.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                handleDeleteEnable();
            }
        });
    }

    private void addSkill() {
        final String skillDescription = txtSkill.getText();

        if(skillDescription.isEmpty()) return;;

        Skill skill = new Skill((int)(Math.random()*100), employee.getEmpId(), skillDescription);
        skillsListModel.addElement(skill);
        employee.addSkill(skill);
        txtSkill.setText("");
    }

    private void deleteSkill() {
        final int selectedIndex = lstSkills.getSelectedIndex();

        if(selectedIndex == -1) return;

        employee.removeSkill(skillsListModel.get(selectedIndex).getSkillId());
        skillsListModel.remove(selectedIndex);
    }

    private void handleDeleteEnable() {
        final int selectedIndex = lstSkills.getSelectedIndex();
        btnDeleteSkill.setEnabled(selectedIndex > -1);
    }

    public void showDialog() {
        setTitle("Insert Employee");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
