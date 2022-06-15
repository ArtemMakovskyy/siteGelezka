package com.example.mag0422.excel.example;


import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public static List<Employee> listEmployees() {
        List<Employee> list = new ArrayList<Employee>();

        Employee e1 = new Employee("E01", "Tom", 200.0, 1, null);
        Employee e2 = new Employee("E02", "Jerry", 100.2, 2, null);
        Employee e3 = new Employee("E03", "Donald", 150.0, 2, null);
        list.add(e1);
        list.add(e2);
        list.add(e3);
        return list;
    }

}