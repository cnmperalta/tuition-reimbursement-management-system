package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DepartmentDataAccessObject extends  GenericDataAccessObject<Department> {
  public DepartmentDataAccessObject() {
    super();
  }
	@Override
	public List<Department> getAll() {
    List<Department> departments = new LinkedList<Department>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Department";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      
      while(rs.next()) {
        int departmentId = rs.getInt("DepartmentID");
        String departmentName = rs.getString("DepartmentName");
        int departmentHeadId = rs.getInt("DepartmentHeadID");

        departments.add(new Department(departmentId, departmentName, departmentHeadId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return departments;
	}

	@Override
	public Department getById(int id) {
    Department d = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
      String sql = "select * from Department where DepartmentID=?";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      
      if(rs.next()) {
        int departmentId = rs.getInt("DepartmentID");
        String departmentName = rs.getString("DepartmentName");
        int departmentHeadId = rs.getInt("DepartmentHeadID");
        
        d = new Department(departmentId, departmentName, departmentHeadId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
		return d;
	}

	@Override
	public List<Department> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(Department e) {
		
	}

	@Override
	public void deleteById(int id) {
		
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
		
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
		
	}

	@Override
	public int getCount() {
		return 0;
	}

}