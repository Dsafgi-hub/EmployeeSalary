package Instance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAverageSalary() {
        if (employees.size() > 0) {
            return getTotalSalary().divide(new BigDecimal(employees.size()),2, RoundingMode.HALF_UP);
        }
        else return BigDecimal.valueOf(0);

    }

    public BigDecimal getTotalSalary() {
        BigDecimal totalSalary = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        for(Employee employee: employees){
            totalSalary = totalSalary.add(employee.getSalary());
        }
        return totalSalary;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
}
