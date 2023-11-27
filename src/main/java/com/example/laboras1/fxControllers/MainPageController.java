package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {


    @FXML
    public MenuItem allUsersItem;
    @FXML
    public MenuItem createUserItem;
    @FXML
    public MenuItem createBookItem;
    @FXML
    public TableView <Book> bookTable;
    @FXML
    public TableColumn <Book, Integer> idCol;
    @FXML
    public TableColumn <Book, String> nameCol;
    @FXML
    public TableColumn <Book, String> authorCol;
    @FXML
    public TableColumn <Book, Date> releaseCol;
    @FXML
    public TableColumn <Book, Integer> pagesCol;
    @FXML
    public TableColumn <Book, Integer> responsibleCol;
    @FXML
    public TableColumn <Book, Integer> takenByCol;
    @FXML
    public TextField idField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField authorField;
    @FXML
    public TextField pagesField;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    @FXML
    public DatePicker releasePicker;
    @FXML
    public Button lgButton;
    @FXML
    public MenuItem allCompaniesItem;
    @FXML
    public MenuItem currentUserAccount;
    @FXML
    public MenuItem takeBookItem;
    @FXML
    public MenuItem takenBooksItem;
    @FXML
    public MenuItem sectionBookItem;
    @FXML
    public MenuItem bookAccessItem;
    @FXML
    public TableColumn <Book, String> sectionCol;
    @FXML
    public TableColumn <Book, String> subSectionCol;
    @FXML
    public ComboBox <Section> sectionComboBox;
    @FXML
    public ComboBox <SubSection> subSectionComboBox;

    private Connection connection;
    private String condition;

    public void updateRecord(ActionEvent actionEvent){
        updateBook();
    }

    public void deleteRecord(ActionEvent actionEvent){ deleteBook();}


    public ObservableList <Book> getBookList(String condition){
        ObservableList <Book> bookList = FXCollections.observableArrayList();
        connection = DbUtils.connectToDB();
        String query = "SELECT distinct b.id, b.name, b.author, b.section, b.sub_section, b.release_date, b.number_of_pages, b.responsible, b.taken_by FROM Books b, Book_access ba " + condition;
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

    public void setCondition() {
        if (!CurrentUser.isIsAdmin()) {
            condition = " WHERE b.id = ba.book_id AND ba.user_id = " + CurrentUser.getId();
        } else {
            condition = "";
        }
    }


    public void showBooks(){

        ObservableList<Book>list = getBookList(condition);

        idCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthor"));
        sectionCol.setCellValueFactory(new PropertyValueFactory<Book, String>("section"));
        subSectionCol.setCellValueFactory(new PropertyValueFactory<Book, String>("subSection"));
        releaseCol.setCellValueFactory(new PropertyValueFactory<Book, Date>("releaseYear"));
        pagesCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numberOfPages"));
        responsibleCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("responsible"));
        takenByCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("takenBy"));

        bookTable.setItems(list);

    }

    private void updateBook(){

        if(idField.getText().isEmpty()){
            alertMessage("Book id is not specified!");
        }
        else if(nameField.getText().isEmpty() || authorField.getText().isEmpty() || sectionComboBox.getSelectionModel().isEmpty() || subSectionComboBox.getSelectionModel().isEmpty() || releasePicker.getValue() == null || pagesField.getText().isEmpty())
        {
            alertMessage("Data is missing, please fill all fields!");
        }
        else if(!pagesField.getText().matches("[0-9]+"))
        {
            alertMessage("Pages must be specified as number!");
        }
        else {
            String query = "UPDATE Books SET name = '" + nameField.getText() + "', author = '" + authorField.getText() + "', section = '" + sectionComboBox.getSelectionModel().getSelectedItem().getName() + "', sub_section = '" + subSectionComboBox.getSelectionModel().getSelectedItem().getName() +
                    "', release_date = '" + releasePicker.getValue() + "', number_of_pages = " + pagesField.getText() + " WHERE id = " + idField.getText() + "";
            System.out.println(query);
            executeQuery(query);
            showBooks();
        }
    }

    private void deleteBook(){
        if(idField.getText().isEmpty()){
            alertMessage("Book id is not specified!");
        }
        else{
            String query = "DELETE FROM Books WHERE id = " + idField.getText() + "";
            executeQuery(query);
            showBooks();
        }
    }

    private void executeQuery(String query){
        connection = DbUtils.connectToDB();
        try{
            PreparedStatement psInsert = connection.prepareStatement(query);
            psInsert.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent mouseEvent){
        Book book = bookTable.getSelectionModel().getSelectedItem();
        idField.setText("" + book.getBookId());
        nameField.setText("" + book.getBookName());
        authorField.setText("" + book.getBookAuthor());
        sectionComboBox.setValue(sectionComboBox.getItems().stream().filter(ap ->
                ap.getName().equals(book.getSection())).findFirst().orElse(null));
        subSectionComboBox.setValue(subSectionComboBox.getItems().stream().filter(ap ->
                ap.getName().equals(book.getSubSection())).findFirst().orElse(null));
        releasePicker.setValue(LocalDate.parse("" + book.getReleaseYear()));
        pagesField.setText("" + book.getNumberOfPages());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCondition();

        sectionComboBox.setItems(SectionController.getSectionList());
        sectionComboBox.setOnAction(actionEvent -> {
            if(sectionComboBox.getSelectionModel().getSelectedItem() != null) {
                subSectionComboBox.setItems(SectionController.getSubSectionList(Integer.parseInt(sectionComboBox.getSelectionModel().getSelectedItem().getId())));
            }
        });

        if(!CurrentUser.isIsAdmin()) {
            allUsersItem.setVisible(false);
            allCompaniesItem.setVisible(false);
            createUserItem.setVisible(false);
            responsibleCol.setVisible(false);
            takenByCol.setVisible(false);
        }

        showBooks();
    }


    public void createBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateBookForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }


    public void showAllUsers(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UsersWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void logOut(ActionEvent actionEvent) throws IOException{

        Parent part = FXMLLoader.load(HelloApplication.class.getResource("Log_in.fxml"));
        Stage stg = (Stage) lgButton.getScene().getWindow();
        stg.close();
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.show();

    }

    public void createUser(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public static void alertMessage(String a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Message: ");
        alert.setContentText(a);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void showAllCompanies(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CompaniesWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showCurrentUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PersonalInformation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void takeBook(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BooksToTake.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void takenBooks(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BooksToReturn.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void bookSection(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Section.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void bookAccess(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BookAccess.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
