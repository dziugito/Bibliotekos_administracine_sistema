package com.example.laboras1.dataStructure;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private boolean isAdmin;

    public User(String login, String password, LocalDate dateCreated, LocalDate dateModified, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isAdmin = isAdmin;
    }

    public User(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
        this.isAdmin = false;
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean active) {
        isAdmin = active;
    }
}
