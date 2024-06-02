package org.devdynamos;

import org.devdynamos.models.User;
import org.devdynamos.utils.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        try{
            List<User> res = DBManager.getAll( User.class,"users");
            for (User user : res){
                System.out.println(user.toString());
            }
        }catch (Exception ex){
            System.err.println(ex.toString());
        }
    }
}