import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dao.*;
import services.BookService;
import services.BorrowerService;
import services.ReservationService;
import utils.db.DatabaseConnection;

public class Main {

    public static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }

        BookDAO bookDAO = new BookDAO(connection);
        BorrowerDAO borrowerDAO = new BorrowerDAO(connection);
        ReservationDAO reservationDAO = new ReservationDAO(connection);
        BookService bookService = new BookService(bookDAO);
        ReservationService reservationService = new ReservationService(reservationDAO);
        BorrowerService borrowerService = new BorrowerService(borrowerDAO);
        int choice;
        Scanner input = new Scanner(System.in);
        do {
            showMenu();

            while (!input.hasNextInt()) {
                System.out.print("ENTER A VALID CHOICE BETWEEN 0 AND 14: ");
                input.next(); // Consume the non-integer input
            }

            choice = input.nextInt();
            switch (choice) {

                // Case
                case 0:
                    break;
                case 1:
                    bookService.createAndSaveBook();
                    break;

                case 3:
                    bookService.searchBooks();
                    break;
                case 4:
                    bookService.showAllBooks();
                    break;
                case 5:
                    borrowerService.insert();
                    break;
                case 6:
                    borrowerService.showAllBorrowers();
                    break;
                case 7:
                    reservationService.checkoutReservation();
                    break;
                case 8:
                    reservationService.checkInReservation();
                    break;
                case 9:
                    reservationService.showAllReservations();
                    break;
                case 10:
                    bookService.deleteBook();
                    break;
                case 11:
                    bookService.updateBook();
                    break;
                case 12:
                    generateStatisticsReport();
                    break;
                default:
                    System.out.println("ENTER A VALID CHOICE BETWEEN 0 AND 8.");
            }
        }while (choice != 0);
    }

    public static void showMenu(){
        System.out.println("  Press 1  : Add new Book.                  ||");
        System.out.println("  Press 3  : Search a Book.                 ||");
        System.out.println("  Press 4  : Show All Books.                ||");
        System.out.println("  Press 5  : Register Borrower.             ||");
        System.out.println("  Press 6  : Show All Registered Borrowers. ||");
        System.out.println("  Press 7  : Check Out Book.                ||");
        System.out.println("  Press 8  : Check In Book.                 ||");
        System.out.println("  Press 9 : Show All Reservations.         ||");
        System.out.println("  Press 10 : Delete Book.");
        System.out.println("  Press 11 : Update Book.                   ||");
        System.out.println("  Press 12 : Generate Statistics Report.    ||");
        System.out.println("  Press 0  : Exit Application.              ||");
        System.out.print("Enter Your Choice: ");
    }

    public static void generateStatisticsReport() {

        String sql = "SELECT COUNT(*) as borrowed," +
                "(SELECT COUNT(*) FROM `reservations` WHERE status = 'Lost') as lost," +
                "(SELECT COUNT(*) FROM `books`) as allbooks," +
                "(SELECT COUNT(*) FROM `books` WHERE books.quantity > 0) as availablebooks," +
                "(SELECT COUNT(*) FROM `reservations` WHERE status = 'Returned') as returned," +
                "(SELECT COUNT(*) FROM `authors`) as authors," +
                "(SELECT COUNT(*) FROM `borrowers`) as borrowers " +
                "FROM `reservations` WHERE status = 'Borrowed';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Print the result
            while (resultSet.next()) {
                int borrowed = resultSet.getInt("borrowed");
                int lost = resultSet.getInt("lost");
                int allbooks = resultSet.getInt("allbooks");
                int availablebooks = resultSet.getInt("availablebooks");
                int returned = resultSet.getInt("returned");
                int authors = resultSet.getInt("authors");
                int borrowers = resultSet.getInt("borrowers");
                int max = Math.max(borrowed, Math.max(lost, Math.max(allbooks, Math.max(availablebooks, Math.max(returned, Math.max(authors, borrowers))))));


                System.out.println("Statistics Chart:");
                drawHorizontalBarChart("Borrowed Books: ", borrowed, max);
                drawHorizontalBarChart("Returned Books: ", returned, max);
                drawHorizontalBarChart("Lost Books:     ", lost, max);
                drawHorizontalBarChart("Total Books:    ", allbooks, max);
                drawHorizontalBarChart("Available Books:", availablebooks, max);
                drawHorizontalBarChart("Total Authors:  ", authors, max);
                drawHorizontalBarChart("Total Borrowers:", borrowers, max);
                System.out.print("                 |");
                for (int i = 0; i < 110; i++) {
                    System.out.print("_");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void drawHorizontalBarChart(String label, int value, int maxValue) {
        int filledWidth = (int) Math.ceil((double) value * 100 / maxValue);

        // Create the bar chart
        StringBuilder chart = new StringBuilder();
        chart.append(label);
        chart.append(" |");

        for (int i = 0; i < filledWidth; i++) {
            chart.append("-");
        }
        chart.append(value);


        // Print the chart
        System.out.println(chart);
    }

}
