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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

        @FXML
        public TextField loginField;
        @FXML
        public TextField passwordField;
        @FXML
        public Button loginButton;
        @FXML
        public Button registerButton;
        @FXML
        public Label loginInfo;

        private Connection connection;
        private Statement statement;



        public void loginAction(ActionEvent actionEvent) throws IOException, SQLException {

            connection = DbUtils.connectToDB();
            statement = connection.createStatement();
            String query = "SELECT id, name, login AS username, password, is_admin FROM Users WHERE login ='" + loginField.getText() + "' AND password ='" + passwordField.getText() + "' UNION ALL SELECT id, name, username, password, is_admin FROM Company WHERE username ='" + loginField.getText() + "' AND password ='" + passwordField.getText() +"'";
            String userName = null;
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                userName = rs.getString("name");
            }
            DbUtils.disconnectFromDB(connection, statement);

            if(userName != null) {
                connection = DbUtils.connectToDB();
                statement = connection.createStatement();
                
                String admin = "SELECT * FROM Users WHERE name = '" + userName + "' UNION ALL SELECT * FROM Company WHERE name = '" + userName + "'";
                ResultSet resultSet = statement.executeQuery(admin);

                while (resultSet.next()){
                    int retrievedId = resultSet.getInt("id");
                    String retrievedName = resultSet.getString("name");
                    String retrievedLogin = resultSet.getString("login");
                    String retrievedPassword = resultSet.getString("password");
                    boolean retrievedIsAdmin = resultSet.getBoolean("is_admin");
                    CurrentUser.CurrentUser(retrievedId, retrievedName, retrievedLogin, retrievedPassword, retrievedIsAdmin);

                        Parent part = FXMLLoader.load(HelloApplication.class.getResource("MainPage.fxml"));
                        Stage stg = (Stage) loginButton.getScene().getWindow();
                        stg.close();
                        Stage stage = new Stage();
                        Scene scene = new Scene(part);
                        stage.setTitle("The kingdom of books");
                        stage.setScene(scene);
                        stage.show();
                    }
                }

            else{
                alertMessage("User does not exist or wrong data input, please try again!");
            }
        }

        public static void alertMessage(String a){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Message: ");
            alert.setContentText(a);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }

        public void registerAction(ActionEvent actionEvent) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("The kingdom of books");
            stage.setScene(scene);
            stage.showAndWait();
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }

