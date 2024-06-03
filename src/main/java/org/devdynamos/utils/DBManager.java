package org.devdynamos.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {
    private static Connection connection = null;

    public static void establishConnection(String host, int port, String database, String userName, String password){
        try{
            if(connection != null) return;

            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + userName + "&password=" + password);

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try{
            connection.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static ResultSet runQuery(String queryString) throws SQLException{
        if(connection == null){
            throw new SQLException();
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryString);

        return resultSet;
    }

    /*
    * in order to utilize this method to fetch all the records from a desired table,
    * you are required to pass a model class type as a generic parameter
    * and the model class should have a default constructor with empty parameters list
    * and scope.
    * You must call this method within a try catch block and this method may throw
    * mentioned exceptions.
    * */

    public static <T> List<T> getAll(Class<T> model, String table) throws SQLException, ReflectiveOperationException {
        List <T> resultsList = new ArrayList<>();

        ResultSet resultSet = runQuery("select * from " + table);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        Field[] fields = model.getDeclaredFields();

        while(resultSet.next()){
            T modelInstance = model.getDeclaredConstructor().newInstance();

            // ResultSet column count starting at 1
            for(int i = 1; i <= columnCount; i++){
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);

                for(Field field : fields){
                    if(field.getName().equalsIgnoreCase(columnName)){
                        field.setAccessible(true);
                        field.set(modelInstance, value);
                        break;
                    }
                }
            }

            resultsList.add(modelInstance);
        }

        return resultsList;
    }

    public static <T> List<T> get(Class<T> model, String table, String condition) throws SQLException, ReflectiveOperationException {
        List<T> resultsList = new ArrayList<>();

        ResultSet resultSet = runQuery("SELECT * FROM " + table + " WHERE " + condition);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        Field[] fields = model.getDeclaredFields();

        while(resultSet.next()){
            T modelInstance = model.getDeclaredConstructor().newInstance();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);

                for(Field field : fields){
                    if(field.getName().equalsIgnoreCase(columnName)){
                        field.setAccessible(true);
                        field.set(value, modelInstance);
                        break;
                    }
                }
            }

            resultsList.add(modelInstance);
        }

        return resultsList;
    }

    public static int insert(String table, Object[] values) throws SQLException {
        if(connection == null){
            throw new SQLException();
        }

        Statement statement = connection.createStatement();
        String _values = "";
        for (int i = 0; i < values.length; i++){
            if(values[i] instanceof String){
                _values += "\"" + values[i] + "\"";
            }else if(values[i] instanceof Integer){
                _values += values[i];
            }

            if(i != values.length - 1){
                _values += ",";
            }
        }
        final int result = statement.executeUpdate("insert into " + table + " values (" + _values + ")");

        return result;
    }

    public static int update(String table, HashMap<String, Object> newValues, String condition) throws SQLException {
        if(connection == null){
            throw new SQLException();
        }

        Statement statement = connection.createStatement();
        String values = "";
        Map.Entry<String, Object>[] entries = newValues.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < entries.length; i++) {
            values += entries[i].getKey() + "=";
            if(entries[i].getValue() instanceof String){
                values += "\"" + entries[i].getValue() + "\"";
            }else if(entries[i].getValue() instanceof Integer){
                values += entries[i].getValue();
            }else{
                values += entries[i].getValue();
            }

            if(i != entries.length - 1){
                values += ", ";
            }
        }
        System.out.println("update " + table + " set " + values + " where " + condition);
        final int result = statement.executeUpdate("update " + table + " set " + values + " where " + condition);

        return result;
    }

    public static int delete(String table, String condition) throws SQLException {
        if(connection == null){
            throw new SQLException();
        }

        Statement statement = connection.createStatement();
        final int result = statement.executeUpdate("delete from " + table + " WHERE " + condition);

        return result;
    }
}
