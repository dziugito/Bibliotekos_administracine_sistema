package com.example.laboras1.serializes;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class UsersSerializer implements JsonSerializer<List<Users>> {
    @Override
    public JsonElement serialize(List<Users> users, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Users.class, new UsersSerializer());
        Gson parser = gsonBuilder.create();

        for (Users l : users) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
