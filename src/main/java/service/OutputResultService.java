package service;

import instance.Department;
import instance.Employee;
import instance.TransferGroup;

import java.util.Map;

public class OutputResultService {
    public static void outputCompanyMap(Map<String, Department> companyMap) {
        System.out.printf("%-15s  %-15s %n", "Department", "Average salary");
        for(Department department: companyMap.values()) {
            System.out.printf("%-15s %-15s %n", department.getName(), department.getAverageSalary());
        }
    }

    public static StringBuilder addMove(Department departmentFrom, Department departmentTo, Employee employee) {
        StringBuilder move = new StringBuilder();
        return move.append("Сотрудник ")
                .append(employee.getName())
                .append(" из отдела ")
                .append(departmentFrom.getName())
                .append(" может быть переведен в отдел ")
                .append(departmentTo.getName())
                .append(". Средняя зарплата в первом станет - ")
                .append(CalculationMoveEmployee.countNewDepartmentSalary(departmentFrom, employee, false))
                .append(", во втором - ")
                .append(CalculationMoveEmployee.countNewDepartmentSalary(departmentTo, employee, true))
                .append(".\n");
    }

    static StringBuilder addMove(TransferGroup transferGroup) {
        StringBuilder printSolution = new StringBuilder();
        if (transferGroup.getEmployees().size() == 1) {
            printSolution.append(addMove(transferGroup.getDepartmentFrom(),
                    transferGroup.getDepartmentTo(), transferGroup.getEmployees().get(0)));
        } else {
            printSolution.append("Сотрудники ");
            for (Employee employee : transferGroup.getEmployees()) {
                printSolution.append(employee.getName()).append(" ");
            }
            printSolution.append("из отдела ")
                    .append(transferGroup.getDepartmentFrom().getName())
                    .append(" могут быть переведены в отдел ")
                    .append(transferGroup.getDepartmentTo().getName())
                    .append(". Средняя зарплата в первом станет - ")
                    .append(transferGroup.getDepartmentFromNewSalary())
                    .append(", во втором - ")
                    .append(transferGroup.getDepartmentToNewSalary())
                    .append("\n");
        }
        return printSolution;
    }
}
