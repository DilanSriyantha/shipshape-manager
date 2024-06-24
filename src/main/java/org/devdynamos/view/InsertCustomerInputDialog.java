package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Customer;
import org.devdynamos.utils.InputValidator;

import javax.swing.*;
import java.awt.event.*;

public class InsertCustomerInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtName;
    private JTextField txtContactNumber;
    private JTextField txtEmail;
    private final DialogPositiveCallback<Customer> positiveCallback;
    private final DialogNegativeCallback<Customer> negativeCallback;
    private final Customer customer;

    public InsertCustomerInputDialog(Customer customer, DialogPositiveCallback<Customer> positiveCallback, DialogNegativeCallback<Customer> negativeCallback) {
        this.customer = customer;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setupModal();
        initButtons();
        populateFields();
    }

    private void setupModal() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);
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

    private void populateFields() {
        txtName.setText(customer.getCustomerName());
        txtContactNumber.setText(customer.getContactNumber());
        txtEmail.setText(customer.getEmail());
    }

    private void onOK() {
        if(!areInputsValid()){
            dispose();
            return;
        }

        customer.setCustomerName(txtName.getText());
        customer.setContactNumber(txtContactNumber.getText());
        customer.setEmail(txtEmail.getText());

        positiveCallback.execute(this, customer);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    private boolean areInputsValid() {
        boolean validName = !txtName.getText().isEmpty();
        boolean validEmail = InputValidator.isValidEmail(txtEmail.getText());
        boolean validPhoneNumber = InputValidator.isValidPhoneNumber(txtContactNumber.getText());

        return validName && validEmail && validPhoneNumber;
    }

    public void showDialog() {
        this.setTitle("Insert Customer");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
