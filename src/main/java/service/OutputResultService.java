package service;

import instance.Department;

import java.util.Map;

public class OutputResultService {
    public static void outputCompanyMap(Map<String, Department> companyMap) {
        System.out.printf("%-15s  %-15s %n", "Department", "Average salary");
        for(Department department: companyMap.values()) {
            System.out.printf("%-15s %-15s %n", department.getName(), department.getAverageSalary());
        }
    }
}
