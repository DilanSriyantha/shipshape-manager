package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.SparePart;
import org.devdynamos.models.Supplier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class InsertSparePartDialog extends JDialog {
    private JPanel contentPane;

    private JTextField txtName;
    private JTextField txtReceivedPrice;
    private JTextField txtSellingPrice;
    private JTextField txtQty;

    private JButton btnConfirm;
    private JButton btnCancel;

    private SparePart sparePart;
    private DialogPositiveCallback<SparePart> positiveCallback;
    private DialogNegativeCallback<SparePart> negativeCallback;
    private Supplier supplier;

    public InsertSparePartDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        initTextFieldRestrictions();
    }

    public InsertSparePartDialog(Supplier supplier, DialogPositiveCallback<SparePart> positiveCallback, DialogNegativeCallback<SparePart> negativeCallback) {
        this.supplier = supplier;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        initTextFieldRestrictions();
    }

    public InsertSparePartDialog(SparePart sparePart) {
        this.sparePart = sparePart;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        initTextFieldRestrictions();
        populateFields();
    }

    public InsertSparePartDialog(SparePart sparePart, DialogPositiveCallback<SparePart> positiveCallback, DialogNegativeCallback<SparePart> negativeCallback) {
        this.sparePart = sparePart;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);

        initButtons();
        initTextFieldRestrictions();
        populateFields();
    }

    private void populateFields() {
        txtName.setText(this.sparePart.getName());
        txtReceivedPrice.setText(String.valueOf(this.sparePart.getReceivedPrice()));
        txtSellingPrice.setText(String.valueOf(this.sparePart.getSellingPrice()));
        txtQty.setText(String.valueOf(this.sparePart.getCurrentQuantity()));
    }

    private void initTextFieldRestrictions() {
        txtReceivedPrice.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtReceivedPrice.getText().isEmpty()) return;
                checkInputValidity(txtReceivedPrice);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        txtSellingPrice.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtSellingPrice.getText().isEmpty()) return;
                checkInputValidity(txtSellingPrice);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

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
        if(this.sparePart == null){
            this.sparePart = new SparePart(
                    -1,
                    this.supplier.getSupplierId(),
                    this.supplier.getSupplierName(),
                    txtName.getText(), Double.parseDouble(txtReceivedPrice.getText()),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Integer.parseInt(txtQty.getText()),
                    Integer.parseInt(txtQty.getText()),
                    false,
                    false
            );
        }else{
            this.sparePart.setName(txtName.getText());
            this.sparePart.setReceivedPrice(Double.parseDouble(txtReceivedPrice.getText()));
            this.sparePart.setSellingPrice(Double.parseDouble(txtSellingPrice.getText()));
            this.sparePart.setCurrentQuantity(Integer.parseInt(txtQty.getText()));
        }

        this.positiveCallback.execute(this, this.sparePart);
    }

    private void onCancel() {
        this.negativeCallback.execute(this);
    }

    public void showDialog() {
        this.setTitle("Insert Spare Part");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void showDialog(SparePart sparePart){
        this.setTitle("Insert Spare Part");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
