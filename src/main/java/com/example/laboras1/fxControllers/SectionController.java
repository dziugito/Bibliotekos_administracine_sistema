package com.example.laboras1.fxControllers;

import com.example.laboras1.HelloApplication;
import com.example.laboras1.controls.DbUtils;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class SectionController implements Initializable{
    @FXML
    public TableView <Section> sectionTable;
    @FXML
    public TableColumn <Section, Integer> sectionIdCol;
    @FXML
    public TableColumn <Section, String> sectionNameCol;
    @FXML
    public TableColumn <Section, Integer> subSectionCountCol;
    @FXML
    public TableView <SubSection> subSectionTable;
    @FXML
    public TableColumn <SubSection, Integer> subSectionIdCol;
    @FXML
    public TableColumn <SubSection, String> subSectionNameCol;
    @FXML
    public TextField sectionNameField;
    @FXML
    public TextField subSectionNameField;
    @FXML
    public Button sectionInsertButton;
    @FXML
    public Button sectionUpdateButton;
    @FXML
    public Button sectionDeleteButton;
    @FXML
    public Button subSectionInsertButton;
    @FXML
    public Button subSectionUpdateButton;
    @FXML
    public Button subSectionDeleteButton;
    @FXML
    public TextField sectionIdField;
    @FXML
    public TextField subSectionIdField;
    public Button goBackButton;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshSections();
    }

    public void insertSection(ActionEvent actionEvent) {
        if (sectionNameField.getText().isEmpty()) {
            alertMessage("Enter section name!");
        } else {
            String sql = "INSERT INTO Section (name) VALUES ('" + sectionNameField.getText() + "')";
            executeQuery(sql);
            sectionIdField.clear();
            sectionNameField.clear();
            refreshSections();
            alertMessage("Section was successfully inserted!");
        }
    }

    public void updateSection(ActionEvent actionEvent) {
        if (sectionNameField.getText().isEmpty() && sectionIdField.getText().isEmpty()) {
            alertMessage("Choose section from the table!");
        } else {
            String sql = "UPDATE Section SET name = '" + sectionNameField.getText() + "' WHERE id = " + sectionIdField.getText();
            executeQuery(sql);
            sectionIdField.clear();
            sectionNameField.clear();
            refreshSections();
            alertMessage("Section was successfully updated!");
        }
    }

    public void deleteSection(ActionEvent actionEvent) {
        if (sectionIdField.getText().isEmpty()) {
            alertMessage("Choose section from the table!");
        } else {
            String sql = "DELETE FROM Section WHERE id = " + sectionIdField.getText();
            executeQuery(sql);
            sectionIdField.clear();
            sectionNameField.clear();
            refreshSections();
            alertMessage("Section was successfully deleted!");
        }
    }

    public void insertSubSection(ActionEvent actionEvent) {
        if (sectionTable.getSelectionModel().getSelectedItem() == null) {
            alertMessage("Choose section from the table!");
        } else if (subSectionNameField.getText().isEmpty()) {
            alertMessage("Enter Sub-Section name!");
        } else {
            String sql = "INSERT INTO Sub_section (section_id, name) VALUES (" + sectionIdField.getText() + ",'" + subSectionNameField.getText() + "')";
            executeQuery(sql);
            refreshSubSections(Integer.parseInt(sectionIdField.getText()));
            refreshSections();
            subSectionNameField.clear();
            subSectionIdField.clear();
            alertMessage("Section was successfully inserted!");
        }
    }

    public void updateSubSection(ActionEvent actionEvent) {
        if (subSectionNameField.getText().isEmpty() && subSectionIdField.getText().isEmpty()) {
            alertMessage("Choose section from the table!");

        } else {
            String sql = "UPDATE Sub_Section SET name = '" + subSectionNameField.getText() + "' WHERE id = " + subSectionIdField.getText();
            executeQuery(sql);
            refreshSubSections(Integer.parseInt(sectionIdField.getText()));
            refreshSections();
            subSectionNameField.clear();
            subSectionIdField.clear();
            alertMessage("Section was successfully updated!");
        }
    }

    public void deleteSubSection(ActionEvent actionEvent) {
        if (subSectionIdField.getText().isEmpty()) {
            alertMessage("Choose section from the table!");
        } else {
            String sql = "DELETE FROM Sub_Section WHERE id = " + subSectionIdField.getText();
            executeQuery(sql);
            refreshSubSections(Integer.parseInt(sectionIdField.getText()));
            refreshSections();
            subSectionNameField.clear();
            subSectionIdField.clear();
            alertMessage("Section was successfully deleted!");
        }
    }

    public static ObservableList<Section> getSectionList() {
        ObservableList<Section> sectionList = FXCollections.observableArrayList();
        try {
            Connection connection = DbUtils.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.id, s.name, count(ss.name) sub_section_count FROM Section s left join Sub_Section ss on ss.section_id = s.id GROUP BY s.id, s.name");
            ResultSet resultSet = preparedStatement.executeQuery();
            Section section;
            while (resultSet.next()) {
                section = new Section(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("sub_section_count"));
                sectionList.add(section);
            }
            resultSet.close();
            DbUtils.disconnectFromDB(connection, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sectionList;
    }


    public static ObservableList<SubSection> getSubSectionList(int sectionId) {
        ObservableList<SubSection> subSectionList = FXCollections.observableArrayList();
        try {
            Connection connection = DbUtils.connectToDB();
            String sql;
            if(sectionId != 0) {
                sql = "SELECT * FROM Sub_Section WHERE section_id = " + sectionId + " ORDER BY name";
            } else{
                sql = "SELECT * FROM Sub_Section ORDER BY name";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            SubSection subSection;
            while (resultSet.next()) {
                subSection = new SubSection(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("section_id"));
                subSectionList.add(subSection);
            }
            resultSet.close();
            DbUtils.disconnectFromDB(connection, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subSectionList;
    }

    public void refreshSections() {
        ObservableList<Section> sectionList = getSectionList();
        sectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        sectionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subSectionCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        sectionTable.setItems(sectionList);
    }

    public void refreshSubSections(int sectionId) {
        ObservableList<SubSection> subSectionList = getSubSectionList(sectionId);
        subSectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        subSectionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subSectionTable.setItems(subSectionList);
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

    public void mouseClickedSection(MouseEvent mouseEvent) {
        Section section = sectionTable.getSelectionModel().getSelectedItem();
        if (section != null) {
            sectionIdField.setText(section.getId());
            sectionNameField.setText(section.getName());
            refreshSubSections(Integer.parseInt(section.getId()));
        }
    }

    public void mouseClickedSubSecton(MouseEvent mouseEvent) {
        SubSection subSection = subSectionTable.getSelectionModel().getSelectedItem();
        if (subSection!= null) {
            subSectionIdField.setText(String.valueOf(subSection.getId()));
            subSectionNameField.setText(subSection.getName());
        }
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

    public static void alertMessage(String a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message: ");
        alert.setContentText(a);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
