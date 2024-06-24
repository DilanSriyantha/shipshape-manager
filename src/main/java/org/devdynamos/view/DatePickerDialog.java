package org.devdynamos.view;

import org.devdynamos.components.DatePicker;
import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class DatePickerDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPanel pnlCalendarContainer;
    private DatePicker datePicker;

    private final DialogPositiveCallback<Date> positiveCallback;
    private final DialogNegativeCallback<Date> negativeCallback;

    public DatePickerDialog(DialogPositiveCallback<Date> positiveCallback, DialogNegativeCallback<Date> negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initDatePicker();
        initButtons();
    }

    private void initDatePicker() {
        datePicker = new DatePicker(new Date());
        pnlCalendarContainer.add(datePicker);
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
        positiveCallback.execute(this, datePicker.getSelectedDate());
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
