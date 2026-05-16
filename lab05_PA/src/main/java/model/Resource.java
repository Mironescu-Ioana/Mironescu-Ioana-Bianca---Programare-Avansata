package model;

import exception.InvalidResourceException;

public class Resource {
    private String id;
    private String title;
    private String location;
    private int year;
    private String author;

    public Resource(String id, String title, String location, int year, String author) throws InvalidResourceException {
        if(id==null || id.isEmpty())
            throw new InvalidResourceException("ID-ul unei resurse nu poate fi null.");
        if(location==null || location.isEmpty())
            throw new InvalidResourceException("Locatia unei resurse trebuie sa fie precizata!");

        this.author = author;
        this.year = year;
        this.title = title;
        this.id = id;
        this.location = location;
    }

    public Resource() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString()
    {
        return "Resursa: "+id+", titlu: "+title+", locatie: "+location+", year: "+year+", autor: "+author;
    }
}
