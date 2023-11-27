package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UsersWindow implements Initializable {

    @FXML
    public TableView usersTable;
    @FXML
    public TableColumn <UserTableParameters, Integer> idCol;
    @FXML
    public TableColumn <UserTableParameters, String> nameCol;
    @FXML
    public TableColumn <UserTableParameters, String>surnameCol;
    @FXML
    public TableColumn <UserTableParameters, String>emailCol;
    @FXML
    public TableColumn <UserTableParameters, String>birthCol;
    @FXML
    public TableColumn <UserTableParameters, String>positionCol;
    @FXML
    public TableColumn <UserTableParameters, String>loginCol;
    @FXML
    public TableColumn <UserTableParameters, String>createdCol;
    @FXML
    public TableColumn <UserTableParameters, String>modifiedCol;
    @FXML
    public TableColumn <UserTableParameters, Void>actionCol;
    @FXML
    public Button goBackButton;

    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

    private Connection connection;
    private Statement statement;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usersTable.setEditable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        createdCol.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        modifiedCol.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(t-> {String newName = t.getNewValue();
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(newName);
        DbUtils.updateField("name", newName, t.getTableView().getItems().get(
                t.getTablePosition().getRow()).getId());
        }
        );
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setOnEditCommit(t-> {String newSurname = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setSurname(newSurname);
                    DbUtils.updateField("surname", newSurname, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(t-> {String newEmail = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(newEmail);
                    DbUtils.updateField("email", newEmail, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        positionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        positionCol.setOnEditCommit(t-> {String newPosition = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPosition(newPosition);
                    DbUtils.updateField("position", newPosition, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loginCol.setOnEditCommit(
                t -> {t.getTableView().getItems().get(t.getTablePosition().getRow()).setLogin(t.getNewValue());
                    DbUtils.updateField("login", t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getLogin(), t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );

        Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> cellFactory = new Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>>(){

            @Override
            public TableCell<UserTableParameters, Void> call(final TableColumn<UserTableParameters, Void> param){
                final TableCell<UserTableParameters, Void> cell = new TableCell<>(){
                    private final Button button = new Button("Delete");
                    {
                        button.setOnAction((ActionEvent event)->{UserTableParameters data = getTableView().getItems().get(getIndex());
                            DbUtils.deleteUser(data.getId());
                            try {
                                loadAllUsers();
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
            loadAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllUsers() throws SQLException {
        usersTable.setEditable(true);
        usersTable.getItems().clear();
        connection = DbUtils.connectToDB();
        String query = "SELECT * FROM Users u WHERE u.is_admin = false";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()){
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setId(rs.getInt(1));
            userTableParameters.setName(rs.getString(2));
            userTableParameters.setSurname(rs.getString(3));
            userTableParameters.setEmail(rs.getString(4));
            userTableParameters.setBirthDate(rs.getString(5));
            userTableParameters.setPosition(rs.getString(6));
            userTableParameters.setLogin(rs.getString(7));
            userTableParameters.setDateCreated(rs.getString(9));
            userTableParameters.setDateModified(rs.getString(10));
            data.add(userTableParameters);
        }
        usersTable.setItems(data);
        DbUtils.disconnectFromDB(connection, statement);
    }

    public void goBackAction(ActionEvent actionEvent) throws IOException {
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
