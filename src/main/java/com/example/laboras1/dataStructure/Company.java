package com.example.laboras1.dataStructure;

import java.time.LocalDate;

public class Company extends User{

    private String name;
    private int code;
    private String representative;
    private String address;
    private int phoneNumber;

    public Company(String login, String password, LocalDate dateCreated, LocalDate dateModified, boolean isActive, String name, int code, String representative, String address, int phoneNumber) {
        super(login, password, dateCreated, dateModified, isActive);
        this.name = name;
        this.code = code;
        this.representative = representative;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Company(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, boolean isActive, String name, int code, String representative, String address, int phoneNumber) {
        super(id, login, password, dateCreated, dateModified, isActive);
        this.name = name;
        this.code = code;
        this.representative = representative;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Company(String login, String password, String name, int code, String representative, String address, int phoneNumber) {
        super(login, password);
        this.name = name;
        this.code = code;
        this.representative = representative;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Company(int id, String login, String password, String name, int code, String representative, String address, int phoneNumber) {
        super(id, login, password);
        this.name = name;
        this.code = code;
        this.representative = representative;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
