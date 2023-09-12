package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import domain.Book;

public class BookDAO {
    private int width = 15;
    private static Connection connection;

    public BookDAO(Connection conn) {
        connection = conn;
    }

    public Boolean insert(Book book) {
        String insertQuery = "INSERT INTO books (title, isbn, quantity, author) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, book.getTitle() );
            preparedStatement.setString(2,book.getIsbn());
            preparedStatement.setInt(3, book.getQuantity());
            preparedStatement.setString(4, book.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert the book into the database.");
        }
        return false;
    }


    public List<Book> getAllBooks(){

        List<Book> books = new ArrayList<Book>();

        String sql = "SELECT * FROM library.books ;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                // Retrieve book information from the result set
                String isbn = resultSet.getString("ISBN");
                String title = resultSet.getString("title");
                String authorId = resultSet.getString("author");
                int quantity = resultSet.getInt("quantity");
                books.add(new Book(isbn,title,authorId,quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBookByTitleOrISBN(String searchCriteria){
        List<Book> books = new ArrayList<Book>();

        String sql = "SELECT ISBN, title, author, quantity FROM books WHERE ISBN LIKE ? OR title LIKE ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters for the search
            preparedStatement.setString(1, "%" + searchCriteria + "%");
            preparedStatement.setString(2, "%" + searchCriteria + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve book information from the result set
                    String isbn = resultSet.getString("ISBN");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    int quantity = resultSet.getInt("quantity");
                    books.add(new Book(isbn,title,author,quantity));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }



    public static boolean downgradeQuantity(String isbn){
        String sql = "UPDATE books SET quantity = quantity - 1 WHERE ISBN = ? and quantity >=1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, isbn);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Book getBookByISBN(String isbn) {
        Scanner scanner = new Scanner(System.in);


        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String authorId = resultSet.getString("author_id");
                int quantity = resultSet.getInt("quantity");
                // Create and return a Book object
                return new Book(isbn, title, authorId, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update book information by ISBN
    public boolean updateBook(Book updatedBook) {

        String sql = "UPDATE books SET title = ?, author = ?, quantity = ? WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedBook.getTitle());
            preparedStatement.setString(2, updatedBook.getAuthor());
            preparedStatement.setInt(3, updatedBook.getQuantity());
            preparedStatement.setString(4, updatedBook.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Boolean checkISBN(String ISBN) {
        String sql = "SELECT COUNT(*) FROM books WHERE ISBN = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ISBN);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteBook(String isbn) {


        String deleteSQL = "UPDATE books SET quantity = 0 WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, isbn);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
        return false;
    }
}
