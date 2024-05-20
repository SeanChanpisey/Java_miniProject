package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private String id;
    private String name;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private String[] classes;
    private String[] subjects;
    private String currentDate;

}