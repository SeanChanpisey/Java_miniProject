package controller;

import model.Student;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public interface StudentController {
    void writeToFile(int count);
    List<Student> readFromFile();
    void clearFile();
    void writeBatch(ObjectOutputStream oos, List<Student> batch);
}
