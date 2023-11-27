package com.example.laboras1.dataStructure;

public record SubSection(int id, String name, int sectionId) {

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSectionId() {
        return sectionId;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getName());
    }
}
