package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.CustomerOrder;
import org.devdynamos.utils.Console;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.HashMap;

public class PaymentDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtPaid;
    private JTextField txtBalance;
    private JLabel lblTotal;
    private JLabel lblDiscount;
    private JLabel lblVat;
    private JLabel lblServiceCharge;
    private JLabel lblGrandTotal;

    private final CustomerOrder customerOrder;
    private final DialogPositiveCallback<CustomerOrder> positiveCallback;
    private final DialogNegativeCallback<CustomerOrder> negativeCallback;

    public PaymentDialog(CustomerOrder customerOrder, DialogPositiveCallback<CustomerOrder> positiveCallback, DialogNegativeCallback<CustomerOrder> negativeCallback) {
        this.customerOrder = customerOrder;
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

        txtPaid.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtPaid.getText().isEmpty()) return;
                checkInputValidity(txtPaid);
                updateBalancedAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBalancedAmount();
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

    private void updateBalancedAmount() {
        try{
            double balance = txtPaid.getText().isEmpty() ? customerOrder.getGrandTotal() : customerOrder.getGrandTotal() - Double.parseDouble(txtPaid.getText());
            balance = balance < 0 ? balance * (-1) : balance;
            balance = (double) Math.round(balance * 100) / 100;
            txtBalance.setText(String.valueOf(balance));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void populateFields() {
        double discountAmount = (double) Math.round((customerOrder.getTotal() * (customerOrder.getDiscountRate() / 100)) * 100) / 100;
        double vatAmount = (double) Math.round(((customerOrder.getTotal() - discountAmount) * (customerOrder.getVatRate() / 100) * 100)) / 100;
        double serviceChargeAmount = (double) Math.round(((customerOrder.getTotal() - discountAmount + vatAmount) * (customerOrder.getServiceChargeRate() / 100)) * 100) / 100;
        double total = (double) Math.round(customerOrder.getTotal() * 100) / 100;
        double grandTotal = (double) Math.round(customerOrder.getGrandTotal() * 100) / 100;

        lblTotal.setText("<html>" + total + "</html>");
        lblDiscount.setText("<html>-" + discountAmount + " (" + customerOrder.getDiscountRate() + "%)</html>");
        lblVat.setText("<html>+" + vatAmount + " (" + customerOrder.getVatRate() + "%)</html>");
        lblServiceCharge.setText("<html>+" + serviceChargeAmount + " (" + customerOrder.getServiceChargeRate() + "%)</html>");
        lblGrandTotal.setText("<html>" + grandTotal + "</html>");
        txtBalance.setText(String.valueOf(grandTotal));
    }

    private void onOK() {
        if(txtPaid.getText().isEmpty() || txtBalance.getText().isEmpty()){
            onCancel();
            return;
        }
        customerOrder.setPaidAmount(Double.parseDouble(txtPaid.getText()));
        customerOrder.setBalanceAmount(Double.parseDouble(txtBalance.getText()));

        positiveCallback.execute(this, customerOrder);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    public void showDialog() {
        setTitle("Confirm payment...");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
