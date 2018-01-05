package com.revature.projects.trms.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.revature.projects.trms.beans.EmployeeType;

public class EmployeeTypeDataAccessObject extends GenericDataAccessObject<EmployeeType> {
  public EmployeeTypeDataAccessObject() {
    super();
  }

	@Override
	public List<EmployeeType> getAll() {
		List<EmployeeType> employeeTypes = new LinkedList<EmployeeType>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from EmployeeType";

      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      
      while(rs.next()) {
        int employeeTypeId = rs.getInt("EmployeeTypeID");
        String employeeType = rs.getString("EmployeeType");

        employeeTypes.add(new EmployeeType(employeeTypeId, employeeType));
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

    return employeeTypes;
  }

	@Override
	public EmployeeType getById(int id) {
    EmployeeType empType = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from EmployeeType where EmployeeTypeID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();

      if(rs.next()) {
        int employeeTypeId = rs.getInt("EmployeeTypeID");
        String employeeType = rs.getString("EmployeeType");

        empType = new EmployeeType(employeeTypeId, employeeType);
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

		return empType;
	}

	@Override
	public List<EmployeeType> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(EmployeeType e) {
		
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
  
  @Override
  public int getCurrentID() {
    CallableStatement cs = null;
    int currentID = 0;

    try {
      String sql = "{ CALL SP_Get_Curr_EmpTypeID(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      currentID = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(cs != null && !cs.isClosed()) cs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return currentID;
  }

}