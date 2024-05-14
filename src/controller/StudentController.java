package controller;

import model.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public interface StudentController {
    void writeToFile(int count);
    //List<Student> readFromFile();
    List<Student> readObjectsFromFile();
    void clearFile();
    void writeBatch(ObjectOutputStream oos, List<Student> batch);
    boolean getValidNumber();
    void pagination(List<Student> studentList, int row);
}
