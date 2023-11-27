package com.example.laboras1.webController;

import com.example.laboras1.dataStructure.Book;
import com.example.laboras1.dataStructure.Person;
import com.example.laboras1.dataStructure.User;
import com.example.laboras1.serializes.UserSerializer;
import com.example.laboras1.serializes.Users;
import com.example.laboras1.serializes.UsersSerializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Controller //localhost:8080/laboras1_war_exploded/
public class UsersController {

    @RequestMapping(value = "users/getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public String getAllUsers() {
        List<Users> users = webOperations.getUsers();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Users.class, new UserSerializer());
        Gson parser = gson.create();
        parser.toJson(users.get(0));

        Type libraryList = new TypeToken<List<Users>>() {
        }.getType();
        gson.registerTypeAdapter(libraryList, new UsersSerializer());
        parser = gson.create();

        return parser.toJson(users);
    }

    @RequestMapping(value = "/users/getUserById", method = RequestMethod.GET)
    @ResponseBody
    public String getUserById(@RequestParam("id") String id) {
        Users user = webOperations.getUserById(Integer.parseInt(id));
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserSerializer());
        Gson parser = gson.create();
        return parser.toJson(user);
    }

    @RequestMapping(value = "/users/deleteUser", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity deleteUser(@RequestParam(name = "id") int id) {
        try {
            webOperations.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

    @RequestMapping(value = "users/createUser", params = {"name", "surname", "email", "birthDate", "position", "login", "password", "dateCreated", "dateModified", "isActive"}, method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam("name") String name, @RequestParam("surname") String surname,
                             @RequestParam("email") String email, @RequestParam("birthDate") String birthDate,
                             @RequestParam("position") String position, @RequestParam("login") String login, @RequestParam("password") String password,
                             @RequestParam("dateCreated") String dateCreated, @RequestParam("dateModified") String dateModified,
                             @RequestParam("isActive") Boolean isActive) {

        String user = webOperations.createUser(name, surname, email, birthDate, position, login, password, dateCreated, dateModified, isActive);

        if (user == null) {
            return "Data not inserted";
        }
        return "User was created";
    }

    @RequestMapping(value = "users/updateUser", params = {"name", "surname", "email", "birthDate", "position", "login", "dateModified", "isActive", "id"}, method = RequestMethod.PUT)
    @ResponseBody
    public String createUser(@RequestParam("name") String name, @RequestParam("surname") String surname,
                             @RequestParam("email") String email, @RequestParam("birthDate") String birthDate,
                             @RequestParam("position") String position, @RequestParam("login") String login,
                             @RequestParam("dateModified") String dateModified, @RequestParam("isActive") Boolean isActive,
                             @RequestParam("id") int id) {

        String user = webOperations.updateUser(name, surname, email, birthDate, position, login, dateModified, isActive, id);

        if (user == null) {
            return "Data not inserted";
        }
        return "User was updated";
    }

    @RequestMapping(value = "/users/usersLogin", params = {"username", "password"}, method = RequestMethod.POST)
    @ResponseBody
    public String validateLogin(@RequestParam("username") String username, @RequestParam("password") String password){

        String user = webOperations.validateByCredentials(username, password);

        if (user == null) {
            return "Wrong credentials";
        }
        return "User exists";
    }

    @RequestMapping(value = "users/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String validateLogin(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String userName = data.getProperty("username");
        String password = data.getProperty("password");

        User user = webOperations.validateByCredential(userName, password);

        if (user == null) {
            return "Wrong credentials";
        }
        return Integer.toString(user.getId());
    }

}


