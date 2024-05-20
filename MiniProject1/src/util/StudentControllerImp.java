package util;
import controller.StudentController;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
public class StudentControllerImp implements StudentController {
    Scanner scanner = new Scanner(System.in);
    List<Student> data = new ArrayList<>();
    List<Student> addNew = new ArrayList<>();
    List<Student> delete = new ArrayList<>();
    List<Student> update = new ArrayList<>();
    @Override
    public void pagination(List<Student> studentList, int row) {
        int a = studentList.size() - 1; // Start from the last index
        int page = 1;

        do {
            int data = 0;
            String input;
            System.out.println("[*] Student's data".toUpperCase());

            // Initialize the table with appropriate styling
            Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
            table.setColumnWidth(0, 22, 22);
            table.setColumnWidth(1, 25, 25);
            table.setColumnWidth(2, 25, 25);
            table.setColumnWidth(3, 30, 30);
            table.setColumnWidth(4, 30, 30);
            table.setColumnWidth(5, 25, 25);

            // Add the header to the table
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Name".toUpperCase(),
                    new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Date of Birth".toUpperCase(),
                    new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student class".toUpperCase(),
                    new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's subject".toUpperCase(),
                    new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Created/Update at".toUpperCase(),
                    new CellStyle(CellStyle.HorizontalAlign.CENTER));

            // Populate the table with data
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

            // Render and display the table
            System.out.println(table.render());
            System.out.println("-".repeat(165));
            System.out.println(" Page Number: " + page + "\t [*] Actual record: "
                    + data + "\t [*] All Records: " + studentList.size()
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
                    System.out.println(
                            "[Error] Name must not contain numbers. Please enter a valid name.");
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

            System.out.println(
                    "[!] YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2).");
            System.out.print("[+] Students's class: ");
            String classesInput = scanner.nextLine();
            String[] classes = Arrays.stream(classesInput.split(","))
                    .map(String::trim)
                    .toArray(String[] ::new);

            System.out.println(
                    "[!] YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
            System.out.print("[+] Subject studied: ");
            String subjectsInput = scanner.nextLine();
            String[] subjects = Arrays.stream(subjectsInput.split(","))
                    .map(String::trim)
                    .toArray(String[] ::new);

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
            addNew.add(newStudent);
            addNewCommittedData();
        } catch (Exception e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    @Override
    public void writeDataToFile() {
        // Overwrite the file with the current list of students
        try (FileOutputStream fos = new FileOutputStream("Data/data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(
                     new BufferedOutputStream(fos, 8 * 1024 * 1024))) {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void showList() {
        int pageSize = 0;
        if (!addNew.isEmpty()) {
            pageSize = 4;
            readDataFromFile("src/transitions/transitionAddNew.dat", addNew);
            List<Student> combine = new ArrayList<>(data);
            combine.addAll(addNew);
            pagination(combine, pageSize);
        }
        else if(!data.isEmpty()){
            pageSize = 4;
            pagination(data, pageSize);
        }
        else {
            pagination(data, pageSize);
        }
    }

    @Override
    public void readDataWhenLoad() {
        long readStartTime = System.currentTimeMillis();

        try (
                FileInputStream fis = new FileInputStream("Data/data.bin");
                BufferedInputStream bis = new BufferedInputStream(fis, 8 * 1024 * 1024);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object readObject = ois.readObject();
            if (readObject == null) {
                System.out.println("[*] NUMBER OF RECORD IN DATA SOURCE FILE: 0");
            } else {
                data = (List<Student>) readObject;
                System.out.println(
                        "[*] NUMBER OF RECORD IN DATA SOURCE FILE: " + data.size());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            System.out.println("[*] NUMBER OF RECORD IN DATA SOURCE FILE: 0");
        } catch (EOFException e) {
            System.out.println("[*] NUMBER OF RECORD IN DATA SOURCE FILE: 0");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.out.println("[*] NUMBER OF RECORD IN DATA SOURCE FILE: 0");
        } finally {
            long readEndTime = System.currentTimeMillis();
            double readTimeSeconds = (readEndTime - readStartTime) / 1000.0;
            System.out.println(
                    "[*] SPENT TIME FOR READING DATA: " + readTimeSeconds + " seconds");
        }
    }

    // Method to generate student ID
    private String generateStudentId() {
        Random random = new Random();
        int number = random.nextInt(10000); // Generates a number between 0 and 9999
        String formattedNumber =
                String.format("%04d", number); // Ensures the number is 4 digits with
        // leading zeros if necessary
        return formattedNumber + "CSTAD";
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
        Table table =
                new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        table.setColumnWidth(0, 25, 25);
        table.setColumnWidth(1, 35, 35);

        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getId(),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getName(),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getBirthYear() + "-" + student.getBirthMonth() + "-"
                        + student.getBirthDay(),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getClasses()),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell("SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getSubjects()),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        table.addCell(
                "CREATED/UPDATE AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getCurrentDate(),
                new CellStyle(
                        CellStyle.HorizontalAlign.CENTER)); // Center-align the content

        System.out.println(table.render());
    }
    @Override
    public void searchStudentDetails() {
        System.out.println("[+] SEARCHING STUDENT");
        System.out.println(".........................................");
        System.out.println("1. SEARCH BY NAME");
        System.out.println("2. SEARCH BY ID");
        System.out.println("- (BACK/B) TO BACK");
        System.out.println(".........................................");
        System.out.print(">> Insert option: ");
        String searchOption =
                new Scanner(System.in).nextLine().trim().toLowerCase();
        switch (searchOption) {
            case "1":
                System.out.println("[*] SEARCH STUDENT BY NAME");
                System.out.print(">>> Insert student's name: ");
                String partialName = new Scanner(System.in).nextLine().trim();
                List<Student> matchingStudents =
                        searchStudentsByPartialName(partialName);
                if (!matchingStudents.isEmpty()) {
                    System.out.println("Found " + matchingStudents.size()
                            + " student(s) with names containing '" + partialName + "':");
                    for (Student student : matchingStudents) {
                        displayStudentDetails(student);
                    }
                } else {
                    System.out.println(
                            "No students found with names containing '" + partialName + "'.");
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

    @Override
    public void updateStudentDetails() {
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
                    update.add(studentToUpdate);
                    updateCommittedData();
                    System.out.println(
                            "[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
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
                            new Scanner(System.in).nextLine(); // Clear the invalid input from
                            // the scanner buffer
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
                            new Scanner(System.in).nextLine(); // Clear the invalid input from
                            // the scanner buffer
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
                            new Scanner(System.in).nextLine(); // Clear the invalid input from
                            // the scanner buffer
                        }
                    } while (birthDay <= 0 || birthDay > 31);

                    // Set the new date of birth in the student object
                    studentToUpdate.setBirthYear(birthYear);
                    studentToUpdate.setBirthMonth(birthMonth);
                    studentToUpdate.setBirthDay(birthDay);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    update.add(studentToUpdate);
                    updateCommittedData();
                    System.out.println(
                            "[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;
                case 3:
                    // Update class
                    System.out.println(
                            "[!] YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1, C2).");
                    System.out.print("[+] Students's class: ");
                    String classesInput = new Scanner(System.in).nextLine();
                    String[] classes = Arrays.stream(classesInput.split(","))
                            .map(String::trim)
                            .toArray(String[] ::new);

                    // Set the new classes in the student object
                    studentToUpdate.setClasses(classes);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    update.add(studentToUpdate);
                    updateCommittedData();
                    System.out.println(
                            "[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    break;

                case 4:
                    // Update subject
                    System.out.println(
                            "[!] YOU CAN INSERT MULTI SUBJECTS BY SPLITTING [,] SYMBOL (S1, S2).");
                    System.out.print("[+] Subject studied: ");
                    String subjectsInput = new Scanner(System.in).nextLine();
                    String[] subjects = Arrays.stream(subjectsInput.split(","))
                            .map(String::trim)
                            .toArray(String[] ::new);

                    // Set the new subjects in the student object
                    studentToUpdate.setSubjects(subjects);

                    System.out.println("..........................");
                    System.out.println("[*] STUDENT'S INFO.");
                    displayStudentDetails(studentToUpdate);
                    update.add(studentToUpdate);
                    updateCommittedData();
                    System.out.println(
                            "[+] UPDATE SUCCESSFULLY, PRESS ENTER TO CONTINUE...");
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
        System.out.println("[*] DELETE STUDENT BY ID");
        System.out.print(">> Insert student's ID: ");
        String studentIdToDelete = new Scanner(System.in).nextLine().trim();
        Student studentToDelete = searchStudentById(studentIdToDelete);
        if (studentToDelete != null) {
            System.out.println("[*] STUDENT'S INFO.");
            displayStudentDetails(studentToDelete);
            System.out.print(
                    "[+] Are you sure to delete the student information [Y/n]: ");
            String confirmDelete =
                    new Scanner(System.in).nextLine().trim().toLowerCase();
            if (confirmDelete.equals("y")) {
                delete.add(studentToDelete);
                deleteCommittedData();
                data.removeAll(delete);
                System.out.println(
                        "[+] User data has been deleted temporarily successfully.");
            } else {
                System.out.println("[+] Delete operation aborted.");
            }
        } else {
            System.out.println("[!] Student not found with ID: " + studentIdToDelete);
        }
    }
    @Override
    public void writeDataToFileAsync(int count) {
        long writeStartTime = System.currentTimeMillis();

        try (FileOutputStream fos = new FileOutputStream("Data/data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(
                     new BufferedOutputStream(fos, 8 * 1024 * 1024))) {
            Student stu = new Student("0000CSTAD", "UDOM", 21, 2, 2003,
                    new String[] {"WEB DESIGN"}, new String[] {"HTML"},
                    LocalDate.now().toString());
            for (int i = 0; i < count; i++) {
                data.add(stu);
            }

            oos.writeObject(data);
            oos.flush();

            long writeEndTime = System.currentTimeMillis();
            double writeTimeSeconds = (writeEndTime - writeStartTime) / 1000.0;
            System.out.println(
                    "Time taken for writing data: " + writeTimeSeconds + " seconds");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void addNewCommittedData() {
        // Overwrite the file with the current list of students
        try (FileOutputStream fos =
                     new FileOutputStream("src/transitions/transitionAddNew.dat");
             ObjectOutputStream oos = new ObjectOutputStream(
                     new BufferedOutputStream(fos, 8 * 1024 * 1024))) {
            oos.writeObject(addNew);
        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void deleteCommittedData() {
        // Overwrite the file with the current list of students
        try (FileOutputStream fos =
                     new FileOutputStream("src/transitions/transitionDelete.dat");
             ObjectOutputStream oos = new ObjectOutputStream(
                     new BufferedOutputStream(fos, 8 * 1024 * 1024))) {
            oos.writeObject(delete);

        } catch (IOException e) {
            System.out.println("[!] Error writing data to file: " + e.getMessage());
        }
    }

    @Override
    public void updateCommittedData() {
        // Overwrite the file with the current list of students
        try (FileOutputStream fos =
                     new FileOutputStream("src/transitions/transitionUpdate.dat");
             ObjectOutputStream oos = new ObjectOutputStream(
                     new BufferedOutputStream(fos, 8 * 1024 * 1024))) {
            oos.writeObject(update);
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("Data/data.bin")))) {
            // Writing nothing to the file will effectively clear it
            System.out.println("[+] Data file has been cleared.");
        } catch (IOException e) {
            System.out.println("[!] Error clearing data file: " + e.getMessage());
        }
    }
    @Override
    public void commitData() {
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

    private void processCommit(
            List<Student> addNew, List<Student> delete, List<Student> update) {
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

    private void clearAllData(
            List<Student> addNew, List<Student> delete, List<Student> update) {
        addNew.clear();
        delete.clear();
        update.clear();
        clearFileContent("src/transitions/transitionAddNew.dat");
        clearFileContent("src/transitions/transitionDelete.dat");
        clearFileContent("src/transitions/transitionUpdate.dat");
    }

    private void readDataFromFile(String filePath, List<Student> list) {
        try (
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis, 8 * 1024 * 1024);
                ObjectInputStream ois = new ObjectInputStream(bis)) {
            // Read the entire list at once
            Object readObject = ois.readObject();
            if (readObject != null) {
                list.clear();
                list.addAll((List<Student>) readObject);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[!] Error reading data from file: " + e.getMessage());
        }
    }

    private void clearFileContent(String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filePath)))) {
            // Writing an empty object stream to clear the file
            outputStream.writeObject(null);
        } catch (IOException e) {
            System.out.println("[!] Error clearing file content: " + e.getMessage());
        }
    }
}