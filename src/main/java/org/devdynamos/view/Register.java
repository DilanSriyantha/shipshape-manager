package org.devdynamos.view;

import org.devdynamos.contollers.UsersController;
import org.devdynamos.interfaces.RegisterRequestCallback;
import org.devdynamos.models.LoginRequestResultSet;
import org.devdynamos.models.User;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Register {
    private JPanel pnlRoot;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JComboBox cmbType;
    private JLabel lblGotoLogin;

    private final RootView rootView;

    private final UsersController usersController = new UsersController();

    public Register(RootView rootView){
        this.rootView = rootView;
        rootView.setSize(new Dimension(300, 300));
        rootView.setResizable(false);

        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void initButtons() {
        lblGotoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblGotoLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblGotoLogin.setForeground(new Color(160, 32, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lblGotoLogin.setForeground(new Color(45, 45, 229));
                rootView.navigate(NavPath.LOGIN, new Login(rootView).getRootPanel());
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedRegister();
            }
        });

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRegister.doClick();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void proceedRegister() {
        if(txtUserName.getText().isEmpty() ||  String.valueOf(txtPassword.getPassword()).isEmpty()){
            JOptionPane.showMessageDialog(null, "Please provide the required information to continue and try again later.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Please wait...");

        User user = new User(txtUserName.getText(), String.valueOf(txtPassword.getPassword()), cmbType.getSelectedIndex());

        usersController.proceedRegister(user, new RegisterRequestCallback<User>() {
            @Override
            public void onSuccess(LoginRequestResultSet<User> authObject) {
                loadingSpinner.stop();

                rootView.setCurrentUser(user);
                rootView.navigate(NavPath.HOME, new HomeView(rootView).getRootPanel());
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
