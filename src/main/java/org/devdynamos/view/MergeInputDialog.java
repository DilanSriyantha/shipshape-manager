package org.devdynamos.view;

import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MergeInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JTextField txtReceivedPrice;
    private JTextField txtSellingPrice;
    private JLabel lblProductName;
    private JLabel lblOrderCaption;
    private JLabel lblWarningIcon;
    private JLabel lblWarningContent;
    private Order order;

    private DialogPositiveCallback<Map<String, Object>> positiveCallback;
    private DialogNegativeCallback<Map<String, Object>> negativeCallback;

    public MergeInputDialog() {
        initButtons();
        setupModalProperties();
    }

    public MergeInputDialog(Order order, DialogPositiveCallback<Map<String, Object>> positiveCallback, DialogNegativeCallback<Map<String, Object>> negativeCallback) {
        this.order = order;
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;

        initButtons();
        initTextFields();
        initOrderDetailsLabels();
        initWarning();
        setupModalProperties();
    }

    private void setupModalProperties() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnConfirm);
        setTitle("Confirm merge");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void initButtons(){
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

    private void initTextFields() {
        if(order == null) return;

        txtReceivedPrice.setText(String.valueOf(order.getReceivedPrice()));
        txtSellingPrice.setText(String.valueOf(order.getSellingPrice()));
    }

    private void initOrderDetailsLabels() {
        if(order == null) return;

        lblProductName.setText("<html>" + order.getPartName() + " (ID: " + order.getSparePartId() + ")</html>");
        lblOrderCaption.setText("<html>" + order.getOrderCaption() + "</html>");
    }

    private void initWarning() {
        lblWarningIcon.setIcon(AssetsManager.getImageIcon("WarningIcon"));
        lblWarningContent.setText("<html>If this new stock has arrived with a different price, please enter the latest prices before confirming the merge process.</html>");
    }

    private void onOK() {
        final double newReceivedPrice = Double.parseDouble(txtReceivedPrice.getText());
        final double newSellingPrice = Double.parseDouble(txtSellingPrice.getText());
        final boolean priceIsZero = order.getSellingPrice() == 0 && order.getReceivedPrice() == 0;
        final boolean priceChanged = !priceIsZero && (order.getReceivedPrice() != newReceivedPrice || order.getSellingPrice() != newSellingPrice);
        final int currentQty = priceChanged ? order.getQuantity() : (order.getCurrentQuantity() + order.getQuantity());
        final int initialQty = priceChanged ? order.getQuantity() : (order.getCurrentQuantity() + order.getQuantity());

        SparePart sparePart = new SparePart(
                order.getSparePartId(),
                order.getSupplierId(),
                order.getSupplierName(),
                order.getPartName(),
                newReceivedPrice,
                newSellingPrice,
                currentQty,
                initialQty,
                false,
                false
        );

        Map<String, Object> returnValues = new HashMap<>() {{
            put("priceChanged", priceChanged);
            put("product", sparePart);
            put("order", order);
        }};

        positiveCallback.execute(this, returnValues);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    public void showDialog() {
        revalidate();
        repaint();
        setVisible(true);
    }
}
