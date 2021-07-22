package instance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TransferGroup {
    private List<Employee> employees;
    private Department departmentFrom;
    private Department departmentTo;

    public TransferGroup(List<Employee> employees, Department departmentFrom, Department departmentTo) {
        this.employees = employees;
        this.departmentFrom = departmentFrom;
        this.departmentTo = departmentTo;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Department getDepartmentFrom() {
        return departmentFrom;
    }

    public Department getDepartmentTo() {
        return departmentTo;
    }

    public BigDecimal getDepartmentFromNewSalary() {
        return countNewDepartmentSalary(departmentFrom, employees, false);
    }

    public BigDecimal getDepartmentToNewSalary() {
        return countNewDepartmentSalary(departmentTo, employees, true);
    }

    private BigDecimal getEmployeeTotalSalary() {
        BigDecimal totalEmployeeSalary = new BigDecimal(0);
        for (Employee employee: employees) {
            totalEmployeeSalary = totalEmployeeSalary.add(employee.getSalary());
        }
        return totalEmployeeSalary;
    }

    private BigDecimal countNewDepartmentSalary(Department department, List<Employee> employees, boolean sign) {
        BigDecimal totalSalary = department.getTotalSalary();
        BigDecimal totalListSalary = getEmployeeTotalSalary();
        totalSalary = sign ? totalSalary.add(totalListSalary) : totalSalary.subtract(totalListSalary);
        return totalSalary.divide(new BigDecimal(department.getEmployees().size() + (sign ? employees.size() : -employees.size())),
                2, RoundingMode.HALF_UP);
    }

}
