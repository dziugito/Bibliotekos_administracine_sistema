package com.example.laboras1.webController;

import com.example.laboras1.dataStructure.Book;
import com.example.laboras1.serializes.BookSerializer;
import com.example.laboras1.serializes.BooksSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.lang.String;
import java.util.List;
import java.util.Properties;

@Controller //localhost:8080/laboras1_war_exploded/
public class BooksController {

    @RequestMapping(value = "books/getAllBooks", method = RequestMethod.GET)
    @ResponseBody
    public String getAllBooks() {
        List<Book> books = webOperations.getBooks();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Book.class, new BookSerializer());
        Gson parser = gson.create();
        parser.toJson(books.get(0));

        Type libraryList = new TypeToken<List<Book>>() {
        }.getType();
        gson.registerTypeAdapter(libraryList, new BooksSerializer());
        parser = gson.create();

        return parser.toJson(books);
    }

    @RequestMapping(value = "books/getBookById", method = RequestMethod.GET)
    @ResponseBody
    public String getBookById(@RequestParam("id") String id) {
        Book book = webOperations.getBookById(Integer.parseInt(id));
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Book.class, new BookSerializer());
        Gson parser = gson.create();
        return parser.toJson(book);
    }


    @RequestMapping(value = "books/deleteBook", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity deleteBook(@RequestParam(name = "id") int id) {
        try {
            webOperations.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book");
        }
    }

    @RequestMapping(value = "books/createBook",params = {"name", "author", "description", "date", "pages"}, method = RequestMethod.POST)
    @ResponseBody
    public String createBook(@RequestParam("name") String name, @RequestParam("author") String author,
                             @RequestParam("description") String description, @RequestParam("date") String date,
                             @RequestParam("pages") int pages) {

        String books = webOperations.insertBook(name, author, description, date, pages);

        if (books == null) {
            return "Data not inserted";
        }
        return "Book was created";
    }

    @RequestMapping(value = "books/updateBook",params = {"name", "author", "section", "sub_section", "date", "pages", "id"}, method = RequestMethod.PUT)
    @ResponseBody
    public String updateBook(@RequestParam("name") String name, @RequestParam("author") String author,
                             @RequestParam("section") String section, @RequestParam("sub_section") String sub_section, @RequestParam("date") String date,
                             @RequestParam("pages") int pages, @RequestParam("id") int id){

        String books = webOperations.updateBook(name, author, section, sub_section, date, pages, id);

        if (books == null) {
            return "Data not inserted";
        }
        return "Book was updated";
    }


    @RequestMapping(value = "books/updateBook", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String author = data.getProperty("author");
        String section = data.getProperty("section");
        String subSection = data.getProperty("subSection");
        String releaseYear = data.getProperty("releaseYear");
        int pages = Integer.parseInt(data.getProperty("numberOfPages"));
        int id = Integer.parseInt(data.getProperty("id"));

        String book = webOperations.updateBook(name, author, section, subSection, releaseYear, pages, id);

        if (book == null) {
            return "No such book";
        }
        return "Success";
    }


}
