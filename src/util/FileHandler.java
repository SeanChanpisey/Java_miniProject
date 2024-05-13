package util;

import model.Student;

import java.io.*;
import java.util.*;

public class FileHandler {
    private static void writeBatch(ObjectOutputStream oos, List<Student> batch) throws IOException {
        for (Student student : batch) {
            oos.writeObject(student);
        }
    }
    public static void main(String[] args) {
        Student stu = new Student(1, "EI CHANUDOM", "21/02/2003", "WebDesign", "HTML,CSS,JAVASCRIPT");
        long currentTimeMillis = System.currentTimeMillis();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Data/Data.bin")))) {
            List<Student> studentBatch = new ArrayList<>();
            int batchSize =1000;

            for (int i = 0; i < 10000000; i++) {
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
}
