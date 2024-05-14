package util;

import model.Student;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Arrays;
import java.util.List;

public class TableStudent {
    public static void printTableStudent(List<Student> studentList, int i, Table table) {
        Student student = studentList.get(i);
        String birthDate = student.getDateOfBirth();

        table.addCell(String.valueOf(student.getId()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(birthDate, new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getStudentClass(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getSubject(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
    }
}
