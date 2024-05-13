package util;

import controller.StudentController;
import model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentControllerImp implements StudentController {
    private static final String FILE_PATH = "./Data/data.bin";
    long startTime = System.currentTimeMillis();
    @Override
    public void writeToFile(int count) {
        Student stu = new Student(1, "EI CHANUDOM", "21/02/2003", "WebDesign", "HTML");
        long currentTimeMillis = System.currentTimeMillis();
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH, true);
             ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos))) {

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
            long currentTimeMillis2 = System.currentTimeMillis();
            double time_seconds = (currentTimeMillis2 - currentTimeMillis) / 1000.0;

            System.out.println("Total time required for writing the data in seconds: " + time_seconds);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public List<Student> readFromFile() {
        List<Student> students = new ArrayList<>();
        int objectCount = 0;
        long startTime = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    // Read a batch of objects
                    List<Student> batch = readBatch(ois);
                    if (batch.isEmpty()) {
                        break; // End of file
                    }
                    students.addAll(batch);
                    objectCount += batch.size(); // Update object count
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println("Error reading from file: " + ex.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double timeInSeconds = (endTime - startTime) / 1000.0;
        System.out.println("Total objects read: " + objectCount);
        System.out.println("Total time required for reading the data in seconds: " + timeInSeconds);
        return students;
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

    private List<Student> readBatch(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        List<Student> batch = new ArrayList<>();
        int batchSize = 1000; // Ensure this matches your writeBatch method
        for (int i = 0; i < batchSize; i++) {
            try {
                Student student = (Student) ois.readObject();
                batch.add(student);
            } catch (EOFException e) {
                break; // End of file reached
            }
        }
        return batch;
    }

}
