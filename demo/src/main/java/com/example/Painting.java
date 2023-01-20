package com.example;

public class Painting {
    public String title;
    public int year_made;
    public Painting(){
        
    }

    public Painting(String title, int year_made) {
        this.title = title;
        this.year_made = year_made;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "title='" + title + '\'' +
                ", year_made=" + year_made +
                '}';
    }

}
