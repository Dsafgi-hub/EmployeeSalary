package service;

import instance.Department;
import instance.Employee;
import instance.TransferGroup;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Combinations {

    public static void getAllPossibleSolutions(Map<String, Department> companyMap, BufferedWriter writer)
    throws IOException {
        ArrayList<Department> departments = new ArrayList<>(companyMap.values());
        departmentSortDesc(departments);
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i + 1; j < departments.size(); j++) {
                Department departmentFrom = departments.get(i),
                        departmentTo = departments.get(j);
                if (departmentFrom.getAverageSalary().compareTo(departmentTo.getAverageSalary()) != 0) {
                    for (int k = 1; k < departmentFrom.getEmployees().size(); k++) {
                        ArrayList<Employee> result = new ArrayList<>(
                                Collections.nCopies(k, new Employee("", new BigDecimal(0))));
                        getAllPossibleCombinations(departmentFrom.getEmployees(), k, 0,
                                new TransferGroup(result, departmentFrom, departmentTo), writer);
                    }
                }
            }
        }
    }

    static void departmentSortDesc(ArrayList<Department> departments) {
          departments.sort((departmentFirst, departmentSecond) ->
                Integer.compare(0, departmentFirst.getAverageSalary().compareTo(departmentSecond.getAverageSalary())));
    }

    static void getAllPossibleCombinations(List<Employee> transferList, int length, int startPosition,
                                          TransferGroup transferGroup, BufferedWriter writer) throws IOException {
        if (length == 0) {
            if (isSolutionSuitable(transferGroup)){
                System.out.print(OutputResultService.addMove(transferGroup));
                writer.append(OutputResultService.addMove(transferGroup));
            }
            return;
        }
        for (int i = startPosition; i <= transferList.size() - length; i++){
            transferGroup.getEmployees().set(transferGroup.getEmployees().size() - length, transferList.get(i));
            getAllPossibleCombinations(transferList, length - 1, i + 1, transferGroup, writer);
        }
    }

    static boolean isSolutionSuitable(TransferGroup transferGroup) {
        return  (transferGroup.getDepartmentFromNewSalary().compareTo(transferGroup.getDepartmentFrom().getAverageSalary()) > 0
                && transferGroup.getDepartmentToNewSalary().compareTo(transferGroup.getDepartmentTo().getAverageSalary()) > 0);
    }



}
