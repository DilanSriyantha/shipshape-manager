package org.devdynamos.utils;

import org.devdynamos.interfaces.DBConnectionListener;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DBManager {
    private static Connection connection = null;
    private static final List<DBConnectionListener> connectionListeners = new ArrayList<>();

    public static void establishConnection(String host, int port, String database, String userName, String password){
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + userName + "&password=" + password);

            notifyConnectionListeners();
        }catch (SQLException ex) {
            Console.log(ex.getMessage());

            terminateConnection();
            notifyConnectionListeners();
        }
    }

    private static void terminateConnection(){
        connection = null;

        notifyConnectionListeners();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void addConnectionListener(DBConnectionListener connectionListener) {
        connectionListeners.add(connectionListener);
    }

    public static void removeConnectionListener(DBConnectionListener connectionListener){
        connectionListeners.remove(connectionListener);
    }

    private static void notifyConnectionListeners() {
        for(DBConnectionListener connectionListener : connectionListeners){
            if(connection != null)
                connectionListener.onConnect();
            else
                connectionListener.onDisconnect();
        }
    }

    public static void closeConnection() {
        try{
            connection.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static ResultSet runQuery(String queryString) throws SQLException{
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

        String query = "select * from " + table;

        try(ResultSet resultSet = runQuery(query)){
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
        }

        return resultsList;
    }

    public static <T> List<T> executeQuery(Class<T> model, String query) throws SQLException, ReflectiveOperationException {
        List <T> resultsList = new ArrayList<>();

        try(ResultSet resultSet = runQuery(query)){
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Field[] fields = model.getDeclaredFields();

            while(resultSet.next()){
                T modelInstance = model.getDeclaredConstructor().newInstance();

                // ResultSet column count starting at 1
                for(int i = 1; i <= columnCount; i++){
                    String columnName = metaData.getColumnName(i);
                    String columnLabel = metaData.getColumnLabel(i);
                    Object value = resultSet.getObject(i);

                    for(Field field : fields){
                        if(field.getName().equalsIgnoreCase(columnName) || field.getName().equalsIgnoreCase(columnLabel)){ // this statement checks whether the field name equals either column name or label;
                            field.setAccessible(true);
                            field.set(modelInstance, value);
                            break;
                        }
                    }
                }

                resultsList.add(modelInstance);
            }
        }

        return resultsList;
    }

    public static <T> List<T> get(Class<T> model, String table, String condition){
        List<T> resultsList = new ArrayList<>();

        String query = "select * from " + table + " where " + condition;
        try(ResultSet resultSet = runQuery(query)){
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
                            field.set(modelInstance, value);
                            break;
                        }
                    }
                }

                resultsList.add(modelInstance);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return resultsList;
    }

    public static int insert(String table, String[] columns, Object[] values) {
        try{
            if(connection == null) throw new SQLException("Connection is null");

            String query = "insert into " + table + " (" + String.join(",", columns) + ") values (" + String.join(",", ArrayUtils.map(columns, (element) -> {
                return "?";
            })) + ")";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < values.length; i++) {
                if(values[i] instanceof Boolean){
                    preparedStatement.setBoolean((i+1), (boolean)values[i]);
                }else if(values[i] instanceof Integer){
                    preparedStatement.setInt((i+1), (int)(values[i]));
                }else if(values[i] instanceof Short){
                    preparedStatement.setShort((i+1), (short)values[i]);
                }else if(values[i] instanceof Long){
                    preparedStatement.setLong((i+1), (long)values[i]);
                }else if(values[i] instanceof Float){
                    preparedStatement.setFloat((i+1), (float)values[i]);
                }else if(values[i] instanceof Double){
                    preparedStatement.setDouble((i+1), (double)values[i]);
                }else if(values[i] instanceof String){
                    preparedStatement.setString((i+1), (String)values[i]);
                }
            }

            preparedStatement.addBatch();

            int id = -1;

            // execute insertion
            final int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                    if(rs.next()){
                        id = rs.getInt(1);
                    }
                }
            }

            return id;
        }catch (Exception ex){
            Console.log(ex.getMessage());

            return -1;
        }
    }

    public static int replace(String table, String[] columns, Object[] values) {
        try{
            if(connection == null) throw new SQLException("Connection is null");

            String query = "replace into " + table + " (" + String.join(",", columns) + ") values (" + String.join(",", ArrayUtils.map(columns, (element) -> {
                return "?";
            })) + ")";

            Console.log(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < values.length; i++) {
                if(values[i] instanceof Boolean){
                    preparedStatement.setBoolean((i+1), (boolean)values[i]);
                }else if(values[i] instanceof Integer){
                    preparedStatement.setInt((i+1), (int)(values[i]));
                }else if(values[i] instanceof Short){
                    preparedStatement.setShort((i+1), (short)values[i]);
                }else if(values[i] instanceof Long){
                    preparedStatement.setLong((i+1), (long)values[i]);
                }else if(values[i] instanceof Float){
                    preparedStatement.setFloat((i+1), (float)values[i]);
                }else if(values[i] instanceof Double){
                    preparedStatement.setDouble((i+1), (double)values[i]);
                }else if(values[i] instanceof String){
                    preparedStatement.setString((i+1), (String)values[i]);
                }
            }

            preparedStatement.addBatch();

            int id = -1;

            // execute insertion
            final int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                    if(rs.next()){
                        id = rs.getInt(1);
                    }
                }
            }

            Console.log("replaced");
            return id;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }


    public static int insertOrReplace(String table, String[] columns, Object[] values, String keyViolationStatement) {
        try{
            if(connection == null) throw new SQLException("Connection is null");

            String query = "insert into " + table + " (" + String.join(",", columns) + ") values (" + String.join(",", ArrayUtils.map(columns, (element) -> {
                return "?";
            })) + ") " + keyViolationStatement;

            Console.log(query);

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < values.length; i++) {
                if(values[i] instanceof Boolean){
                    preparedStatement.setBoolean((i+1), (boolean)values[i]);
                }else if(values[i] instanceof Integer){
                    preparedStatement.setInt((i+1), (int)(values[i]));
                }else if(values[i] instanceof Short){
                    preparedStatement.setShort((i+1), (short)values[i]);
                }else if(values[i] instanceof Long){
                    preparedStatement.setLong((i+1), (long)values[i]);
                }else if(values[i] instanceof Float){
                    preparedStatement.setFloat((i+1), (float)values[i]);
                }else if(values[i] instanceof Double){
                    preparedStatement.setDouble((i+1), (double)values[i]);
                }else if(values[i] instanceof String){
                    preparedStatement.setString((i+1), (String)values[i]);
                }
            }

            preparedStatement.addBatch();

            int id = -1;

            // execute insertion
            final int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                    if(rs.next()){
                        id = rs.getInt(1);
                    }
                }
            }

            Console.log("replaced");
            return id;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public static void setAutoCommit(boolean status){
        try {
            connection.setAutoCommit(status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertBatch(String table, String[] columns, Object[][] values) {
        try{
            if(connection == null){
                throw new SQLException();
            }

            String query = "insert into " + table + "(" + String.join(",", columns) + ")" + " values (" + String.join(",", ArrayUtils.map(columns, (element) -> {
                return "?";
            })) + ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values[i].length; j++) {
                    if(values[i][j] instanceof Boolean){
                        preparedStatement.setBoolean((j+1), (boolean)values[i][j]);
                    }else if(values[i][j] instanceof Integer){
                        preparedStatement.setInt((j+1), (int)(values[i][j]));
                    }else if(values[i][j] instanceof Short){
                        preparedStatement.setShort((j+1), (short)values[i][j]);
                    }else if(values[i][j] instanceof Long){
                        preparedStatement.setLong((j+1), (long)values[i][j]);
                    }else if(values[i][j] instanceof Float){
                        preparedStatement.setFloat((j+1), (float)values[i][j]);
                    }else if(values[i][j] instanceof Double){
                        preparedStatement.setDouble((j+1), (double)values[i][j]);
                    }else if(values[i][j] instanceof String){
                        preparedStatement.setString((j+1), (String)values[i][j]);
                    }
                }
                preparedStatement.addBatch();

                // execute insertion once for every 1000 times. This might not be triggered in most cases.
                if(i % 1000 == 0){
                    preparedStatement.executeBatch();
                }
            }

            // execute batch insertion
            preparedStatement.executeBatch();

            // commit transaction
            if(!connection.getAutoCommit()){
                connection.commit();
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            ex.printStackTrace();

            try{
                connection.rollback();
                connection.setAutoCommit(true);
            }catch (SQLException ex2){
                ex.printStackTrace();
            }
        }
    }

    public static int update(String table, HashMap<String, Object> newValues, String condition) {
        try{
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
            final int affectedRows = statement.executeUpdate("update " + table + " set " + values + " where " + condition);

            return affectedRows;
        }catch (Exception ex){
            ex.printStackTrace();

            return -1;
        }
    }

    public static void executeCustomQuery(String query){
        try{
            if(connection == null){
                throw new SQLException();
            }

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void delete(String table, String condition) {
        try{
            if(connection == null){
                throw new SQLException();
            }

            Statement statement = connection.createStatement();
            String query = "delete from " + table + " where " + condition + ";";
            Console.log(query);
            statement.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
