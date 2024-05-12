package util;

import model.Student;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static void main(String[] args) {
        Student stu = new Student(1, "udom" ,"21/02/2003","WebDesign","HTML,CSS,JAVASCRIPT");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("test.bin"));
            String stuData = stu.toString();
            for (int i = 1; i <= 100000; i++) {
                bw.write(stuData + " " + i + "\n");

            }
            bw.close();
            long currentTimeMillis2 = System.currentTimeMillis();
            double time_seconds = (currentTimeMillis2 - currentTimeMillis) / 1000.0;
            System.out.println("Total time required for Writing the text in seconds " + time_seconds);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
