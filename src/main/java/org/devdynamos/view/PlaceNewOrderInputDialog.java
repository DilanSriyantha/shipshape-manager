package org.devdynamos.view;

import org.devdynamos.components.DatePicker;
import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.models.Supplier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;

public class PlaceNewOrderInputDialog extends JDialog {
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtQty;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPanel pnlCalendarContainer;
    private DatePicker datePicker;

    private Supplier supplier;

    private final DialogPositiveCallback<HashMap<String, Object>> positiveCallback;
    private final DialogNegativeCallback<HashMap<String, Object>> negativeCallback;

    public PlaceNewOrderInputDialog(Supplier supplier, DialogPositiveCallback<HashMap<String, Object>> positiveCallback, DialogNegativeCallback<HashMap<String, Object>> negativeCallback) {
        this.supplier = supplier;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        initButtons();
        initDatePicker();
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

    private void initTextFieldRestrictions() {
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

    private void initDatePicker() {
        datePicker = new DatePicker(new Date());
        pnlCalendarContainer.add(datePicker);
    }

    private void onOK() {
        SparePart sparePart = new SparePart(
                -1,
                this.supplier.getSupplierId(),
                this.supplier.getSupplierName(),
                txtName.getText(),
                0.00,
                0.00,
                0,
                0,
                false,
                false
        );
        sparePart.setSupplierId(supplier.getSupplierId());
        sparePart.setSupplierName(supplier.getSupplierName());
        sparePart.setSupplierEmail(supplier.getEmail());

        positiveCallback.execute(this, new HashMap<>(){{
            put("sparePart", sparePart);
            put("expectedDate", datePicker.getSelectedDateString());
            put("requiredQuantity", Integer.parseInt(txtQty.getText()));
        }});
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);
        setTitle("New Order Placement Details");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
