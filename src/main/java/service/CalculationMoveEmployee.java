package service;

import instance.Department;
import instance.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculationMoveEmployee {
    static StringBuilder getPossibleSolutions(Map<String, Department> companyMap) {
        StringBuilder result = new StringBuilder();
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
                result.append(checkPossibleMove(departmentFrom, departmentTo));
            }
        }
        return result;
    }

    static StringBuilder checkPossibleMove(Department departmentFrom, Department departmentTo) {
        StringBuilder move = new StringBuilder();
        for (Employee employee : departmentFrom.getEmployees()) {
            if ((departmentTo.getAverageSalary().compareTo(employee.getSalary()) < 0)
                    && (departmentFrom.getAverageSalary().compareTo(employee.getSalary()) > 0)) {
                move.append(addMove(departmentFrom, departmentTo, employee));
            }
        }
        return move;
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
                .append(countNewDepartmentSalary(departmentFrom, employee, false))
                .append(", во втором - ")
                .append(countNewDepartmentSalary(departmentTo, employee, true))
                .append(".\n");
    }

    public static BigDecimal countNewDepartmentSalary(Department department, Employee employee, boolean sign) {
        BigDecimal totalSalary = department.getTotalSalary();
        totalSalary = sign ? totalSalary.add(employee.getSalary()) : totalSalary.subtract(employee.getSalary());
        return totalSalary.divide(new BigDecimal(department.getEmployees().size() + (sign ? 1 : -1)),2, RoundingMode.HALF_UP);
    }
}
