package org.devdynamos.contollers;

import org.devdynamos.interfaces.LoginRequestCallback;
import org.devdynamos.interfaces.RegisterRequestCallback;
import org.devdynamos.models.LoginRequestResultSet;
import org.devdynamos.models.User;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UsersController {
    public UsersController() {}

    public void proceedLogin(User user, LoginRequestCallback<User> callback) {
        LoginRequestResultSet<User> resultSet = new LoginRequestResultSet<>();
        AtomicReference<Throwable> exceptionRef = new AtomicReference<>(null);
        Thread proceedLogin = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<User> authUser = DBManager.get(User.class, "users", "name='" + user.getName() + "' and password='" + user.getPassword() + "';");

                    if(authUser.isEmpty()) {
                        throw new IllegalArgumentException("Invalid credentials. Try again with correct credentials");
                    }else {
                        resultSet.setAuthObject(authUser.getFirst());
                    }
                }catch (Exception ex){
                    exceptionRef.set(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    proceedLogin.join();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }

                Throwable loginThreadException = exceptionRef.get();
                if(loginThreadException == null)
                    callback.onSuccess(resultSet);
                else
                    callback.onFailed((Exception) loginThreadException);
            }
        });

        proceedLogin.start();
        waitingThread.start();
    }

    public void proceedRegister(User user, RegisterRequestCallback<User> callback){
        LoginRequestResultSet<User> resultSet = new LoginRequestResultSet<>();
        AtomicReference<Throwable> exceptionRef = new AtomicReference<>(null);
        Thread proceedRegister = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // check whether there is at least one admin user
                    List<User> adminUsers = DBManager.get(User.class, "users", "type=1");
                    if(user.getType() == 0) {
                        if(adminUsers.isEmpty())
                            throw new IllegalArgumentException("An Admin user has to be configured before creating Employee users.");

                        Object enteredAdminPassword = JOptionPane.showInputDialog(new JPasswordField(), "Enter admin password");
                        if(!adminUsers.getFirst().getPassword().equals(enteredAdminPassword))
                            throw new IllegalArgumentException("Admin password you've entered is not correct. Please try again later.");
                    }

                    if(user.getType() == 1 && !adminUsers.isEmpty()) {
                        throw new IllegalArgumentException("Only 1 Admin user can be existed in the system. Try creating Employee users instead");
                    }

                    int id = DBManager.insert("users", new String[] { "name", "password", "type" }, user.toObjectArray());
                    user.setId(id);
                    resultSet.setAuthObject(user);
                }catch (Exception ex){
                    exceptionRef.set(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    proceedRegister.join();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }

                Throwable registerThreadException = exceptionRef.get();
                if(registerThreadException == null)
                    callback.onSuccess(resultSet);
                else
                    callback.onFailed((Exception)registerThreadException);
            }
        });

        proceedRegister.start();
        waitingThread.start();
    }
}
