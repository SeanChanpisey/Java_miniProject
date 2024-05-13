package view;

import controller.StudentController;
import util.StudentControllerImp;

import java.util.Scanner;

public class View {
    private StudentController studentController;
    private Scanner scanner;

    public View() {
        studentController = new StudentControllerImp();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. ADD NEW STUDENT");
            System.out.println("2. LIST ALL STUDENTS");
            System.out.println("3. COMMIT DATA TO FILE");
            System.out.println("4. SEARCH FOR STUDENT");
            System.out.println("5. UPDATE STUDENT'S INFO BY ID");
            System.out.println("6. DELETE STUDENT'S DATA");
            System.out.println("7. GENERATE DATA TO FILE");
            System.out.println("8. DELETE/CLEAR ALL DATA FROM DATA STORE");
            System.out.println("0. EXIT");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add new student
                    // Implement this based on your logic
                    break;
                case 2:
                    break;
                case 3:
                    // Commit data to file
                    // Implement this based on your logic
                    break;
                case 4:
                    // Search for student
                    // Implement this based on your logic
                    break;
                case 5:
                    // Update student's info by ID
                    // Implement this based on your logic
                    break;
                case 6:
                    // Delete student's data
                    // Implement this based on your logic
                    break;
                case 7:
                    // Generate data to file
                    // Implement this based on your logic
                    break;
                case 8:
                    // Delete/clear all data from data store
                    // Implement this based on your logic
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }
    public void closeScanner() {
        scanner.close();
    }
}
