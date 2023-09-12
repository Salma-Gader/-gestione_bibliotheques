package services;

import dao.BookDAO;
import dao.BorrowerDAO;
import dao.ReservationDAO;
import domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReservationService {
    private int width = 15;

    private ReservationDAO reservationDAO;
    Scanner scanner = new Scanner(System.in);

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public void checkoutReservation(){
        String isbn;
        do {
            System.out.print("Enter the book ISBN: ");
            isbn = scanner.nextLine();
        }while (!BookDAO.checkISBN(isbn));

        int borrowerId;
        do {
            System.out.print("Enter Borrower ID: ");
            borrowerId = scanner.nextInt();
        }while (!BorrowerDAO.checkBorrowerId(borrowerId));

        System.out.print("Enter duration: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Enter duration: ");
            scanner.next();
        }
        int duration = scanner.nextInt();


        Reservation reservation = new Reservation(isbn,borrowerId,duration);

        if (reservationDAO.checkOut(reservation)){
            System.out.println("Reservation INSERTED SUCCESSFULLY.");
        }else {
            System.out.println("No more copies for this book.");
        };
    }

    public void checkInReservation(){
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for search criteria
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        // Prompt the user for the new quantity
        System.out.print("Enter Borrower ID: ");
        int borrower_id = scanner.nextInt();

        Reservation reservation = new Reservation(isbn,borrower_id,0);

        if (reservationDAO.checkIn(reservation)) {

            System.out.println("the book checked In successfully.");
        } else {
            System.out.println("No Reservation whit this Informations.");
        }
    }

    public void showAllReservations(){
        // Display the list of books in a formatted table
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n",
                "ISBN", "Title","Author","Name","Phone","borrowing_date","Duration","Status");
        System.out.println(
                "-----------------------------------------------------------------------------");
        ResultSet reservations = reservationDAO.getAllReservations();

        try {
            while (reservations.next()) {
                // Retrieve reservation information from the ResultSet
                String isbn = reservations.getString("isbn");
                String title = reservations.getString("title");
                String author = reservations.getString("author");
                String name = reservations.getString("name");
                String phone = reservations.getString("phone");
                Date borrowingDate = reservations.getDate("borrowing_date");
                int duration = reservations.getInt("duration");
                String status = reservations.getString("status");

                // Print the reservation information in the desired format
                System.out.printf("%-"+width+"s %-"+width+"s %-"+width+"s %-"+width+"s %-"+width+"s %-"+width+"s %-"+width+"s %-"+width+"s %n",
                        isbn, title, author, name, phone, borrowingDate, duration, status);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
}
