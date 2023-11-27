package com.example.laboras1.fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CompanieTableParameters {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty companyCode = new SimpleStringProperty();
    private SimpleStringProperty representative = new SimpleStringProperty();
    private SimpleStringProperty adress = new SimpleStringProperty();
    private SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty dateCreated = new SimpleStringProperty();
    private SimpleStringProperty dateModified = new SimpleStringProperty();

    public CompanieTableParameters(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty companyCode, SimpleStringProperty representative, SimpleStringProperty adress, SimpleStringProperty phoneNumber, SimpleStringProperty username, SimpleStringProperty dateCreated, SimpleStringProperty dateModified) {
        this.id = id;
        this.name = name;
        this.companyCode = companyCode;
        this.representative = representative;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public CompanieTableParameters() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCompanyCode() {
        return companyCode.get();
    }

    public SimpleStringProperty companyCodeProperty() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode.set(companyCode);
    }

    public String getRepresentative() {
        return representative.get();
    }

    public SimpleStringProperty representativeProperty() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative.set(representative);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public String getDateModified() {
        return dateModified.get();
    }

    public SimpleStringProperty dateModifiedProperty() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified.set(dateModified);
    }
}
