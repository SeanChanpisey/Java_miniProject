package util;
import controller.StudentController;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class StudentControllerImp implements StudentController {
    Scanner scanner = new Scanner(System.in);
    List<Student> data = new ArrayList<>();
    private static final String FILE_PATH = "Data/data.txt";
    private static final String TRANSITION_UPDATE_FILE = "src/transitions/transitionUpdate.txt";
    private static final String C = "src/transitions/transitionAddNew.txt";
    private static final Random random = new Random();
    @Override
    public void pagination(List<Student> studentList, int row){
        int a = studentList.size() - 1; // Start from the last index
        int page = 1;
        do {
            int data = 0;
            String input;
            System.out.println("[*] Student's data".toUpperCase());
            Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
            table.setColumnWidth(0, 22, 22);
            table.setColumnWidth(1, 25, 25);
            table.setColumnWidth(2, 25, 25);
            table.setColumnWidth(3, 30, 30);
            table.setColumnWidth(4, 30, 30);
            table.setColumnWidth(5, 25, 25);
            // Head table
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Name".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Date of Birth".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student class".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's subject".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Created/Update at".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            // Body table
            if (a - row >= 0) {
                for (int i = a; i >= a - row + 1; i--) { // Iterate in reverse order
                    data += 1;
                    TableStudent.printTableStudent(studentList, i, table);
                }
            } else {
                for (int i = a; i >= 0; i--) { // Iterate in reverse order
                    data += 1;
                    TableStudent.printTableStudent(studentList, i, table);
                }
            }
            System.out.println(table.render());
            System.out.println("-".repeat(165));
            System.out.println(" Page Number: " + page
                    + "\t [*] Actual record: " + data
                    + "\t [*] All Records: " + studentList.size()
                    + "\t\t\t\t[+] Previous (P/p) - [+] Next (n/N) - [+] Back (B/b)");
            System.out.println("-".repeat(165));
            System.out.print("[+] Insert to Navigate [p/n]: ");
            input = new Scanner(System.in).nextLine().toLowerCase();
            if (input.equals("n") || input.equals("next")) {
                if (a - row >= 0) {
                    a -= row;
                    page += 1;
                } else {
                    System.out.println("[!] Last Page <<".toUpperCase());
                }
            } else if (input.equals("p") || input.equals("previous")) {
                if (a + row < studentList.size()) {
                    a += row;
                    page -= 1;
                } else {
                    System.out.println("[!] First Page >>".toUpperCase());
                }
            } else if (input.equals("b") || input.equals("back")) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        } while (true);
    }
    @Override
    public void addNewStudent() {
        try {
            List<Student> newStudentCommit = new ArrayList<>();
            // Auto-generate ID ending with "CSTAD"
            String id = generateStudentId();
            // Get the current date and time
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Format the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String currentDate = currentDateTime.format(formatter);
            System.out.println("INSERT STUDENT'S INFO");
            String name;
            do {
                System.out.print("[+] Insert student's name: ");
                name = scanner.nextLine();
                // Check if name contains numbers
                if (name.matches(".*\\d.*")) {
                    System.out.println("[Error] Name must not contain numbers. Please enter a valid name.");
                }
            } while (name.matches(".*\\d.*"));

            int birthYear;
            do {
                try {
                    System.out.println("[+] STUDENT DATE OF BIRTH");
                    System.out.print("> Year (number): ");
                    birthYear = scanner.nextInt();
                    if (birthYear <= 0) {
                        System.out.println("[Error] Year must be greater than 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("[Error] Year must be an integer.");
                    birthYear = 0; // Reset birthYear to trigger the loop again
                    scanner.nextLine(); // Clear the invalid input from the scanner buffer
                }
            } while (birthYear <= 0);

            int birthMonth;
            do {
                try {
                    System.out.print("> Month (number): ");
                    birthMonth = scanner.nextInt();
                    if (birthMonth <= 0 || birthMonth > 12) {
                        System.out.println("[Error] Month must be between 1 and 12.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("[Error] Month must be an integer.");
                    birthMonth = 0; // Reset birthMonth to trigger the loop again
                    scanner.nextLine(); // Clear the invalid input from the scanner buffer
                }
            } while (birthMonth <= 0 || birthMonth > 12);

            int birthDay;
            do {
                try {
                    System.out.print("> Day (number): ");
                    birthDay = scanner.nextInt();
                    if (birthDay <= 0 || birthDay > 31) {
                        System.out.println("[Error] Day must be between 1 and 31.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("[Error] Day must be an integer.");
                    birthDay = 0; // Reset birthDay to trigger the loop again
                    scanner.nextLine(); // Clear the invalid input from the scanner buffer
                }
            } while (birthDay <= 0 || birthDay > 31);

            // Clear the scanner buffer
            scanner.nextLine();

            System.out.println("[!] YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2).");
            System.out.print("[+] Students's class: ");
            String classesInput = scanner.nextLine();
            String[] classes = Arrays.stream(classesInput.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            System.out.println("[!] YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
            System.out.print("[+] Subject studied: ");
            String subjectsInput = scanner.nextLine();
            String[] subjects = Arrays.stream(subjectsInput.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            // Create the student object
            Student newStudent = new Student();
            newStudent.setId(id);
            newStudent.setName(name);
            newStudent.setBirthYear(birthYear);
            newStudent.setBirthMonth(birthMonth);
            newStudent.setBirthDay(birthDay);
            newStudent.setClasses(classes);
            newStudent.setSubjects(subjects);
            newStudent.setCurrentDate(currentDate); // Set the current date

            System.out.println("[*] STUDENT HAS BEEN ADD SUCCESSFULLY.");
            data.add(newStudent);
            newStudentCommit.add(newStudent);
            //writeDataToFile();
            addNewCommittedData(newStudentCommit);
        } catch (Exception e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }


    @Override
    public void writeDataToFile() {
        // Ensure the directory exists
        File directory = new File("Data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Overwrite the file with the current list of students
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data/data.bin")))) {
            for (Student student : data) {
                outputStream.writeObject(student);
            }
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void showList(){
        int pageSize = 0;
        if (!data.isEmpty()) {
            pageSize = 4;
            pagination(data, pageSize);
        } else {
            pagination(data, pageSize);
        }
    }
    @Override
    public void readDataWhenLoad() {
        long startTime = System.nanoTime(); // Start time

        int recordCount = 0; // Count of records read
        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Data/data.bin")))) {
            while (true) {
                try {
                    Student student = (Student) inputStream.readObject();
                    data.add(student); // Add the student to the list of students
                    recordCount++; // Increment record count
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[!] Error reading data from file: " + e.getMessage());
        }

        long endTime = System.nanoTime(); // End time
        long elapsedTime = endTime - startTime; // Elapsed time in nanoseconds
        double seconds = (double) elapsedTime / 1_000_000_000.0; // Convert nanoseconds to seconds

        // Display time taken and record count
        System.out.println("[*] Read " + recordCount + " records from file in " + seconds + " seconds.");
    }

    // Method to generate student ID
    private String generateStudentId() {
        return UUID.randomUUID().toString().substring(0, 4).toUpperCase() + "CSTAD";
    }

    @Override
    public Student searchStudentById(String id) {
        for (Student student : data) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null; // Student not found
    }

    private void displayStudentDetails(Student student) {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);


        table.setColumnWidth(0, 25, 25);
        table.setColumnWidth(1, 35, 35);

        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getBirthYear() + "-" + student.getBirthMonth() + "-" + student.getBirthDay(), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("CREATED/UPDATE AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getCurrentDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        System.out.println(table.render());
    }
    @Override
    public void searchStudentDetails(){
        System.out.println("[+] SEARCHING STUDENT");
        System.out.println(".........................................");
        System.out.println("1. SEARCH BY NAME");
        System.out.println("2. SEARCH BY ID");
        System.out.println("- (BACK/B) TO BACK");
        System.out.println(".........................................");
        System.out.print(">> Insert option: ");
        String searchOption = new Scanner(System.in).nextLine().trim().toLowerCase();
        switch (searchOption) {
            case "1":
                System.out.println("[*] SEARCH STUDENT BY NAME");
                System.out.print(">>> Insert student's name: ");
                String partialName = new Scanner(System.in).nextLine().trim();
                List<Student> matchingStudents = searchStudentsByPartialName(partialName);
                if (!matchingStudents.isEmpty()) {
                    System.out.println("Found " + matchingStudents.size() + " student(s) with names containing '" + partialName + "':");
                    for (Student student : matchingStudents) {
                        displayStudentDetails(student);
                    }
                } else {
                    System.out.println("No students found with names containing '" + partialName + "'.");
                }
                System.out.print(">>> Press enter to continue...");
                new Scanner(System.in).nextLine(); // Wait for user input
                break;
            case "2":
                System.out.println("[*] SEARCH STUDENT BY ID");
                System.out.print(">>> Insert student's ID: ");
                String studentId = new Scanner(System.in).nextLine().trim();
                Student searchedStudent = searchStudentById(studentId);
                if (searchedStudent != null) {
                    displayStudentDetails(searchedStudent);
                } else {
                    System.out.println("Student not found with ID: " + studentId);
                }
                System.out.print(">>> Press enter to continue...");
                new Scanner(System.in).nextLine(); // Wait for user input
                break;
            case "b":
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private List<Student> searchStudentsByPartialName(String partialName) {
        List<Student> matchingStudents = new ArrayList<>();
        for (Student student : data) {
            if (student.getName().toLowerCase().contains(partialName.toLowerCase())) {
                matchingStudents.add(student);
            }
        }
        return matchingStudents;
    }

    private List<Student> searchStudentByName(String studentName) {
        List<Student> searchedStudents = new ArrayList<>();
        for (Student student : data) {
            if (student.getName().equalsIgnoreCase(studentName)) {
                searchedStudents.add(student);
            }
        }
        return searchedStudents;
    }

    @Override
    public void updateStudentDetails(){
        List<Student> newUpdate = new ArrayList<>();
        System.out.println("[*] UPDATE STUDENT BY ID");
        System.out.print(">>> Insert student's ID: ");
        String updateId = new Scanner(System.in).nextLine().trim();
        Student studentToUpdate = searchStudentById(updateId);
        if (studentToUpdate != null) {
            displayStudentDetails(studentToUpdate);
            System.out.println("===================================");
            System.out.println("[+] UPDATE STUDENT'S INFORMATION:");
            System.out.println("-----------------------------------");
            System.out.println("1. UPDATE STUDENT'S NAME");
            System.out.println("2. UPDATE STUDENT'S DATE OF BIRTH");
            System.out.println("3. UPDATE STUDENT'S CLASS");
            System.out.println("4. UPDATE STUDENT'S SUBJECT");
            System.out.println("...................................");
            System.out.print(">>> Insert option: ");
            int updateOption = new Scanner(System.in).nextInt();
            switch (updateOption) {
                case 1:
                    System.out.print("[+] Insert new student's name: ");
                    String newName = new Scanner(System.in).nextLine().trim();
                    studentToUpdate.setName(newName);
                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    newUpdate.add(studentToUpdate);
                    updateCommittedData(newUpdate);
                    System.out.println("[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;
                case 2:
                    // Update date of birth
                    int birthYear;
                    do {
                        try {
                            System.out.println("[+] Insert new student's date of birth:");
                            System.out.print("> Year (number): ");
                            birthYear = new Scanner(System.in).nextInt();
                            if (birthYear <= 0) {
                                System.out.println("[Error] Year must be greater than 0.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("[Error] Year must be an integer.");
                            birthYear = 0; // Reset birthYear to trigger the loop again
                            new Scanner(System.in).nextLine(); // Clear the invalid input from the scanner buffer
                        }
                    } while (birthYear <= 0);

                    int birthMonth;
                    do {
                        try {
                            System.out.print("> Month (number): ");
                            birthMonth = new Scanner(System.in).nextInt();
                            if (birthMonth <= 0 || birthMonth > 12) {
                                System.out.println("[Error] Month must be between 1 and 12.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("[Error] Month must be an integer.");
                            birthMonth = 0; // Reset birthMonth to trigger the loop again
                            new Scanner(System.in).nextLine(); // Clear the invalid input from the scanner buffer
                        }
                    } while (birthMonth <= 0 || birthMonth > 12);

                    int birthDay;
                    do {
                        try {
                            System.out.print("> Day (number): ");
                            birthDay = new Scanner(System.in).nextInt();
                            if (birthDay <= 0 || birthDay > 31) {
                                System.out.println("[Error] Day must be between 1 and 31.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("[Error] Day must be an integer.");
                            birthDay = 0; // Reset birthDay to trigger the loop again
                            new Scanner(System.in).nextLine(); // Clear the invalid input from the scanner buffer
                        }
                    } while (birthDay <= 0 || birthDay > 31);

                    // Set the new date of birth in the student object
                    studentToUpdate.setBirthYear(birthYear);
                    studentToUpdate.setBirthMonth(birthMonth);
                    studentToUpdate.setBirthDay(birthDay);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    newUpdate.add(studentToUpdate);
                    updateCommittedData(newUpdate);
                    System.out.println("[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;
                case 3:
                    // Update class
                    System.out.println("[!] YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2).");
                    System.out.print("[+] Students's class: ");
                    String classesInput = new Scanner(System.in).nextLine();
                    String[] classes = Arrays.stream(classesInput.split(","))
                            .map(String::trim)
                            .toArray(String[]::new);

                    // Set the new classes in the student object
                    studentToUpdate.setClasses(classes);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    newUpdate.add(studentToUpdate);
                    updateCommittedData(newUpdate);
                    System.out.println("[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;

                case 4:
                    // Update subject
                    System.out.println("[!] YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
                    System.out.print("[+] Subject studied: ");
                    String subjectsInput = new Scanner(System.in).nextLine();
                    String[] subjects = Arrays.stream(subjectsInput.split(","))
                            .map(String::trim)
                            .toArray(String[]::new);

                    // Set the new subjects in the student object
                    studentToUpdate.setSubjects(subjects);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    newUpdate.add(studentToUpdate);
                    updateCommittedData(newUpdate);
                    System.out.println("[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } else {
            System.out.println("Student not found with ID: " + updateId);
        }
    }

    @Override
    public void deleteStudentDetails() {
        List<Student> newDelete = new ArrayList<>();
        System.out.println("[*] DELETE STUDENT BY ID");
        System.out.print(">> Insert student's ID: ");
        String studentIdToDelete = new Scanner(System.in).nextLine().trim();
        Student studentToDelete = searchStudentById(studentIdToDelete);
        if (studentToDelete != null) {
            System.out.println("[*] STUDENT'S INFO.");
            displayStudentDetails(studentToDelete);
            System.out.print("[+] Are you sure to delete the student information [Y/n]: ");
            String confirmDelete = new Scanner(System.in).nextLine().trim().toLowerCase();
            if (confirmDelete.equals("y")) {
                // Delete the student
                deleteStudent(studentToDelete);
                System.out.println("[+] User data has been deleted temporarily successfully.");

                // Store the deleted student's data in transitionDelete.txt
                newDelete.add(studentToDelete);
                deleteCommittedData(newDelete);
            } else {
                System.out.println("[+] Delete operation aborted.");
            }
        } else {
            System.out.println("[!] Student not found with ID: " + studentIdToDelete);
        }
    }

    private void deleteStudent(Student student) {
        if (data.contains(student)) {
            data.remove(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    @Override
    public void writeDataToFileAsync(int count) {
        long startTime = System.nanoTime(); // Start timing

        for (int i = 0; i < count; i++) {
            data.add(new Student("0000CSTAD", "UDOM", 21, 2, 2003, new String[]{"WEB DESIGN"}, new String[]{"HTML"}, LocalDate.now().toString()));
        }

        writeDataToFile(); // Write all data to the file

        long endTime = System.nanoTime(); // End timing
        long elapsedTime = endTime - startTime; // Calculate elapsed time
        double seconds = (double) elapsedTime / 1_000_000_000.0; // Convert to seconds

        // Display the time taken and count of records written
        System.out.println("[*] Data written to file in " + seconds + " seconds.");
        System.out.println("[*] " + count + " records written to file.");
    }


    @Override
    public void addNewCommittedData(List<Student> newStudentCommit) {
        // Ensure the directory exists
        File directory = new File("src/transitions");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Overwrite the file with the current list of students
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/transitions/transitionAddNew.dat")))) {
            for (Student student : newStudentCommit) {
                outputStream.writeObject(student);
            }
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void deleteCommittedData(List<Student> newDelete) {
        // Ensure the directory exists
        File directory = new File("src/transitions");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Overwrite the file with the current list of students
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/transitions/transitionDelete.dat")))) {
            for (Student student : newDelete) {
                outputStream.writeObject(student);
            }
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void updateCommittedData(List<Student> newUpdate) {
        // Ensure the directory exists
        File directory = new File("src/transitions");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Overwrite the file with the current list of students
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/transitions/transitionUpdate.dat")))) {
            for (Student student : newUpdate) {
                outputStream.writeObject(student);
            }
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }
    @Override
    public void clearDataFile() {
        data.clear();
        // Ensure the directory exists
        File directory = new File("Data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Clear the file by overwriting it with an empty list
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data/data.bin")))) {
            // Writing nothing to the file will effectively clear it
            System.out.println("[+] Data file has been cleared.");
        } catch (IOException e) {
            System.out.println("[!] Error clearing data file: " + e.getMessage());
        }
    }
    @Override
    public void commitData() {
        List<Student> addNew = new ArrayList<>();
        List<Student> delete = new ArrayList<>();
        List<Student> update = new ArrayList<>();

        // Read data from files
        readDataFromFile("src/transitions/transitionAddNew.dat", addNew);
        readDataFromFile("src/transitions/transitionDelete.dat", delete);
        readDataFromFile("src/transitions/transitionUpdate.dat", update);

        int totalRecords = addNew.size() + delete.size() + update.size();

        if (totalRecords > 0) {
            System.out.print("> Commit changes now? [Y/N]: ");

                String response = scanner.nextLine().trim().toUpperCase();
                switch (response) {
                    case "Y":
                        processCommit(addNew, delete, update);
                        break;
                    case "N":
                        clearAllData(addNew, delete, update);
                        break;
                    default:
                        System.out.println("[!] Invalid response. No action taken.");
                }

        } else {
            System.out.println("[!] No data to commit.");
        }
    }

    private void processCommit(List<Student> addNew, List<Student> delete, List<Student> update) {
        if (!addNew.isEmpty()) {
            data.addAll(addNew);
            writeDataToFile();
            System.out.println("[+] Added new data records successfully.");
        }
        if (!delete.isEmpty()) {
            data.removeAll(delete);
            writeDataToFile();
            System.out.println("[+] Deleted data records successfully.");
        }
        if (!update.isEmpty()) {
            for (Student student : update) {
                data.removeIf(s -> s.getId().equals(student.getId()));
                data.add(student);
            }
            writeDataToFile();
            System.out.println("[+] Updated data records successfully.");
        }
        clearAllData(addNew, delete, update);
    }

    private void clearAllData(List<Student> addNew, List<Student> delete, List<Student> update) {
        addNew.clear();
        delete.clear();
        update.clear();
        clearFileContent("src/transitions/transitionAddNew.dat");
        clearFileContent("src/transitions/transitionDelete.dat");
        clearFileContent("src/transitions/transitionUpdate.dat");
    }

    private void readDataFromFile(String filePath, List<Student> list) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            while (true) {
                try {
                    Student student = (Student) inputStream.readObject();
                    if (student != null) {
                        list.add(student);
                    }
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[!] Error reading data from file: " + e.getMessage());
        }
    }

    private void clearFileContent(String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))) {
            // Writing an empty object stream to clear the file
            outputStream.writeObject(null);
        } catch (IOException e) {
            System.out.println("[!] Error clearing file content: " + e.getMessage());
        }
    }
}