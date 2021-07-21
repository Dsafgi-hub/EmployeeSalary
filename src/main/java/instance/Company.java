package instance;

import java.util.HashMap;
import java.util.Map;

public class Company {
    private Map<String, Department> companyMap;

    public Company() {
        this.companyMap = new HashMap<>();
    }

    public Map<String, Department> getCompanyMap() {
        return companyMap;
    }
}
