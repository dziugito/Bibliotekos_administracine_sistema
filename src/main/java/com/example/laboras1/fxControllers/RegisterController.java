package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.Company;
import com.example.laboras1.dataStructure.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController implements Initializable {

    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker birthPicker;
    @FXML
    public TextField positionField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button submitButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Label registerInfo;
    @FXML
    public TextField companyNameF;
    @FXML
    public TextField companyCodeF;
    @FXML
    public TextField representativeF;
    @FXML
    public TextField addressF;
    @FXML
    public TextField phoneNumberF;
    @FXML
    public TextField companyUsernameF;
    @FXML
    public TextField companyPasswordF;
    @FXML
    public RadioButton personRadio;
    @FXML
    public RadioButton companyRadio;
    @FXML
    public ToggleGroup userType;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public void submitAction(ActionEvent actionEvent) throws IOException, SQLException {

            if(personRadio.isSelected()){

                if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || emailField.getText().isEmpty() || birthPicker.getValue() == null ||
                        positionField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {

                    registerInfo.setText("Some data is missing. Please fill all fields!");
                }
                else{
                    Person person = new Person(nameField.getText(), surnameField.getText(), emailField.getText(), birthPicker.getValue(),
                            positionField.getText(), loginField.getText(), passwordField.getText());

                    connection = DbUtils.connectToDB();

                    String insertString = "INSERT INTO Users(name, surname, email, birth_date, position, login, password, date_created, date_modified, is_admin) VALUES (?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertString);
                    preparedStatement.setString(1, person.getName());
                    preparedStatement.setString(2, person.getSurname());
                    preparedStatement.setString(3, person.getEmail());
                    preparedStatement.setDate(4, Date.valueOf(person.getBirthDate()));
                    preparedStatement.setString(5, person.getPosition());
                    preparedStatement.setString(6, person.getLogin());
                    preparedStatement.setString(7, person.getPassword());
                    preparedStatement.setDate(8, Date.valueOf(person.getDateCreated()));
                    preparedStatement.setDate(9, Date.valueOf(person.getDateModified()));
                    preparedStatement.setBoolean(10, person.isAdmin());
                    preparedStatement.execute();

                    DbUtils.disconnectFromDB(connection, preparedStatement);

                    registerInfo.setText("User was created!");
                    nameField.clear();
                    surnameField.clear();
                    emailField.clear();
                    birthPicker.getEditor().clear();
                    positionField.clear();
                    loginField.clear();
                    passwordField.clear();
                }

        }
        else if(companyRadio.isSelected()){

                if (companyNameF.getText().isEmpty() || companyCodeF.getText().isEmpty() || representativeF.getText().isEmpty() || addressF.getText().isEmpty() ||
                        phoneNumberF.getText().isEmpty() || companyUsernameF.getText().isEmpty() || companyPasswordF.getText().isEmpty()) {

                    registerInfo.setText("Some data is missing. Please fill all fields!");
                }
                else if(!companyCodeF.getText().matches("[0-9]+"))
                {
                    registerInfo.setText("Company code must be specified as number!");
                }
                else if(!phoneNumberF.getText().matches("[0-9]+"))
                {
                    registerInfo.setText("Company phone number must be specified as number!");
                }

            Company company = new Company(companyUsernameF.getText(), companyPasswordF.getText(), companyNameF.getText(), Integer.parseInt(companyCodeF.getText()), representativeF.getText(), addressF.getText(),
                    Integer.parseInt(phoneNumberF.getText()));

            connection = DbUtils.connectToDB();

            String insertString = "INSERT INTO Company(name, company_code, representative, address, phone_number, username, password, date_created, date_modified, is_admin) VALUES (?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getCode());
            preparedStatement.setString(3, company.getRepresentative());
            preparedStatement.setString(4, company.getAddress());
            preparedStatement.setInt(5, company.getPhoneNumber());
            preparedStatement.setString(6, company.getLogin());
            preparedStatement.setString(7, company.getPassword());
            preparedStatement.setDate(8, Date.valueOf(company.getDateCreated()));
            preparedStatement.setDate(9, Date.valueOf(company.getDateModified()));
            preparedStatement.setBoolean(10, company.isAdmin());
            preparedStatement.execute();

            DbUtils.disconnectFromDB(connection, preparedStatement);

            registerInfo.setText("Company was created!");
            companyNameF.clear();
            companyCodeF.clear();
            representativeF.clear();
            addressF.clear();
            phoneNumberF.clear();
            companyUsernameF.clear();
            companyPasswordF.clear();
        }

    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent part = FXMLLoader.load(HelloApplication.class.getResource("Log_in.fxml"));
        Stage stg = (Stage) cancelButton.getScene().getWindow();
        stg.close();
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companyNameF.setDisable(true);
        companyCodeF.setDisable(true);
        representativeF.setDisable(true);
        addressF.setDisable(true);
        phoneNumberF.setDisable(true);
        companyUsernameF.setDisable(true);
        companyPasswordF.setDisable(true);

    }

    public void enableFields(ActionEvent actionEvent) {

        if(personRadio.isSelected()) {
            nameField.setDisable(false);
            surnameField.setDisable(false);
            emailField.setDisable(false);
            birthPicker.setDisable(false);
            positionField.setDisable(false);
            loginField.setDisable(false);
            passwordField.setDisable(false);
            companyNameF.setDisable(true);
            companyCodeF.setDisable(true);
            representativeF.setDisable(true);
            addressF.setDisable(true);
            phoneNumberF.setDisable(true);
            companyUsernameF.setDisable(true);
            companyPasswordF.setDisable(true);
        }
        else{
            nameField.setDisable(true);
            surnameField.setDisable(true);
            emailField.setDisable(true);
            birthPicker.setDisable(true);
            positionField.setDisable(true);
            loginField.setDisable(true);
            passwordField.setDisable(true);
            companyNameF.setDisable(false);
            companyCodeF.setDisable(false);
            representativeF.setDisable(false);
            addressF.setDisable(false);
            phoneNumberF.setDisable(false);
            companyUsernameF.setDisable(false);
            companyPasswordF.setDisable(false);

        }

        }
    }

