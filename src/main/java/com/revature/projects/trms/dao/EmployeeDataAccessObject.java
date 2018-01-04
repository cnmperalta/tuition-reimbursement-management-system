package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.Department;
import com.revature.projects.trms.beans.Employee;
import com.revature.projects.trms.beans.EmployeeType;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDataAccessObject extends GenericDataAccessObject<Employee> {
  private HashMap<Integer, String> employeeTypes;
  private HashMap<String, Integer> employeeTypesReversed;
  private List<Department> departments;

  public EmployeeDataAccessObject() {
    super();
    employeeTypes = new HashMap<Integer, String>();
    employeeTypesReversed = new HashMap<String, Integer>();
    init();
  }

  private void init() {
    List<EmployeeType> employeeTypeList = new LinkedList<EmployeeType>();
    EmployeeTypeDataAccessObject etDao = new EmployeeTypeDataAccessObject();

    employeeTypeList = etDao.getAll();
    for(EmployeeType employeeType : employeeTypeList) {
      employeeTypes.put(employeeType.getEmployeeTypeId(), employeeType.getEmployeeType());
      employeeTypesReversed.put(employeeType.getEmployeeType(), employeeType.getEmployeeTypeId());
    }
  }

  private void populateList(List<Employee> employees, ResultSet rs) throws SQLException {
    while(rs.next()) {
      int employeeId = rs.getInt("EmployeeID");
      String firstName = rs.getString("FirstName");
      String lastName = rs.getString("LastName");
      String emailAddress = rs.getString("EmailAddress");
      String password = rs.getString("Password");
      String salt = rs.getString("Salt");
      ZonedDateTime lastLogin = rs.getObject("LastLogin", ZonedDateTime.class);
      int directSupervisorId = rs.getInt("DirectSupervisorID");
      int departmentId = rs.getInt("DepartmentID");
      int employeeTypeId = rs.getInt("EmployeeTypeID");
      
      employees.add(new Employee(
        employeeId, 
        firstName, 
        lastName, 
        emailAddress, 
        password, 
        salt, 
        directSupervisorId, 
        departmentId, 
        lastLogin, 
        employeeTypeId));
    }
  }

	@Override
	public List<Employee> getAll() {
    List<Employee> employees = new LinkedList<Employee>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Employee";

      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      populateList(employees, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for getting all Employees.");
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        System.err.println("Error closing ResultSet for getting all Employees.");
      }
    }

    return employees;
	}

	@Override
	public Employee getById(int id) {
    Employee employee = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Employee where EmployeeID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();
      
      if(rs.next()) {
        int employeeId = rs.getInt("EmployeeID");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String emailAddress = rs.getString("EmailAddress");
        String password = rs.getString("Password");
        String salt = rs.getString("Salt");
        ZonedDateTime lastLogin = rs.getObject("LastLogin", ZonedDateTime.class);
        int directSupervisorId = rs.getInt("DirectSupervisorID");
        int departmentId = rs.getInt("DepartmentID");
        int employeeTypeId = rs.getInt("EmployeeTypeID");

        employee = new Employee(employeeId, firstName, lastName, emailAddress, password, salt, directSupervisorId, departmentId, lastLogin, employeeTypeId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for getting all Employees.");
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        System.err.println("Error closing ResultSet for getting all Employees.");
      }
    }

		return employee;
	}

  public Employee getByEmailAddress(String emailAddress) {
    Employee employee = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Employee where EmailAddress=?";
      ps = connection.prepareStatement(sql);
      ps.setString(1, emailAddress);
      rs = ps.executeQuery();

      if(rs.next()) {
        int employeeId = rs.getInt("EmployeeID");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        // String emailAddress = rs.getString("EmailAddress");
        String password = rs.getString("Password");
        String salt = rs.getString("Salt");
        ZonedDateTime lastLogin = rs.getObject("LastLogin", ZonedDateTime.class);
        int directSupervisorId = rs.getInt("DirectSupervisorID");
        int departmentId = rs.getInt("DepartmentID");
        int employeeTypeId = rs.getInt("EmployeeTypeID");

        employee = new Employee(employeeId, firstName, lastName, emailAddress, password, salt, directSupervisorId, departmentId, lastLogin, employeeTypeId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for getting Employee by Email Address.");
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        System.err.println("Error closing ResultSet for getting Employee by Email Address.");
      }
    }

    return employee;
  }

	@Override
	public List<Employee> getByAttribute(String attributeName, Object attributeValue) {
    List<Employee> employees = new LinkedList<Employee>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select from Employee where " + attributeName + "=?";
      ps = connection.prepareStatement(sql);

      if(attributeName.equalsIgnoreCase("EmployeeID") || attributeName.equalsIgnoreCase("DirectSupervisorID") || attributeName.equalsIgnoreCase("DepartmentHeadID"))
        ps.setInt(1, (Integer)attributeValue);
      else if(attributeName.equalsIgnoreCase("LastLogin"))
        ps.setDate(1, Date.valueOf(((ZonedDateTime) attributeValue).toLocalDate()));
      else
        ps.setString(1, (String) attributeValue);
      
      rs = ps.executeQuery();

      populateList(employees, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return employees;
	}

	@Override
	public void createNew(Employee e) {
    PreparedStatement ps = null;
    
    try {
      String sql = "insert into Employee (FirstName, LastName, EmailAddress, Password, Salt, DirectSupervisorID, DepartmentID, EmployeeTypeID) values (?, ?, ?, ?, ?, ?, ?, ?)";

      ps = connection.prepareStatement(sql);
      ps.setString(1, e.getFirstName());
      ps.setString(2, e.getLastName());
      ps.setString(3, e.getEmailAddress());
      ps.setString(4, e.getPassword());
      ps.setString(5, e.getSalt());
      ps.setInt(6, e.getDirectSupervisorId());
      ps.setInt(7, e.getDepartmentId());
      ps.setInt(8, e.getEmployeeTypeId());

      ps.execute();
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException ex) {
        System.out.println("Error closing PreparedStatement after inserting to Employee.");
        ex.printStackTrace();
      }
    }
	}

	@Override
	public void deleteById(int id) {
    PreparedStatement ps = null;

    try {
      String sql = "delete from Employee where EmployeeID=?";

      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for deleting Employee # " + id);
      }
    }
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    
    try {
      String sql = "delete from Employee where " + attributeName + "=?";
      
      ps = connection.prepareStatement(sql);
      
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer)attributeValue);
      else if(attributeName.equalsIgnoreCase("LastLogin"))
        ps.setTimestamp(1, Timestamp.from(((ZonedDateTime)attributeValue).toInstant()));
      else
        ps.setString(1, (String) attributeValue);
      
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }  finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for deleting Employee(s) by " + attributeName + " of value " + attributeValue);
      }
    }
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    
    try {
      String sql = "update Employee set " + attributeName + "=? where EmployeeID=?";

      ps = connection.prepareStatement(sql);
      
      if(attributeName.equalsIgnoreCase("EmployeeID") || attributeName.equalsIgnoreCase("DirectSupervisorID") || attributeName.equalsIgnoreCase("DepartmentHeadID"))
        ps.setInt(1, (Integer)attributeValue);
      else if(attributeName.equalsIgnoreCase("LastLogin"))
        ps.setTimestamp(1, Timestamp.from(((ZonedDateTime)attributeValue).toInstant()));
      else
        ps.setString(1, (String) attributeValue);
      
      ps.setInt(2, id);

      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for updating " + attributeName + " of Employee # " + id);
      }
    }
	}

	@Override
	public int getCount() {
    CallableStatement cs = null;
    int employeeCount = 0;

    try {
      String sql = "{CALL SP_Get_Employee_Count(?)}";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      employeeCount = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }

		return employeeCount;
	}

}