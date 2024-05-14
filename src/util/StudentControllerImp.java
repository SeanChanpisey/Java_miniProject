package util;

import controller.StudentController;
import model.Student;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StudentControllerImp implements StudentController {
    private static final String FILE_PATH = "./Data/data.bin";
    long startTime = System.currentTimeMillis();
    @Override
    public void writeToFile(int count) {
        Student stu = new Student(1, "EI CHANUDOM", "21/02/2003", "WebDesign", "HTML");
        long currentTimeMillis = System.currentTimeMillis();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data/data.bin")))) {

            List<Student> studentBatch = new ArrayList<>();
            int batchSize = 1000;
            for (int i = 0; i < count; i++) {
                studentBatch.add(stu);
                if (studentBatch.size() >= batchSize) {
                    writeBatch(oos, studentBatch);
                    studentBatch.clear();
                }
            }
            // Write remaining objects if any
            if (!studentBatch.isEmpty()) {
                writeBatch(oos, studentBatch);
            }
            oos.flush();
            oos.close();
            long currentTimeMillis2 = System.currentTimeMillis();
            double time_seconds = (currentTimeMillis2 - currentTimeMillis) / 1000.0;

            System.out.println("Total time required for writing the data in seconds: " + time_seconds);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public List<Student> readObjectsFromFile() {
        List<Student> objects = new ArrayList<>();
        int count = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)))) {
            while (true) {
                List<Student> batch = readBatch(ois);
                if (batch.isEmpty()) {
                    break; // No more data to read
                }
                objects.addAll(batch);
                count += batch.size();
            }
            long endTime = System.currentTimeMillis();
            double timeInSeconds = (endTime - startTime) / 1000.0;
            System.out.println("Time taken for reading: " + timeInSeconds + " seconds");
            System.out.println("Number of objects read: " + count);
            System.out.println("Objects read from file with batch processing successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }



    private static List<Student> readBatch(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        List<Student> batch = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            try {
                Student obj = (Student) ois.readObject();
                batch.add(obj);
            } catch (EOFException e) {
                // End of file reached
                break;
            }
        }
        return batch;
    }

    @Override
    public void clearFile() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            System.out.println("Data cleared from the file.");
        } catch (IOException e) {
            System.err.println("Error clearing data from file: " + e.getMessage());
        }
    }

    @Override
    public void writeBatch(ObjectOutputStream oos, List<Student> batch) {
        try{
            for (Student student : batch) {
                oos.writeObject(student);
            }
        }catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public boolean getValidNumber() {
        if (new Scanner(System.in).hasNextInt()) {
            return true;
        } else {
            System.out.print("Invalid input. Please enter a number: ");
            new Scanner(System.in).next(); // discard the invalid input
            return false;
        }
    }

    @Override
    public void pagination(List<Student> studentList, int row){
        int a = 0, page = 1;
        do {
            int data = 0;
            String input;
            System.out.println("[*] Student's data".toUpperCase());
            Table table = new Table(5, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
            table.setColumnWidth(0, 22, 22);
            table.setColumnWidth(1, 25, 25);
            table.setColumnWidth(2, 25, 25);
            table.setColumnWidth(3, 30, 30);
            table.setColumnWidth(4, 30, 30);
            //Head table
            table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Name".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's Date of Birth".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student class".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            table.addCell("Student's subject".toUpperCase(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            //Body table
            if(a+row < studentList.size()) {
                for (int i = a; i < a + row; i++) {
                    data += 1;
                    TableStudent.printTableStudent(studentList, i, table);
                }
            }
            else{
                for (int i = a; i < studentList.size(); i++) {
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
            if(input.equals("n") || input.equals("next")) {
                if (a + row < studentList.size()) {
                    a += row;
                    page += 1;
                } else {
                    System.out.println("[!] Last Page <<".toUpperCase());
                }
            } else if (input.equals("p") || input.equals("previous")) {
                if (a > 0) {
                    a -= row;
                    page -= 1;
                } else {
                    System.out.println("[!] First Page >>".toUpperCase());
                }
            } else if (input.equals("b") || input.equals("back")) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }
        while(true);
    }
}
