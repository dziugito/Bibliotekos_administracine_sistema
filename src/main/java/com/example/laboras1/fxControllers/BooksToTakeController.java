package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.CurrentUser;
import com.example.laboras1.dataStructure.Section;
import com.example.laboras1.dataStructure.SubSection;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BooksToTakeController implements Initializable {
    @FXML
    public TableColumn <BooksParameters, String> nameCol;
    @FXML
    public TableColumn <BooksParameters, String> authorCol;
    @FXML
    public TableColumn <BooksParameters, String> dateCol;
    @FXML
    public TableColumn <BooksParameters, Integer> pagesCol;
    @FXML
    public TableColumn <BooksParameters, Void> actionCol;
    @FXML
    public TableView booksTable;
    @FXML
    public TableColumn <BooksParameters, Integer> responsibleCol;
    @FXML
    public Button goBackButton;
    @FXML
    public TableColumn <BooksParameters, String> sectionCol;
    @FXML
    public TableColumn <BooksParameters, String> subSectionCol;

    private ObservableList<BooksParameters> data = FXCollections.observableArrayList();

    private Connection connection;
    private Statement statement;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        booksTable.setEditable(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        sectionCol.setCellValueFactory(new PropertyValueFactory<>("section"));
        subSectionCol.setCellValueFactory(new PropertyValueFactory<>("subSection"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        pagesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
        responsibleCol.setCellValueFactory(new PropertyValueFactory<>("responsible"));

        Callback<TableColumn<BooksParameters, Void>, TableCell<BooksParameters, Void>> cellFactory = new Callback<TableColumn<BooksParameters, Void>, TableCell<BooksParameters, Void>>(){

            @Override
            public TableCell<BooksParameters, Void> call(final TableColumn<BooksParameters, Void> param){
                final TableCell<BooksParameters, Void> cell = new TableCell<>(){
                    private final Button button = new Button("Take");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            BooksParameters data = getTableView().getItems().get(getIndex());
                            DbUtils.takeBook(data.getId(), CurrentUser.getId(), "taken_by");
                            try {
                                loadAllBooks();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty) setGraphic(null);
                        else setGraphic(button);
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);

        try {
            loadAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllBooks() throws SQLException {

        booksTable.setEditable(true);
        booksTable.getItems().clear();
        connection = DbUtils.connectToDB();
        String query;
        query = "SELECT * FROM Books b WHERE b.taken_by = false";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()){
            BooksParameters booksToTakeParameters = new BooksParameters();
            booksToTakeParameters.setId(rs.getInt(1));
            booksToTakeParameters.setName(rs.getString(2));
            booksToTakeParameters.setAuthor(rs.getString(3));
            booksToTakeParameters.setSection(rs.getString(4));
            booksToTakeParameters.setSubSection(rs.getString(5));
            booksToTakeParameters.setReleaseDate(rs.getString(6));
            booksToTakeParameters.setNumberOfPages(rs.getInt(7));
            booksToTakeParameters.setResponsible(rs.getInt(8));
            //booksToTakeParameters.setTakenBy(rs.getInt(8));
            data.add(booksToTakeParameters);
        }
        booksTable.setItems(data);
        DbUtils.disconnectFromDB(connection, statement);
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
