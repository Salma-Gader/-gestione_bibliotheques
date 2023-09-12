package domain;


import java.util.Scanner;

public class Reservation {
        Scanner scanner = new Scanner(System.in);
        private String isbn;
        private int borrowerId;
        private int duration;

        // Constructor
        public Reservation(String isbn,int borrowerId,int duration) {
            this.isbn = isbn;
            this.borrowerId = borrowerId;
            this.duration = duration;
        }

        // Getter for ISBN
        public String getIsbn() {
            return isbn;
        }

        // Setter for ISBN
        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        // Getter for Borrower ID
        public int getBorrowerId() {
            return borrowerId;
        }

        // Setter for Borrower ID
        public void setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
        }

        // Getter for Duration
        public int getDuration() {
            return duration;
        }

        // Setter for Duration
        public void setDuration(int duration) {
            this.duration = duration;
        }
}
