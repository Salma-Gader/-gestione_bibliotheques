package dao;

import domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Reservation;

public class ReservationDAO {

    private static Connection connection;

    public ReservationDAO(Connection conn) {
        connection = conn;
    }

    public boolean checkOut(Reservation reservation) {
        String insertQuery = "INSERT INTO reservations (isbn,borrower_id,duration) VALUES (?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, reservation.getIsbn());
            preparedStatement.setInt(2, reservation.getBorrowerId());
            preparedStatement.setInt(3, reservation.getDuration());

            if(preparedStatement.executeUpdate() > 0 && BookDAO.downgradeQuantity(reservation.getIsbn())){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert the Reservation into the database.");
        }
        return false;
    }

    public boolean checkIn(Reservation reservation){


        // SQL query to update the quantity of the book
        String sql = "UPDATE reservations SET status = 'returned' WHERE ISBN = ? and borrower_id = ? and status = 'Borrowed'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(2, reservation.getBorrowerId());
            preparedStatement.setString(1, reservation.getIsbn());

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 ) {

               return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ResultSet getAllReservations(){
        try{
            String sql = "SELECT reservations.*, books.*, borrowers.*, authors.name as author " +
                    "FROM reservations " +
                    "JOIN books ON books.isbn = reservations.isbn " +
                    "JOIN borrowers ON borrowers.id = reservations.borrower_id " +
                    "JOIN authors ON books.author_id = authors.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }
}
