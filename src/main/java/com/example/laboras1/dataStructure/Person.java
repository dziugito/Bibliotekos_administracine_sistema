package com.example.laboras1.dataStructure;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Person extends User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private String position;

    public Person(String name, String surname, String email, LocalDate birthDate, String position, String login, String password) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
    }

    public Person(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, boolean isActive, String name, String surname, String email, LocalDate birthDate, String position) {
        super(id, login, password, dateCreated, dateModified, isActive);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
    }

    public Person(int id, String login, String password, String name, String surname) {
        super(id, login, password);
        this.name = name;
        this.surname = surname;
    }

    public Person(String login, String password, String name, String surname, String email) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Hello" + name + " " + surname;
    }
}

