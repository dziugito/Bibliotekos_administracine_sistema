package com.example.laboras1.dataStructure;

import java.sql.Date;


public class Book{
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String section;
    private String subSection;
    private Date releaseYear;
    private int numberOfPages;
    private int responsible;
    private int takenBy;

    public Book(int bookId, String bookName, String bookAuthor, String section, String subSection, Date releaseYear, int numberOfPages, int responsible, int takenBy) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.section = section;
        this.subSection = subSection;
        this.releaseYear = releaseYear;
        this.numberOfPages = numberOfPages;
        this.responsible = responsible;
        this.takenBy = takenBy;
    }

    public Book(String bookName, String bookAuthor, String section, String subSection, Date releaseYear, int numberOfPages, int takenBy) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.section = section;
        this.subSection = subSection;
        this.releaseYear = releaseYear;
        this.numberOfPages = numberOfPages;
        this.takenBy = takenBy;
    }

    public Book(String section) {
        this.section = section;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getResponsible() {
        return responsible;
    }

    public void setResponsible(int responsible) {
        this.responsible = responsible;
    }

    public int getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(int takenBy) {
        this.takenBy = takenBy;
    }

    @Override
    public String toString() {
        return "Book name = '" + bookName + '\'' +
                " Book author = '" + bookAuthor + '\'';
    }

}
