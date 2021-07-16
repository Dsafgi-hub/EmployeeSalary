import java.util.HashMap;

public class Company {
    private HashMap<String, Department> companyMap;

    public Company() {
        this.companyMap = new HashMap<>();
    }

    public HashMap<String, Department> getCompanyMap() {
        return companyMap;
    }

    public boolean addDepartment(Department department){
        if(!companyMap.containsKey(department.getName())){
            companyMap.put(department.getName(), department);
            return true;
        } else {
            return false;
        }
    }
}
