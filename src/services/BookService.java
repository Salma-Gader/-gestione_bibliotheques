package services;

import dao.BookDAO;
import domain.Book;

import java.util.List;
import java.util.Scanner;

public class BookService {
    private int width = 15;

    private BookDAO bookDAO;
    Scanner scanner = new Scanner(System.in);

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void createAndSaveBook() {

        String ISBN;
        do {
            System.out.print("Enter Unique ISBN: ");
            ISBN = scanner.nextLine();
        } while (BookDAO.checkISBN(ISBN) || !ISBN.matches("\\S+"));

        String title;
        do {
            System.out.print("Enter title: ");
            title = scanner.nextLine();
        } while (!title.matches("\\S+"));

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter quantity: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Enter quantity: ");
            scanner.next();
        }
        int quantity = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character

        Book book = new Book(ISBN, title, author, quantity);

        if (bookDAO.insert(book)) {
            System.out.println("Book Added successfully.");
        } else {
            System.out.println("Book Failed.");
        }
    }

    public void upgradeQuantity(){

        // Prompt the user for search criteria
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (!BookDAO.checkISBN(isbn)) {
            System.out.println("Book with ISBN " + isbn + " does not exist.");
            return;
        }

        // Prompt the user for the new quantity
        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();

        Book book = new Book(isbn,quantity);

    }

    public void searchBooks(){

        // Prompt the user for search criteria
        System.out.print("Enter ISBN or title to search: ");
        String searchCriteria = scanner.nextLine();

        List<Book> books = bookDAO.getBookByTitleOrISBN(searchCriteria);


        System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "s %-" + width + "s%n",
                "ISBN", "Title", "Author ", "Quantity");
        System.out.println(
                "-----------------------------------------------------------------------------");
        for (Book book : books){
            System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "s %-" + width + "d%n",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQuantity());
        }

    }

    public void showAllBooks(){
        // Display the list of books in a formatted table
        System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "s %-" + width + "s%n",
                "ISBN", "Title", "Author", "Quantity");
        System.out.println(
                "-----------------------------------------------------------------------------");
        List<Book> books = bookDAO.getAllBooks();

        for (Book book : books){
            System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "s %-" + width + "d%n",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQuantity());
        }
    }

    public void updateBook(){
        // Prompt the user for search criteria
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        while(!BookDAO.checkISBN(isbn)) {
            System.out.println("Book with ISBN " + isbn + " does not exist.");
            System.out.print("Enter ISBN: ");
            isbn = scanner.nextLine();
        }


        System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "s %-" + width + "s%n",
                "ISBN", "Title", "Author ID", "Quantity");
        System.out.println(
                "-----------------------------------------------------------------------------");

        Book book = bookDAO.getBookByISBN(isbn);

        System.out.printf("%-" + width + "s %-" + width + "s %-" + width + "d %-" + width + "d%n",
                book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQuantity());


        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter new author ID: ");
        String newAuthorId = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        Book newBook = new Book(isbn,newTitle,newAuthorId,newQuantity);

        if (bookDAO.updateBook(newBook)) {
            System.out.println("Quantity updated successfully.");
        } else {
            System.out.println("Failed to update quantity.");
        }
    }

    public void deleteBook(){

        // Prompt the user for search criteria
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if ( bookDAO.deleteBook(isbn)) {
            System.out.println("Book with ISBN " + isbn + " has been deleted.");
        } else {
            System.out.println("Book with ISBN " + isbn + " not found in the database.");
        }
    }
}
