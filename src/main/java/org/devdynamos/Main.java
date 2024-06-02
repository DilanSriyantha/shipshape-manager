package org.devdynamos;

import org.devdynamos.utils.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        try{
            ResultSet res = DBManager.getAll("users");
            if(res != null){
                while(res.next()){
                    System.out.println(res.getString(4));
                }
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}