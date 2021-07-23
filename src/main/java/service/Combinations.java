package service;

import instance.Department;
import instance.Employee;
import instance.TransferGroup;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Combinations {

    public static void getAllPossibleSolutions(Map<String, Department> companyMap, Path outputFilePath) {
        ArrayList<Department> departments = new ArrayList<>(companyMap.values());
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department departmentFrom = getRankDepartment(departments, i, j).get(0),
                        departmentTo = getRankDepartment(departments, i, j).get(1);
                for (int k = 1; k < departmentFrom.getEmployees().size(); k++) {
                    ArrayList<Employee> result = new ArrayList<>(
                            Collections.nCopies(k, new Employee("", new BigDecimal(0))));
                    getAllPossibleCombinations(departmentFrom.getEmployees(), k, 0,
                            new TransferGroup(result, departmentFrom, departmentTo), outputFilePath);
                }
            }
        }
    }

    public static ArrayList<Department> getRankDepartment(ArrayList<Department> departments, int i, int j) {
        ArrayList<Department> departmentArrayList = new ArrayList<>(2);
        Department departmentFrom, departmentTo;
        if (departments.get(i).getAverageSalary().compareTo(departments.get(j).getAverageSalary()) > 0) {
            departmentFrom = departments.get(i);
            departmentTo = departments.get(j);
        } else {
            departmentFrom = departments.get(j);
            departmentTo = departments.get(i);
        }
        departmentArrayList.add(0, departmentFrom);
        departmentArrayList.add(1, departmentTo);
        return departmentArrayList;
    }

    static void getAllPossibleCombinations(List<Employee> transferList, int length, int startPosition,
                                          TransferGroup transferGroup, Path outputFilePath) {
        if (length == 0) {
            if (isSolutionSuitable(transferGroup)){
                System.out.print(OutputResultService.addMove(transferGroup));
                OutputFileService.appendResultToFile(OutputResultService.addMove(transferGroup), outputFilePath);
            }
            return;
        }
        for (int i = startPosition; i <= transferList.size() - length; i++){
            transferGroup.getEmployees().set(transferGroup.getEmployees().size() - length, transferList.get(i));
            getAllPossibleCombinations(transferList, length - 1, i + 1, transferGroup, outputFilePath);
        }
    }

    static boolean isSolutionSuitable(TransferGroup transferGroup) {
        return  (transferGroup.getDepartmentFromNewSalary().compareTo(transferGroup.getDepartmentFrom().getAverageSalary()) > 0
                && transferGroup.getDepartmentToNewSalary().compareTo(transferGroup.getDepartmentTo().getAverageSalary()) > 0);
    }
}
