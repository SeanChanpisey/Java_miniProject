package view;

import controller.StudentController;
import util.StudentControllerImp;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentController studentController = new StudentControllerImp();

        System.out.println("\n" +
                "       ██╗ █████╗ ██╗   ██╗ █████╗      ██████╗███████╗████████╗ █████╗ ██████╗ \n" +
                "       ██║██╔══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗\n" +
                "       ██║███████║██║   ██║███████║    ██║     ███████╗   ██║   ███████║██║  ██║\n" +
                "  ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║    ██║     ╚════██║   ██║   ██╔══██║██║  ██║\n" +
                "  ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║    ╚██████╗███████║   ██║   ██║  ██║██████╔╝\n" +
                "   ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝     ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝ \n" +
                "                                                                               \n");
        studentController.readDataWhenLoad();
        studentController.commitData();

        int choice;
        // Start the menu loop
        do {
            // Display the menu options
            System.out.println("=================================================================================================");
            System.out.print("\t\t1.ADD NEW STUDENT");
            System.out.print("\t\t\t2.LIST ALL STUDENTS");
            System.out.print("\t\t\t\t\t3.COMMIT DATA TO FILE\n");
            System.out.print("\t\t4.SEARCH FOR STUDENT");
            System.out.print("\t\t5.UPDATE STUDENT'S INFO BY ID");
            System.out.print("\t\t6.DELETE STUDENT DATA\n");
            System.out.print("\t\t7.GENERATE DATA TO FILE");
            System.out.print("\t\t8.CLEAR / DELETE ALL DATA FROM STORE\n");
            System.out.print("\t\t0.Exit\n");
            System.out.println("\n@Copyright by CSTAD");
            System.out.println("==================================================================================================");
            System.out.print("> Insert option: ");

            // Read the user's choice
            try {
                choice = new Scanner(System.in).nextInt();

                // Process the user's choice
                switch (choice) {
                    case 1:
                        studentController.addNewStudent();
                        break;
                    case 2:
                        System.out.println("List of students:");
                        studentController.showList();
                        break;
                    case 3:
                            studentController.commitData();
                        break;
                    case 4:
                        studentController.searchStudentDetails();
                        break;
                    case 5:
                        studentController.updateStudentDetails();
                        break;
                    case 6:
                        studentController.deleteStudentDetails();
                        break;
                    case 7:
                        try {
                            System.out.print("[+] Number of objects you want to generate (100M - 1_000_000_000): ");
                            int count = new Scanner(System.in).nextInt();
                            studentController.writeDataToFileAsync(count);
                        } catch (Exception e) {
                            System.out.println("[!] Number Invalid");
                        }
                        break;
                    case 8:
                        studentController.clearDataFile();
                        break;
                    case 0:
                        studentController.commitData();
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Invalid choice. Please enter a valid option.");
                choice = -1; // Set choice to an invalid value to repeat the loop
            }
        } while (choice != 0);
    }
}

