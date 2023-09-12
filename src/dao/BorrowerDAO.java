package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Borrower;

public class BorrowerDAO {

    private static Connection connection;

    public BorrowerDAO(Connection conn) {
        connection = conn;
    }

    public boolean insert(Borrower borrower) {
        String insertQuery = "INSERT INTO borrowers (name,num) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, borrower.getName());
            preparedStatement.setString(2, borrower.getNum());

            if (preparedStatement.executeUpdate()>0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert the Borrower into the database.");
        }
        return false;
    }

    public ResultSet getAllBorrowers(){
        // Define column widths (adjust as needed)
        String sql = "SELECT * FROM borrowers";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean checkBorrowerId(int id) {
        String sql = "SELECT COUNT(*) FROM borrowers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

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
}
