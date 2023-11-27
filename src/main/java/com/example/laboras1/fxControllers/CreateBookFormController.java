package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.Book;
import com.example.laboras1.dataStructure.CurrentUser;
import com.example.laboras1.dataStructure.Section;
import com.example.laboras1.dataStructure.SubSection;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.laboras1.fxControllers.MainPageController.alertMessage;

public class CreateBookFormController implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField authorField;
    @FXML
    public TextField pagesField;
    @FXML
    public Button createButton;
    @FXML
    public Button goBackButton;
    @FXML
    public ListView bookList;
    @FXML
    public DatePicker releasePicker;
    @FXML
    public ComboBox <Section> sectionComboBox;
    @FXML
    public ComboBox <SubSection> subSectionComboBox;

    private ArrayList<Book> createdBooks = new ArrayList<>();

    private Connection connection;
    private PreparedStatement preparedStatement;
    private int setTakenBy = 0;

    public void createBook(ActionEvent actionEvent) throws SQLException {

        if (nameField.getText().isEmpty() || authorField.getText().isEmpty() || sectionComboBox.getSelectionModel().isEmpty() || subSectionComboBox.getSelectionModel().isEmpty() || releasePicker.getValue() == null ||
                pagesField.getText().isEmpty()) {

            alertMessage("Some data is missing. Please fill all fields!");

        }
        else if(!pagesField.getText().matches("[0-9]+"))
        {
            alertMessage("Pages must be specified as number!");
        }
        else {

            alertMessage("Book was inserted!");

            Book book = new Book(nameField.getText(), authorField.getText(), sectionComboBox.getSelectionModel().getSelectedItem().getName(), subSectionComboBox.getSelectionModel().getSelectedItem().getName(), Date.valueOf(releasePicker.getValue()), Integer.parseInt(pagesField.getText()), setTakenBy);

            connection = DbUtils.connectToDB();

            int bookId = getNextId("Books");

            String sql1 = "INSERT INTO Books(id, name, author, section, sub_section, release_date, number_of_pages, responsible, taken_by) VALUES (?,?,?,?,?,?,?,?,?)";
            String sql2 = "INSERT INTO Book_access (book_id, user_id) VALUES (" + bookId + ", " + CurrentUser.getId() + ")";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getBookAuthor());
            preparedStatement.setString(4, book.getSection());
            preparedStatement.setString(5, book.getSubSection());
            preparedStatement.setDate(6, book.getReleaseYear());
            preparedStatement.setInt(7, book.getNumberOfPages());
            preparedStatement.setInt(8, CurrentUser.getId());
            preparedStatement.setInt(9, book.getTakenBy());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.execute();

            DbUtils.disconnectFromDB(connection, preparedStatement);

            nameField.clear();
            authorField.clear();
            sectionComboBox.getEditor().clear();
            subSectionComboBox.getEditor().clear();
            releasePicker.getEditor().clear();
            pagesField.clear();

            createdBooks.add(book);
            bookList.getItems().add(book);
            bookList.refresh();
        }
    }


    public static int getNextId(String tableName) {
        int value = 0;
        try {
            Connection connection = DbUtils.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) id FROM "+ tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getInt("id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void goBack(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        Parent root = fxmlLoader.load();
        Stage stg = (Stage) goBackButton.getScene().getWindow();
        stg.close();
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bookList.getItems().clear();
        bookList.getItems().addAll(createdBooks);
        sectionComboBox.setItems(SectionController.getSectionList());
        sectionComboBox.setOnAction(actionEvent -> {
            if(sectionComboBox.getSelectionModel().getSelectedItem() != null) {
                subSectionComboBox.setItems(SectionController.getSubSectionList(Integer.parseInt(sectionComboBox.getSelectionModel().getSelectedItem().getId())));
            }
        });
    }
}
