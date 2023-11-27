package com.example.laboras1.serializes;

import com.example.laboras1.dataStructure.Book;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BookSerializer implements JsonSerializer<Book> {
    @Override
    public JsonElement serialize(Book book, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("bookId", book.getBookId());
        bookJson.addProperty("bookName", book.getBookName());
        bookJson.addProperty("bookAuthor", book.getBookAuthor());
        bookJson.addProperty("bookSection", book.getSection());
        bookJson.addProperty("bookSubSection", book.getSubSection());
        bookJson.addProperty("releaseYear", String.valueOf(book.getReleaseYear()));
        bookJson.addProperty("numberOfPages", book.getNumberOfPages());

        return bookJson;
    }
}
