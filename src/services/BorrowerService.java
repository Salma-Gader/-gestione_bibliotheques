package services;

import dao.BorrowerDAO;
import domain.Borrower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BorrowerService {

    private int width = 15;

    private BorrowerDAO borrowerDAO;
    Scanner scanner = new Scanner(System.in);

    public BorrowerService(BorrowerDAO borrowerDAO) {
        this.borrowerDAO = borrowerDAO;
    }

    public void insert(){
        String name;
        do {
            System.out.print("Enter Borrower Name: ");
            name = scanner.nextLine();
        }while (!name.matches("\\S+"));

        String num;
        do {
            System.out.print("Enter Borrower num: ");
            num = scanner.nextLine();
        } while (!num.matches("\\S+"));

        Borrower borrower = new Borrower(name,num);

        if (borrowerDAO.insert(borrower)){
            System.out.println("Borrower INSERTED SUCCESSFULLY.");
        }

    }

    public void showAllBorrowers(){
        // Display the list of books in a formatted table
        System.out.printf("%-15s %-15s %-15s %n",
                "Id", "Name","Phone");
        System.out.println(
                "-----------------------------------------------------------------------------");

        ResultSet resultSet = borrowerDAO.getAllBorrowers();

        try {
            while (resultSet.next()) {
                // Retrieve book information from the result set
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("num");

                System.out.printf("%-15s %-15s %-15s %n",
                        id,name,phone);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
