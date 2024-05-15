package util;

import model.Student;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
public class TableStudent {

    public static void printTableStudent (List < Student > studentList,int i, Table table){
        Student student = studentList.get(i);
        String birthDate = student.getBirthYear()
                + "-" + student.getBirthMonth()
                + "-" + student.getBirthDay();
        table.addCell(student.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(student.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(birthDate, new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(Arrays.toString(student.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell(String.valueOf(LocalDate.now()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
    }
}