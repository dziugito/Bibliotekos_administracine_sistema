package com.example.laboras1.dataStructure;

import java.util.Date;

public class CurrentUser {
    private static int id;
    private static String name;
    private static String login;
    private static String password;
    private static boolean isAdmin;
    private static String surname;
    private static String email;
    private static String position;
    private static Date birthDate;

    public static void CurrentUser(int usrId, String usrName, String usrLogin, String usrPassword, boolean usrIsAdmin) {
        id = usrId;
        name = usrName;
        login = usrLogin;
        password = usrPassword;
        isAdmin = usrIsAdmin;
    }

    public static void CurrentUser(int usrId, String usrName, String usrLogin, String usrPassword, boolean usrIsAdmin, String usrSurname, String usrPosition, String usrEmail, Date usrBirthDate) {
        id = usrId;
        name = usrName;
        login = usrLogin;
        password = usrPassword;
        isAdmin = usrIsAdmin;
        surname = usrSurname;
        email = usrEmail;
        position = usrPosition;
        birthDate = usrBirthDate;
    }
    public static void CurrentUser(int usrId, String usrName, String usrLogin, String usrPassword, String usrSurname, String usrEmail, String usrPosition, Date usrBirthDate) {
        id = usrId;
        name = usrName;
        login = usrLogin;
        password = usrPassword;
        surname = usrSurname;
        email = usrEmail;
        position = usrPosition;
        birthDate = usrBirthDate;
    }


    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        CurrentUser.surname = surname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }

    public static String getPosition() {
        return position;
    }

    public static void setPosition(String position) {
        CurrentUser.position = position;
    }

    public static Date getBirthDate() {
        return birthDate;
    }

    public static void setBirthDate(Date birthDate) {
        CurrentUser.birthDate = birthDate;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        CurrentUser.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentUser.name = name;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        CurrentUser.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        CurrentUser.isAdmin = isAdmin;
    }
}
