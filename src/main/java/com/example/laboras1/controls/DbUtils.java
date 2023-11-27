package com.example.laboras1.controls;

import javafx.scene.control.Alert;

import java.sql.*;

public class DbUtils {

    public static Connection connectToDB(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost/1_laboras";
            String USER = "root";
            String PASSWORD = "Krepsinis44+";
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

        } catch (SQLException | ClassNotFoundException g) {
            g.printStackTrace();
        }
        return conn;
    }

    public static void disconnectFromDB(Connection connection, Statement statment){

        try {
            if (connection != null && statment != null){
                connection.close();
                statment.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteUser(int id) {
        Connection connection = connectToDB();
        String sql = "DELETE FROM Users where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateField(String dbColName, String value, int userId) {
        Connection connection = connectToDB();
        String sql = "UPDATE Users SET " + dbColName + " = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCompanyField(String dbColName, String value, int userId) {
        Connection connection = connectToDB();
        String sql = "UPDATE Company SET " + dbColName + " = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCompany(int id) {
        Connection connection = connectToDB();
        String sql = "DELETE FROM Company where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void takeBook(int id, int currentUserId, String dbColName){
        Connection connection = connectToDB();
        String sql = "UPDATE Books SET " + dbColName + " = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentUserId);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(int id, String dbColName){
        Connection connection = connectToDB();
        String sql = "UPDATE Books SET " + dbColName + " = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeQuery(String query){
        Connection connection = connectToDB();
        try{
            PreparedStatement psInsert = connection.prepareStatement(query);
            psInsert.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void executeQ(String query) {
        Connection connection = connectToDB();
        try {
            connectToDB();
            PreparedStatement psInsert = connection.prepareStatement(query);
            psInsert.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnectFromDB(connection, connection.prepareStatement(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void insertUser(String name, String username, String password, int isAdmin) throws Exception{
        Connection connection = connectToDB();
        executeQuery("INSERT INTO users (name, username, password, is_admin) VALUES ('" + name + "', '" + username + "', '" + password + "', " + isAdmin + ")");
    }

    public static void updateUser(String name, String surname, String email, String position, String login, int id) {
        Connection connection = connectToDB();
        String sql = "UPDATE Users SET name = '" + name + "',  surname = '" + surname + "', email = '" + email + "', position = '" + position + "', login = '" + login + "' WHERE id = " + id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, email);
            preparedStatement.setString(6, position);
            preparedStatement.setString(7, login);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
