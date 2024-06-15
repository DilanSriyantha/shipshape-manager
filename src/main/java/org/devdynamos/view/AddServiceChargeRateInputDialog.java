package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AddServiceChargeRateInputDialog extends JDialog {
    private JPanel contentPane;
    private JTextField txtServiceCharge;
    private JButton btnConfirm;
    private JButton btnCancel;

    private final DialogPositiveCallback<Integer> positiveCallback;
    private final DialogNegativeCallback<Integer> negativeCallback;

    public AddServiceChargeRateInputDialog(DialogPositiveCallback<Integer> positiveCallback, DialogNegativeCallback<Integer> negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setupModalProperties();
        initButtons();
        initInputRestrictions();
    }

    private void setupModalProperties() {
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

        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke("enter"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void initInputRestrictions() {
        txtServiceCharge.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtServiceCharge.getText().isEmpty()) return;
                checkInputValidity(txtServiceCharge);
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

    private void onOK() {
        if(txtServiceCharge.getText().isEmpty()){
            dispose();
            return;
        }
        positiveCallback.execute(this, Integer.parseInt(txtServiceCharge.getText()));
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    public void showDialog() {
        setTitle("Enter the service charge rate...");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
