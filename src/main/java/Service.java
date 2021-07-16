import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class Service {

    public static void main(String[] args) {
        Company company = new Company();
        String filename = "src/main/employeeInfo.txt";
        try {
            Map<String, Department>  companyMap = uploadEmployeeFromFile(filename, company);
            outputCompanyMap(companyMap);
            StringBuilder result = getPossibleSolutions(companyMap);
            writeResultToFile(result);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Внимание! Ошибка: " + e);
        }
    }

    public static Map<String, Department> uploadEmployeeFromFile(String filename, Company company) throws IOException {
        Map<String, Department> companyMap = company.getCompanyMap();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] params = line.split(";");
            companyMap.putIfAbsent(params[1], new Department(params[1]));
            companyMap.get(params[1]).getEmployees().add(new Employee(params[0], BigDecimal.valueOf(Double.parseDouble(params[2]))));
        }
        return companyMap;
    }

    public static void outputCompanyMap(Map<String, Department> companyMap) {
        System.out.printf("%-15s  %-15s %n", "Department", "Average salary");
        for(Map.Entry<String, Department> department: companyMap.entrySet()) {
            System.out.printf("%-15s %-15s %n", department.getKey(), department.getValue().getAverageSalary());
        }
    }

    static StringBuilder getPossibleSolutions(Map<String, Department> companyMap) {
        StringBuilder result = new StringBuilder();
        ArrayList<Department> departments = new ArrayList<>(companyMap.values());
        for (int i = 0; i <= departments.size() - 1; i++) {
            Department departmentFrom = departments.get(i);
            for (int j = 0; j < departments.size() - 1; j++) {
                if (i != j) {
                    Department departmentTo = departments.get(j);
                    result.append(checkPossibleMove(departmentFrom, departmentTo));
                }
            }
        }
        return result;
    }

    static StringBuilder checkPossibleMove(Department departmentFrom, Department departmentTo) {
        StringBuilder move = new StringBuilder();
        ArrayList<Employee> employeesDepartmentFrom = new ArrayList<>(departmentFrom.getEmployees());
        for (Employee employee : employeesDepartmentFrom) {
            BigDecimal averageSalaryDepartmentToBefore = departmentTo.getAverageSalary();
            if (averageSalaryDepartmentToBefore.compareTo(employee.getSalary()) <= 0) {
                departmentTo.addEmployee(employee);
                if (averageSalaryDepartmentToBefore.compareTo(departmentTo.getAverageSalary()) < 0) {
                    BigDecimal averageSalaryDepartmentFromBefore = departmentFrom.getAverageSalary();
                    departmentFrom.removeEmployee(employee);
                    if (averageSalaryDepartmentFromBefore.compareTo(departmentFrom.getAverageSalary()) < 0) {
                        move.append("Сотрдник ")
                                .append(employee.getName())
                                .append(" из отдела ")
                                .append(departmentFrom.getName())
                                .append(" может быть переведен в отдел ")
                                .append(departmentTo.getName())
                                .append(". Средняя зарплата в первом станет - ")
                                .append(departmentFrom.getAverageSalary())
                                .append(", во втором - ")
                                .append(departmentTo.getAverageSalary())
                                .append(".\n");
                    }
                    departmentFrom.addEmployee(employee);
                }
                departmentTo.removeEmployee(employee);
            }
        }
        return move;
    }


    static void writeResultToFile(StringBuilder result) throws IOException {
        FileWriter writer = new FileWriter("result.txt");
        writer.write(String.valueOf(result));
        writer.flush();
    }
}
