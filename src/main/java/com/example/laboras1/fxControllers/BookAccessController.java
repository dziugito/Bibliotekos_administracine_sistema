package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.Book;
import com.example.laboras1.dataStructure.CurrentUser;
import com.example.laboras1.dataStructure.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookAccessController implements Initializable {
    @FXML
    public TableView <Book>bookTable;
    @FXML
    public TableColumn <Book, Integer> bookIdCol;
    @FXML
    public TableColumn <Book, String> bookNameCol;
    @FXML
    public TableView <Person> haveAccessTable;
    @FXML
    public TableColumn <Person, Integer> userIdCol;
    @FXML
    public TableColumn <Person, String> userNameCol;
    @FXML
    public TableView <Person> dontHaveAccessTable;
    @FXML
    public TableColumn <Person, Integer> userIdCol2;
    @FXML
    public TableColumn <Person, String> userNameCol2;
    @FXML
    public Button removeButon;
    @FXML
    public Button addButton;
    @FXML
    public Button goBackButton;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private String condition;

    public ObservableList<Book> getBookList(String condition){
        ObservableList <Book> bookList = FXCollections.observableArrayList();
        connection = DbUtils.connectToDB();
        String query = "SELECT * FROM Books " + condition;
        Statement st;
        ResultSet rs;

        try{
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Book book;
            while(rs.next()){
                book = new Book(rs.getInt("id"), rs.getString("name"), rs.getString("author"), rs.getString("section"), rs.getString("sub_section"), rs.getDate("release_date"), rs.getInt("number_of_pages"), rs.getInt("responsible"), rs.getInt("taken_by"));
                bookList.add(book);
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return bookList;
    }

    public void showBooks(){

        ObservableList<Book>list = getBookList(condition);

        bookIdCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));

        bookTable.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!CurrentUser.isIsAdmin())
        {
            condition = "WHERE responsible = " + CurrentUser.getId();
        }
        else
        {
            condition = "";
        }

        bookTable.setOnMouseClicked(event -> {
            if (bookTable.getSelectionModel().getSelectedItem() != null) {
                refreshUsersAccess(Integer.parseInt(String.valueOf(bookTable.getSelectionModel().getSelectedItem().getBookId())));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Choose book!");
                alert.show();
            }

        });

        showBooks();
    }

    public static ObservableList<Person> getUsersAccess(int bookId, String condition) {
        ObservableList<Person> userList = FXCollections.observableArrayList();
        try {
            Connection connection = DbUtils.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, login, password, surname  FROM Users WHERE id " + condition + " (SELECT user_id FROM Book_access WHERE book_id = " + bookId + ") AND is_admin = 0");
            ResultSet resultSet = preparedStatement.executeQuery();
            Person users;
            while (resultSet.next()) {
                users = new Person(resultSet.getInt("id"), resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("surname"));
                userList.add(users);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void refreshUsersAccess(int bookId) {
        ObservableList<Person> userListIn = getUsersAccess(bookId, "IN");
        ObservableList<Person> userListNotIn = getUsersAccess(bookId, "NOT IN");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        haveAccessTable.setItems(userListIn);
        userIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        dontHaveAccessTable.setItems(userListNotIn);
    }


    public void removeUser(ActionEvent actionEvent) throws SQLException {
        connection = DbUtils.connectToDB();
        int bookId = Integer.parseInt(String.valueOf(bookTable.getSelectionModel().getSelectedItem().getBookId()));
        if (bookTable.getSelectionModel().getSelectedItem().getResponsible() == Integer.parseInt(String.valueOf(haveAccessTable.getSelectionModel().getSelectedItem().getId()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Unable to remove access rights from owner item!");
            alert.show();
        } else {
            String sql = "DELETE FROM Book_access WHERE book_id = " + bookId + " AND user_id = " + haveAccessTable.getSelectionModel().getSelectedItem().getId();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            DbUtils.disconnectFromDB(connection, preparedStatement);
            refreshUsersAccess(bookId);
        }
    }

    public void addUser(ActionEvent actionEvent) throws SQLException {
        connection = DbUtils.connectToDB();
        int bookId = Integer.parseInt(String.valueOf(bookTable.getSelectionModel().getSelectedItem().getBookId()));
        String sql = "INSERT INTO Book_access (book_id, user_id) VALUES(" + bookId + ", " + dontHaveAccessTable.getSelectionModel().getSelectedItem().getId() + ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        DbUtils.disconnectFromDB(connection, preparedStatement);
        refreshUsersAccess(bookId);

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent part = FXMLLoader.load(HelloApplication.class.getResource("MainPage.fxml"));
        Stage stg = (Stage) goBackButton.getScene().getWindow();
        stg.close();
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.show();
    }
}
