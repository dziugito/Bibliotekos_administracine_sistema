package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
import com.example.laboras1.dataStructure.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PersonalInformationController implements Initializable {
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

    private Connection connection;
    private Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateFields();
    }

    public void personalInformation() throws IOException, SQLException {

    connection =DbUtils.connectToDB();
    statement =connection.createStatement();
    String query = "SELECT id, name, login, password, surname, email, position, birth_date FROM Users WHERE id = " + CurrentUser.getId();
    ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int retrievedId = resultSet.getInt("id");
            String retrievedName = resultSet.getString("name");
            String retrievedLogin = resultSet.getString("login");
            String retrievedPassword = resultSet.getString("password");
            String retrievedSurname = resultSet.getString("surname");
            String retrievedEmail = resultSet.getString("email");
            String retrievedPosition = resultSet.getString("position");
            Date retrievedBirthDate = resultSet.getDate("birth_date");
            CurrentUser.CurrentUser(retrievedId, retrievedName, retrievedLogin, retrievedPassword, retrievedSurname,retrievedEmail,retrievedPosition,retrievedBirthDate);
        }
    }

    private void populateFields(){

        try {
            personalInformation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        java.sql.Date birthDate = (Date) CurrentUser.getBirthDate();
        nameField.setText(CurrentUser.getName());
        surnameField.setText(CurrentUser.getSurname());
        emailField.setText(CurrentUser.getEmail());
        positionField.setText(CurrentUser.getPosition());
        loginField.setText(CurrentUser.getLogin());
        passwordField.setText(CurrentUser.getPassword());
        birthPicker.setValue(birthDate.toLocalDate());

    }

    public void submitAction(ActionEvent actionEvent) {

        connection = DbUtils.connectToDB();
        String query = "UPDATE Users SET name = '" + nameField.getText() + "', surname = '" + surnameField.getText() + "', email = '" + emailField.getText() + "', position = '" + positionField.getText() + "', login = '" + loginField.getText() + "', password = '" + passwordField.getText() + "', birth_date = '"+ birthPicker.getValue() +"' WHERE id = " + CurrentUser.getId();
        try{
            PreparedStatement psInsert = connection.prepareStatement(query);
            psInsert.executeUpdate();
            alertMessage("Your personal information was updated!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Parent part = FXMLLoader.load(HelloApplication.class.getResource("MainPage.fxml"));
        Stage stg = (Stage) cancelButton.getScene().getWindow();
        stg.close();
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        stage.setTitle("The kingdom of books");
        stage.setScene(scene);
        stage.show();
    }

    public static void alertMessage(String a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message: ");
        alert.setContentText(a);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
