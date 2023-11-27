package com.example.laboras1.serializes;

import com.example.laboras1.dataStructure.Book;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class BooksSerializer implements JsonSerializer<List<Book>> {
    @Override
    public JsonElement serialize(List<Book> books, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Book.class, new BooksSerializer());
        Gson parser = gsonBuilder.create();

        for (Book l : books) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
