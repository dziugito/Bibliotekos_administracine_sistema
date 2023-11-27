package com.example.laboras1.serializes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<Users> {
    @Override
    public JsonElement serialize(Users users, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject userJson = new JsonObject();
        userJson.addProperty("id", users.getId());
        userJson.addProperty("name", users.getName());
        userJson.addProperty("surname", users.getSurname());
        userJson.addProperty("email", users.getEmail());
        userJson.addProperty("birthDate", String.valueOf(users.getBirthDate()));
        userJson.addProperty("position", users.getPosition());
        userJson.addProperty("login", users.getLogin());
        userJson.addProperty("password", users.getPassword());
        userJson.addProperty("dateCreated", String.valueOf(users.getDateCreated()));
        userJson.addProperty("dateModified", String.valueOf(users.getDateModified()));
        userJson.addProperty("isActive", String.valueOf(users.getActive()));

        return userJson;
    }
}
