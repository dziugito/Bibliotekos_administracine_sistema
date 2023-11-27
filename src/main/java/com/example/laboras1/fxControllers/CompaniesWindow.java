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

public class CompaniesWindow implements Initializable {
    @FXML
    public TableView companiesTable;
    @FXML
    public TableColumn <CompanieTableParameters, Integer> idCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> nameCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> companyCodeCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> representativeCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> addressCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> phoneCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> loginCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> createdCol;
    @FXML
    public TableColumn <CompanieTableParameters, String> modifiedCol;
    @FXML
    public TableColumn <CompanieTableParameters, Void> actionCol;
    @FXML
    public Button goBackButton;

    private ObservableList<CompanieTableParameters> data = FXCollections.observableArrayList();

    private Connection connection;
    private Statement statement;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companiesTable.setEditable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        createdCol.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        modifiedCol.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(t-> {String newName = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(newName);
                    DbUtils.updateField("name", newName, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        companyCodeCol.setCellValueFactory(new PropertyValueFactory<>("companyCode"));
        companyCodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        companyCodeCol.setOnEditCommit(t-> {String newCompanyCode = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCompanyCode(newCompanyCode);
                    DbUtils.updateCompanyField("companyCode", newCompanyCode, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        representativeCol.setCellValueFactory(new PropertyValueFactory<>("representative"));
        representativeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        representativeCol.setOnEditCommit(t-> {String newRepresentative = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setRepresentative(newRepresentative);
                    DbUtils.updateCompanyField("representative", newRepresentative, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        addressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(t-> {String newAddress = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setAdress(newAddress);
                    DbUtils.updateCompanyField("address", newAddress, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(t-> {String newPhone = t.getNewValue();
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhoneNumber(newPhone);
                    DbUtils.updateCompanyField("phone_number", newPhone, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );
        loginCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loginCol.setOnEditCommit(
                t -> {t.getTableView().getItems().get(t.getTablePosition().getRow()).setUsername(t.getNewValue());
                    DbUtils.updateCompanyField("username", t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUsername(), t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId());
                }
        );

        Callback<TableColumn<CompanieTableParameters, Void>, TableCell<CompanieTableParameters, Void>> cellFactory = new Callback<TableColumn<CompanieTableParameters, Void>, TableCell<CompanieTableParameters, Void>>(){

            @Override
            public TableCell<CompanieTableParameters, Void> call(final TableColumn<CompanieTableParameters, Void> param){
                final TableCell<CompanieTableParameters, Void> cell = new TableCell<>(){
                    private final Button button = new Button("Delete");
                    {
                        button.setOnAction((ActionEvent event)->{CompanieTableParameters data = getTableView().getItems().get(getIndex());
                            DbUtils.deleteCompany(data.getId());
                            try {
                                loadAllCompanies();
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
            loadAllCompanies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadAllCompanies() throws SQLException {
        companiesTable.setEditable(true);
        companiesTable.getItems().clear();
        connection = DbUtils.connectToDB();
        String query = "SELECT * FROM Company u WHERE u.is_admin = false";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()){
            CompanieTableParameters companieTableParameters = new CompanieTableParameters();
            companieTableParameters.setId(rs.getInt(1));
            companieTableParameters.setName(rs.getString(2));
            companieTableParameters.setCompanyCode(rs.getString(3));
            companieTableParameters.setRepresentative(rs.getString(4));
            companieTableParameters.setAdress(rs.getString(5));
            companieTableParameters.setPhoneNumber(rs.getString(6));
            companieTableParameters.setUsername(rs.getString(7));
            companieTableParameters.setDateCreated(rs.getString(9));
            companieTableParameters.setDateModified(rs.getString(10));
            data.add(companieTableParameters);
        }
        companiesTable.setItems(data);
        DbUtils.disconnectFromDB(connection, statement);
    }
}

