package view;

import controller.StudentController;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;
import util.StudentControllerImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    private StudentController studentController;
    private Scanner scanner;
    private List<Student> data;


    public View() {
        studentController = new StudentControllerImp();
        scanner = new Scanner(System.in);
        data = new ArrayList<>();
    }
    public void loadData() {
        data = studentController.readObjectsFromFile();
        System.out.println("Data loaded successfully.");
    }

    public void displayMenu() {
        System.out.println("List of students:");
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
            choice = new Scanner(System.in).nextInt();

            switch (choice) {
                case 1:
                    // Add new student
                    // Implement this based on your logic
                    break;
                case 2:
                    System.out.println("List of students:");

                    // Assuming data.size() > 0 to ensure there are students to display
                    if (data.size() > 0) {
                        int pageSize = 4; // Number of rows per page

                        // Display pagination
                        studentController.pagination(data, pageSize);
                    } else {
                        System.out.println("No students to display.");
                    }
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
                    try{
                        System.out.print("[+] Number of objects you want to generate (100M - 1_000_000_000): ");
                        int count = scanner.nextInt();
                        studentController.writeToFile(count);

                    }catch (Exception e){
                        System.out.println("[!] Number Invalid");
                    }
                    break;
                case 8:
                    studentController.clearFile();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }
}
