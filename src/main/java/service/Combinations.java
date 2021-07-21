package service;

import instance.Company;
import instance.Department;
import instance.Employee;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Combinations {
    public static void main(String[] args){
        try {
            Company company = new Company();
            Path inputFilePath = Path.of(args[0]),
                    outputFilePath = Path.of(args[1]);
            Map<String, Department> companyMap = InputFileService.uploadEmployeeFromFile(inputFilePath, company);
            if (companyMap.size() != 0) {
                OutputResultService.outputCompanyMap(companyMap);
                getAllPossibleSolutions(companyMap);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Внимание! Укажите путь к обоим файлам во входных аргументах(args[0] и args[1])");
        }
    }

    public static void getAllPossibleSolutions(Map<String, Department> companyMap) {
        ArrayList<Department> departments = new ArrayList<>(companyMap.values());
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department departmentFrom, departmentTo;
                if (departments.get(i).getAverageSalary().compareTo(departments.get(j).getAverageSalary()) > 0) {
                    departmentFrom = departments.get(i);
                    departmentTo = departments.get(j);
                } else {
                    departmentFrom = departments.get(j);
                    departmentTo = departments.get(i);
                }
                for (int l = 1; l < departmentFrom.getEmployees().size(); l++) {
                    ArrayList<Employee> temp = new ArrayList<>(l);
                    for (int o = 0; o < l; o++) {
                        temp.add(new Employee("E", new BigDecimal(0)));
                    }
                    getAllPossibleCombinations(departmentFrom.getEmployees(), l, 0,
                            temp, departmentFrom, departmentTo);
                }

            }
        }
    }

    static void getAllPossibleCombinations(List<Employee> arrayList, int length, int startPosition,
                                           List<Employee> result, Department departmentFrom, Department departmentTo){
        if (length == 0) {
            if (checkSolution(result, departmentFrom, departmentTo)){
                printSolution(result, departmentFrom, departmentTo);
            }
            return;
        }
        for (int i = startPosition; i <= arrayList.size() - length; i++){
            result.set(result.size() - length, arrayList.get(i));
            getAllPossibleCombinations(arrayList, length - 1, i + 1, result, departmentFrom, departmentTo);
        }

    }
    static boolean checkSolution(List<Employee> result, Department departmentFrom, Department departmentTo) {
        return  (countNewDepartmentSalary(departmentFrom, result, false).compareTo(departmentFrom.getAverageSalary()) > 0
                && countNewDepartmentSalary(departmentTo, result, true).compareTo(departmentTo.getAverageSalary()) > 0);
    }

    public static BigDecimal countNewDepartmentSalary(Department department, List<Employee> result, boolean sign) {
        BigDecimal totalSalary = department.getTotalSalary();
        BigDecimal totalListSalary = new BigDecimal(0);
        for (Employee employee: result) {
            totalListSalary = totalListSalary.add(employee.getSalary());
        }
        totalSalary = sign ? totalSalary.add(totalListSalary) : totalSalary.subtract(totalListSalary);
        return totalSalary.divide(new BigDecimal(department.getEmployees().size() + (sign ? result.size() : -result.size())),
                2, RoundingMode.HALF_UP);
    }

    static void printSolution(List<Employee> result, Department departmentFrom, Department departmentTo) {
        StringBuilder stringBuilder = new StringBuilder();
        if (result.size() > 1) {
            stringBuilder.append("Сотрудники ");
        } else {
            stringBuilder.append("Сотрудник ");
        }
        for (Employee employee : result) {
            stringBuilder.append(employee.getName()).append(" ");
        }
        stringBuilder.append("из отдела ").append(departmentFrom.getName());
        if (result.size() > 1) {
            stringBuilder.append(" могут быть переведены в отдел ");
        } else {
            stringBuilder.append(" может быть переведен в отдел ");
        }
        stringBuilder.append(departmentTo.getName())
                .append(". Средняя зарплата в первом станет - ")
                .append(countNewDepartmentSalary(departmentFrom, result, false))
                .append(", во втором - ")
                .append(countNewDepartmentSalary(departmentTo, result, true));

        System.out.println(stringBuilder);
    }
}
