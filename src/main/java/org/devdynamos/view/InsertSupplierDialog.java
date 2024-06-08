package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Supplier;

import javax.swing.*;
import java.awt.event.*;

public class InsertSupplierDialog extends JDialog {
    private JPanel contentPane;

    private JTextField txtName;
    private JTextField txtContactNumber;
    private JTextField txtEmail;

    private JButton btnConfirm;
    private JButton btnCancel;

    private Supplier supplier;
    private DialogPositiveCallback<Supplier> positiveCallback;
    private DialogNegativeCallback<Supplier> negativeCallback;

    public InsertSupplierDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
    }

    public InsertSupplierDialog(DialogPositiveCallback<Supplier> positiveCallback, DialogNegativeCallback<Supplier> negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
    }

    public InsertSupplierDialog(Supplier supplier) {
        this.supplier = supplier;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        populateFields();
    }

    public InsertSupplierDialog(Supplier supplier, DialogPositiveCallback<Supplier> positiveCallback, DialogNegativeCallback<Supplier> negativeCallback) {
        this.supplier = supplier;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        populateFields();
    }

    private void populateFields() {
        txtName.setText(this.supplier.getSupplierName());
        txtContactNumber.setText(this.supplier.getContactNumber());
        txtEmail.setText(this.supplier.getEmail());
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
    }

    private void onOK() {
        if(this.supplier != null){
            this.supplier.setSupplierName(txtName.getText());
            this.supplier.setContactNumber(txtContactNumber.getText());
            this.supplier.setEmail(txtEmail.getText());
        }else{
            this.supplier = new Supplier(-1, txtName.getText(), txtContactNumber.getText(), txtEmail.getText());
        }

        this.positiveCallback.execute(this, this.supplier);
    }

    private void onCancel() {
        this.negativeCallback.execute(this);
    }

    public void showDialog() {
        this.setTitle("Insert Supplier");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
