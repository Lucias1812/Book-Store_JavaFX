package model;

public class Book {
    private String title;
    private String author;
    private int physicalCopies;
    private double price;
    private int copiesSold;

    // Constructor
    public Book(String title, String author, int physicalCopies, double price, int copiesSold) {
        this.title = title;
        this.author = author;
        this.physicalCopies = physicalCopies;
        this.price = price;
        this.copiesSold = copiesSold;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPhysicalCopies() { return physicalCopies; }
    public double getPrice() { return price; }
    public int getCopiesSold() { return copiesSold; }
}
