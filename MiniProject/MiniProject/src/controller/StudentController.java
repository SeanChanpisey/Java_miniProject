package controller;

import model.Student;
import java.util.List;

public interface StudentController {
    void pagination(List<Student> studentList, int row);
    void addNewStudent();
    void writeDataToFile();
    void showList();
    void readDataWhenLoad();
    Student searchStudentById(String id);
    void searchStudentDetails();
    void updateStudentDetails();
    void deleteStudentDetails();
    void writeDataToFileAsync(int count);
    void addNewCommittedData(List<Student> newStudentCommit);
    void deleteCommittedData(List<Student> newDelete);
    void updateCommittedData(List<Student> newUpdate);
    void clearDataFile();
    void commitData();
}
