package org.devdynamos.view;

import org.devdynamos.contollers.UsersController;
import org.devdynamos.interfaces.LoginRequestCallback;
import org.devdynamos.models.LoginRequestResultSet;
import org.devdynamos.models.User;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {
    private JPanel pnlRoot;
    private JButton btnLogin;
    private JTextField txtUserName;
    private JLabel lblGotoRegister;
    private JPasswordField txtPassword;

    private final RootView rootView;

    private final UsersController usersController = new UsersController();

    public Login(RootView rootView){
        this.rootView = rootView;
        rootView.setSize(new Dimension(300, 300));
        rootView.setResizable(false);

        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void initButtons() {
        lblGotoRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblGotoRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lblGotoRegister.setForeground(new Color(160, 32, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lblGotoRegister.setForeground(new Color(45, 45, 229));
                rootView.navigate(NavPath.REGISTER, new Register(rootView).getRootPanel());
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedLoginOperation();
            }
        });

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void proceedLoginOperation() {
        if(txtUserName.getText().isEmpty() ||  String.valueOf(txtPassword.getPassword()).isEmpty()){
            JOptionPane.showMessageDialog(null, "Please provide the required information to continue and try again later.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("<html>Logging in...</html>");

        User user = new User(txtUserName.getText(), String.valueOf(txtPassword.getPassword()));

        usersController.proceedLogin(user, new LoginRequestCallback<User>() {
            @Override
            public void onSuccess(LoginRequestResultSet<User> resultSet) {
                loadingSpinner.stop();

                rootView.setCurrentUser(resultSet.getAuthObject());
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
