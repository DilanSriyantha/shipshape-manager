package org.devdynamos.view;

import org.devdynamos.controller.AllocationController;
import org.devdynamos.controller.EmployeeController;
import org.devdynamos.controller.JobController;
import org.devdynamos.model.Employee;
import org.devdynamos.model.Job;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllocateView {
    private JPanel panel1;
    private JTextField txtEmpID;
    private JTextField txtEmpAddress;
    private JTextField txtEmpPosition;
    private JTextField txtEmpName;
    private JButton btnEmpAdd;
    private JLabel lblEmpID;
    private JLabel lblEmpName;
    private JLabel lblEmpAddress;
    private JLabel lblEmpPosition;
    private JTextField txtJobID;
    private JTextField txtJobTitle;
    private JTextField txtJobDesc;
    private JTextField txtShipName;
    private JTextField txtJobLocation;
    private JTextField txtJobDate;
    private JButton btnJobAdd;
    private JLabel lblJobID;
    private JLabel lblJobTitle;
    private JLabel lblJobDesc;
    private JLabel lblShipName;
    private JLabel lblJobLocation;
    private JLabel lblJobDate;
    private JComboBox comboBoxEmp;
    private JComboBox comboBoxJob;
    private JButton btnAllocate;
    private JLabel lblChooseJob;
    private JLabel lblChooseEmp;
    private JTextField txtAllocateTask;
    private JLabel lblAllocateTask;

    EmployeeController employeeController = new EmployeeController();
    Employee employee = null;
    JobController jobController = new JobController();
    Job job = null;
    AllocationController allocationController = new AllocationController();
    Job.Allocation allocation = null;
    JFrame frame = new JFrame("AllocateView");

    public AllocateView() {
        btnEmpAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addEmployee(); }
        });
        btnJobAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addJob(); }
        });
        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee selectedEmp = employeeController.getEmployeeList().get(comboBoxEmp.getSelectedIndex());
                Job selectedJob = jobController.getJobList().get(comboBoxJob.getSelectedIndex());
                allocation = allocationController.createAllocation(selectedJob, selectedEmp, txtAllocateTask.getText());
                JOptionPane.showMessageDialog(frame, selectedEmp.getmEmpName() + " Allocated for " + selectedJob.getmJobTitle());
            }
        });
    }

    public void addEmployee() {
        boolean valid = false;
        try{
            valid =
                    !txtEmpID.getText().isEmpty() &&
                    !txtEmpName.getText().isEmpty() &&
                    !txtEmpAddress.getText().isEmpty() &&
                    !txtEmpPosition.getText().isEmpty();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\n");
        }

        if (valid) {
            employee = employeeController.addEmployee(
                    txtEmpID.getText(),
                    txtEmpName.getText(),
                    txtEmpAddress.getText(),
                    txtEmpPosition.getText()
            );
            JOptionPane.showMessageDialog(frame, employee.getmEmpName() + " Added successfully!");
            comboBoxEmp.removeAllItems();
            for (Employee employee : employeeController.getEmployeeList()) {
                comboBoxEmp.addItem(employee.getmEmpID() + " - " + employee.getmEmpName());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid inputs!");
        }
    }

    public void addJob() {
        boolean valid = false;
        try{
            valid =
                    !txtJobID.getText().isEmpty() &&
                    !txtJobTitle.getText().isEmpty() &&
                    !txtJobDesc.getText().isEmpty() &&
                    !txtShipName.getText().isEmpty() &&
                    !txtJobLocation.getText().isEmpty() &&
                    !txtJobDate.getText().isEmpty();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\n");
        }
        if (valid) {
            job = jobController.addJob(
                    txtJobID.getText(),
                    txtJobTitle.getText(),
                    txtJobDesc.getText(),
                    txtShipName.getText(),
                    txtJobLocation.getText(),
                    txtJobDate.getText()
            );
            JOptionPane.showMessageDialog(frame, job.getmJobTitle() + " added successfully!");
            comboBoxJob.removeAllItems();
            for (Job job : jobController.getJobList()) {
                comboBoxJob.addItem(job.getmJobID() + " - " + job.getmJobTitle());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid inputs!");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AllocateView");
        frame.setContentPane(new AllocateView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
