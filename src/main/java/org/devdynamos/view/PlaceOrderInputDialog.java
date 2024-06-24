package org.devdynamos.view;

import org.devdynamos.components.DatePicker;
import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.PlaceOrderInputResultSet;
import org.devdynamos.utils.Console;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;

public class PlaceOrderInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtQty;
    private JPanel pnlCalendarContainer;
    private DatePicker datePicker;

    private final DialogPositiveCallback<PlaceOrderInputResultSet> positiveCallback;
    private final DialogNegativeCallback<PlaceOrderInputResultSet> negativeCallback;

    public PlaceOrderInputDialog(DialogPositiveCallback<PlaceOrderInputResultSet> positiveCallback, DialogNegativeCallback<PlaceOrderInputResultSet> negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initDatePicker();
        initTextFieldRestrictions();
        initButtons();
    }

    private void initDatePicker() {
        datePicker = new DatePicker(new Date());
        pnlCalendarContainer.add(datePicker);
    }

    private void initButtons() {
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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

    private void initTextFieldRestrictions(){
        txtQty.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtQty.getText().isEmpty()) return;
                checkInputValidity(txtQty);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void checkInputValidity(JTextField field){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String text = field.getText();
                try{
                    Double.parseDouble(text);
                }catch (Exception ex){
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
        if(txtQty.getText().isEmpty()) return;
        positiveCallback.execute(
                this,
                new PlaceOrderInputResultSet(Integer.parseInt(txtQty.getText()), datePicker.getSelectedDateString())
        );
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    public void showDialog(){
        this.setTitle("Order Placement Details");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
