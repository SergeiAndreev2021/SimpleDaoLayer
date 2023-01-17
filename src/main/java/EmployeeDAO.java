import entity.Employee;

import java.util.List;

public interface EmployeeDAO extends DAOInterface<Employee>{
   public List<Employee> getListByAgeAboveNumber( int number);
}
