package entity;

import lombok.Data;

@Data
public class Employee {
    private  int id;
    private String first_name;
    private String last_name;
    private int age;
    private String department;

    public Employee(String first_name, String last_name, int age, String department) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.department = department;
    }
}
