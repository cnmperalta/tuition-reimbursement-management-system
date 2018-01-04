package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.GradingFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GradingFormatDataAccessObject extends GenericDataAccessObject<GradingFormat> {

  public GradingFormatDataAccessObject() {
    super();
  }

	@Override
	public List<GradingFormat> getAll() {
    List<GradingFormat> gradingFormats = new LinkedList<GradingFormat>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from GradingFormat";

      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      while(rs.next()) {
        int gradingFormatId = rs.getInt("GradingFormatID");
        String gradingFormat = rs.getString("GradingFormat");

        gradingFormats.add(new GradingFormat(gradingFormatId, gradingFormat));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();  
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      try {
        if(rs != null && !rs.isClosed()) rs.close();  
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return gradingFormats;
	}

	@Override
	public GradingFormat getById(int id) {
		return null;
	}

	@Override
	public List<GradingFormat> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(GradingFormat e) {
		
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