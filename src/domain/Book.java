package domain;


import java.util.Scanner;

public class Book {
    Scanner scanner = new Scanner(System.in);
    private String ISBN;
    private String title;
    private String author;
    private int quantity;

    public Book(String isbn, int quantity) {
        this.ISBN = isbn;
        this.quantity = quantity;
    }

    public Book(String isbn, String title, String author, int quantity) {
        this.ISBN = isbn;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getter and Setter methods for ISBN
    public String getIsbn() {
        return ISBN;
    }

    public void setIsbn(String isbn) {
        this.ISBN = isbn;
    }

    // Getter and Setter methods for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter methods for author
    public String getAuthor() {return author;}

    public void setAuthor(String Author) {
        this.author = Author;
    }

    public void setAuthorId(String author_id) {
        this.author = author_id;
    }

    // Getter and Setter methods for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}