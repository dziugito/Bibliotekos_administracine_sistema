package com.example.laboras1.fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BooksParameters {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty author = new SimpleStringProperty();
    private SimpleStringProperty section = new SimpleStringProperty();
    private SimpleStringProperty subSection = new SimpleStringProperty();
    private SimpleStringProperty releaseDate = new SimpleStringProperty();
    private SimpleIntegerProperty numberOfPages = new SimpleIntegerProperty();
    private SimpleIntegerProperty responsible = new SimpleIntegerProperty();
    private SimpleIntegerProperty takenBy = new SimpleIntegerProperty();

    public BooksParameters(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty author, SimpleStringProperty section, SimpleStringProperty subSection, SimpleStringProperty releaseDate, SimpleIntegerProperty numberOfPages, SimpleIntegerProperty responsible, SimpleIntegerProperty takenBy) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.section = section;
        this.subSection = subSection;
        this.releaseDate = releaseDate;
        this.numberOfPages = numberOfPages;
        this.responsible = responsible;
        this.takenBy = takenBy;
    }

    public BooksParameters() {
    }

    public int getTakenBy() {
        return takenBy.get();
    }

    public SimpleIntegerProperty takenByProperty() {
        return takenBy;
    }

    public void setTakenBy(int takenBy) {
        this.takenBy.set(takenBy);
    }

    public String getSubSection() {
        return subSection.get();
    }

    public SimpleStringProperty subSectionProperty() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection.set(subSection);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getSection() {
        return section.get();
    }

    public SimpleStringProperty sectionProperty() {
        return section;
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getReleaseDate() {
        return releaseDate.get();
    }

    public SimpleStringProperty releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public int getNumberOfPages() {
        return numberOfPages.get();
    }

    public SimpleIntegerProperty numberOfPagesProperty() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages.set(numberOfPages);
    }

    public int getResponsible() {
        return responsible.get();
    }

    public SimpleIntegerProperty responsibleProperty() {
        return responsible;
    }

    public void setResponsible(int responsible) {
        this.responsible.set(responsible);
    }
}
