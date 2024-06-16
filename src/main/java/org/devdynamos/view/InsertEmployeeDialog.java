package org.devdynamos.view;

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

    private boolean UPDATE_MODE = false;
    private Employee employee;

    private final SkillsListModel skillsListModel = new SkillsListModel();

    public InsertEmployeeDialog() {
        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(btnConfirm);

        initSkillsList();

        btnConfirm.setFocusable(false);

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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
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

    public InsertEmployeeDialog(Employee employee){
        this.UPDATE_MODE = true;
        this.employee = employee;

        populateInputFields(this.employee);
        populateSkillsList(this.employee.getSkills());

        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(btnConfirm);

        initSkillsList();

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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        txtSkill.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    addSkill();
                }
            }
        });
    }

    private void populateInputFields(Employee employee){
        this.txtName.setText(employee.getEmpName());
        this.txtEmail.setText(employee.getEmail());
        this.txtContactNumber.setText(employee.getContactNumber());
        this.cmbJobRole.setSelectedItem(employee.getJobRole());
        this.cmbWorkArea.setSelectedItem(employee.getWorkArea());
    }

    private void populateSkillsList(List<Skill> skills){
        if(skills == null) return;
        this.skillsListModel.addAll(skills);
    }

    private void onOK() {
        // add your code here

        if(!areInputsValid()){
            JOptionPane.showMessageDialog(null, "Invalid inputs are detected. Please check the information provided and try again.", "Invalid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(this.UPDATE_MODE){
            initiateUpdate();
            return;
        }
        initiateInsertion();

        this.dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void initiateInsertion(){
        Employee employee = new Employee(
                0,
                this.txtName.getText(),
                this.txtEmail.getText(),
                this.txtContactNumber.getText(),
                (String)this.cmbJobRole.getModel().getElementAt(this.cmbJobRole.getSelectedIndex()),
                true,
                (String)this.cmbWorkArea.getModel().getElementAt(this.cmbWorkArea.getSelectedIndex()),
                false
        );

        final int id = employee.insert();

        this.skillsListModel.setEmpIdBatch(id);
        this.skillsListModel.insertBatch();
    }

    private void initiateUpdate() {
        employee.setEmpName(this.txtName.getText());
        employee.setEmail(this.txtEmail.getText());
        employee.setContactNumber(this.txtContactNumber.getText());
        employee.setJobRole((String)this.cmbJobRole.getModel().getElementAt(this.cmbJobRole.getSelectedIndex()));
        employee.setWorkArea((String)this.cmbWorkArea.getModel().getElementAt(this.cmbWorkArea.getSelectedIndex()));

        employee.update();
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

        if(this.UPDATE_MODE){
            Skill skill = new Skill(employee.getMaxSkillId() + 1, employee.getEmpId(), skillDescription);
            this.employee.addSkill(skill);
            skillsListModel.addElement(skill);
            txtSkill.setText("");

            return;
        }

        skillsListModel.addElement(new Skill(-1, -1, skillDescription));
        txtSkill.setText("");
    }

    private void deleteSkill() {
        final int selectedIndex = lstSkills.getSelectedIndex();

        if(selectedIndex == -1) return;

        if(this.UPDATE_MODE){
            System.out.println(skillsListModel.get(selectedIndex).getSkillId());
            employee.removeSkill(skillsListModel.get(selectedIndex).getSkillId());
        }

        skillsListModel.remove(selectedIndex);
    }

    private void handleDeleteEnable() {
        final int selectedIndex = lstSkills.getSelectedIndex();
        btnDeleteSkill.setEnabled(selectedIndex > -1);
    }

    public void showDialog() {
        InsertEmployeeDialog dialog = new InsertEmployeeDialog();
        dialog.setTitle("Insert Employee");
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void showDialog(Employee employee) {
        InsertEmployeeDialog dialog = new InsertEmployeeDialog(employee);
        dialog.setTitle("Update Employee");
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
