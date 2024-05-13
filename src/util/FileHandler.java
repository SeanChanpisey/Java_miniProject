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

    }
}
