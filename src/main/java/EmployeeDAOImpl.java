import connectionutil.ConnectionSetter;
import entity.Employee;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO{
    @SneakyThrows
    public void dropTable(){
        String sql = "DROP TABLE employees";
       try( Connection connection = ConnectionSetter.setConnection()) {
          try ( Statement statement = connection.createStatement()) {
              statement.execute(sql);
          }
       }
    }
    @SneakyThrows
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS employees (\n" +
                "  id SERIAL PRIMARY KEY,\n" +
                "  first_name VARCHAR(100) NOT NULL,\n" +
                "  last_name VARCHAR(100) NOT NULL,\n"  +
                "  emp_age INTEGER NOT NULL,\n" +
                "  department VARCHAR(100) NOT NULL\n" +
                ");";
        Connection connection = ConnectionSetter.setConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    @SneakyThrows
    @Override
    public Employee getById(int id) {
        String sql = " SELECT * FROM EMPLOYEES WHERE id = ?";
        Employee employee = null;
        Connection connection = ConnectionSetter.setConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            int age = resultSet.getInt("emp_age");
            String depart = resultSet.getString("department");
            employee = new Employee(name,last_name,age,depart);
            employee.setId(id);
        }

        return employee;
    }
    @SneakyThrows
    @Override
    public List<Employee> getAll() {
        List <Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection connection = ConnectionSetter.setConnection()) {
            try ( Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String last_name = resultSet.getString(3);
                    int age = resultSet.getInt(4);
                    String dp = resultSet.getString(5);
                    Employee employee = new Employee(name,last_name,age,dp);
                    employee.setId(id);
                    list.add(employee);
                }
            }
        }
        return list;
    }

    @SneakyThrows
    @Override
    public void insert(Employee employee) {
        String sql = "INSERT INTO EMPLOYEES(first_name, last_name, emp_age, department) VALUES ( ?, ?, ?, ?) RETURNING id";
       try ( Connection connection = ConnectionSetter.setConnection()) {
          try( PreparedStatement ps = connection.prepareStatement(sql)) {
              ps.setString(1, employee.getFirst_name());
              ps.setString(2, employee.getLast_name());
              ps.setInt(3, employee.getAge());
              ps.setString(4, employee.getDepartment());
              ps.execute();
          }
       }
    }


    @SneakyThrows
    @Override
    public void update(Employee employee) {
        String sql = "UPDATE employees set first_name =?, last_name =?, emp_age =?, department =? where id =?";
        try(Connection connection = ConnectionSetter.setConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,employee.getFirst_name());
                ps.setString(2,employee.getLast_name());
                ps.setInt(3, employee.getAge());
                ps.setString(4,employee.getDepartment());
                ps.setInt(5, employee.getId());
                ps.execute();
            }
        }
    }

    @SneakyThrows
    @Override
    public void delete(int delid) {
        String sql ="DELETE FROM  employees  WHERE id =?";
        try(Connection connection = ConnectionSetter.setConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,delid);
                ps.execute();
            }
        }
    }
}
