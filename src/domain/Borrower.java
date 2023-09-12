package domain;


import java.util.List;
import java.util.Scanner;

public class Borrower {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private String num;

    public List<Reservation> reservations;

    // Constructor
    public Borrower(String name,String num) {
        this.name = name;
        this.num = num;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for Num
    public String getNum() {
        return num;
    }

    // Setter for phone
    public void setPhone(String phone) {
        this.num = num;
    }

}