package com.example.laboras1.dataStructure;

public record Section (int id, String name, int count){

    public String getId(){
        return String.valueOf(id);
    }

    public String getName() {
        return name;
    }

    public String getCount() { return String.valueOf(count);}

    @Override
    public String toString() {
        return String.valueOf(this.getName());
    }
}


