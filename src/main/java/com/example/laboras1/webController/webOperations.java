package com.example.laboras1.webController;

import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.Book;
import com.example.laboras1.dataStructure.Person;
import com.example.laboras1.serializes.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class webOperations {

    public static List<Book> getBooks() {
        Connection connection = DbUtils.connectToDB();
        List<Book> books = new ArrayList<>();
        Book book;
        try {
            String sql = "SELECT * FROM Books";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)
,                        resultSet.getString(5), resultSet.getDate(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static Book getBookById(int id) {
        Connection connection = DbUtils.connectToDB();
        Book book = null;
        try {
            String sql = "SELECT * FROM Books WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)
                        ,                        resultSet.getString(5), resultSet.getDate(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    public static boolean deleteBook(int id) {
        Connection connection = DbUtils.connectToDB();
        String sql = "DELETE FROM Books where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            DbUtils.disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String insertBook(String name, String author, String description, String releaseYear, int numberOfpages) {
        Connection connection = DbUtils.connectToDB();

        try {
            String sql = "INSERT INTO Books (`name`, `author`, section, release_date, number_of_pages) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, description);
            preparedStatement.setDate(4, Date.valueOf(releaseYear));
            preparedStatement.setInt(5, numberOfpages);
            preparedStatement.execute();

            String selectBook = "SELECT LAST_INSERT_ID() as id;";
            preparedStatement = connection.prepareStatement(selectBook);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String updateBook(String name, String author, String section, String subSection, String releaseYear, int numberOfpages, int id) {
        Connection connection = DbUtils.connectToDB();

        try {
            String sql = "UPDATE Books SET `name` = ?, `author` = ?, `section` = ?, `sub_section` = ?, `release_date` = ?, `number_of_pages` = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, section);
            preparedStatement.setString(4, subSection);
            preparedStatement.setDate(5, Date.valueOf(releaseYear));
            preparedStatement.setInt(6, numberOfpages);
            preparedStatement.setInt(7, id);
            preparedStatement.execute();

            String selectBook = "SELECT LAST_INSERT_ID() as id;";
            preparedStatement = connection.prepareStatement(selectBook);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Users> getUsers() {
        Connection connection = DbUtils.connectToDB();
        List<Users> users = new ArrayList<>();
        Users user;
        try {
            String sql = "SELECT * FROM Users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new Users(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getDate(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                        resultSet.getDate(9), resultSet.getDate(10), resultSet.getBoolean(11));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static Users getUserById(int id) {
        Connection connection = DbUtils.connectToDB();
        Users user = null;
        try {
            String sql = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new Users(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getDate(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                        resultSet.getDate(9), resultSet.getDate(10), resultSet.getBoolean(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void deleteUser(int id) {
        Connection connection = DbUtils.connectToDB();
        String sql = "DELETE FROM Users where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            DbUtils.disconnectFromDB(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String createUser(String name, String surname, String email, String birthDate, String position, String login, String password, String dateCreated, String dateModified, Boolean isActive) {
        Connection connection = DbUtils.connectToDB();

        try {
            String insertUser = "INSERT INTO Users (`name`, `surname`, `email`, birth_date, `position`, `login`, `password`, date_created, date_modified, is_admin ) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, Date.valueOf(birthDate));
            preparedStatement.setString(5, position);
            preparedStatement.setString(6, login);
            preparedStatement.setString(7, password);
            preparedStatement.setDate(8, Date.valueOf(dateCreated));
            preparedStatement.setDate(9, Date.valueOf(dateModified));
            preparedStatement.setBoolean(10, isActive);
            preparedStatement.execute();

            String selectUser = "SELECT LAST_INSERT_ID() as id;";
            preparedStatement = connection.prepareStatement(selectUser);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String updateUser(String name, String surname, String email, String birthDate, String position, String login, String dateModified, Boolean isActive, int id) {
        Connection connection = DbUtils.connectToDB();

        try {
            String insertUser = "UPDATE Users SET `name` = ?, `surname` = ?, `email` = ?, `birth_date` = ?, `position` = ?, `login` = ?, `date_modified` = ?, is_admin = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, Date.valueOf(birthDate));
            preparedStatement.setString(5, position);
            preparedStatement.setString(6, login);
            preparedStatement.setDate(7, Date.valueOf(dateModified));
            preparedStatement.setBoolean(8, isActive);
            preparedStatement.setInt(9, id);
            preparedStatement.execute();

            String selectUser = "SELECT LAST_INSERT_ID() as id;";
            preparedStatement = connection.prepareStatement(selectUser);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String validateByCredentials(String username, String password){
        Connection connection = DbUtils.connectToDB();
        String currentUser = null;
        try {
            String query = "SELECT * FROM Users WHERE login = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                currentUser = (rs.getString("login"));
            }
            DbUtils.disconnectFromDB(connection, preparedStatement);

            if (currentUser != null) {
                return currentUser;
            }

            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

    public static Person validateByCredential(String login, String password) {
        Connection connection = DbUtils.connectToDB();
        Person currentUser = null;
        try {
            String selectUser = "SELECT * FROM Users AS u WHERE u.login = ? AND u.password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                currentUser = new Person(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"), rs.getString("surname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    }


