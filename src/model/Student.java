package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private Integer id;
    private String name;
    private String dateOfBirth;
    private String studentClass;
    private String subject;
}