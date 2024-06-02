package org.devdynamos.utils;

import java.sql.*;

public class DBManager {
    private static Connection connection = null;

    public static Connection establishConnection(String host, int port, String database, String userName, String password){
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + userName + "&password=" + password);

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return connection;
    }

    private static ResultSet runQuery(String queryString) throws SQLException{
        try{
            if(connection == null){
                throw new SQLException();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString);

            return resultSet;
        }catch(SQLException ex){
            throw ex;
        }
    }

    public static ResultSet getAll(String table) throws SQLException {
        return runQuery("select * from " + table);
    }
}
