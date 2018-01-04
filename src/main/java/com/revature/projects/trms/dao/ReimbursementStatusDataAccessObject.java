package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.ReimbursementStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReimbursementStatusDataAccessObject extends GenericDataAccessObject<ReimbursementStatus> {

  public ReimbursementStatusDataAccessObject() {
    super();
  }

	@Override
	public List<ReimbursementStatus> getAll() {
    List<ReimbursementStatus> reimbursementStatuses = new LinkedList<ReimbursementStatus>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from ReimbursementStatus";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      while(rs.next()) {
        int reimbursementStatusId = rs.getInt("ReimbursementStatusId");
        String reimbursementStatus = rs.getString("ReimbursementStatus");

        reimbursementStatuses.add(new ReimbursementStatus(
          reimbursementStatusId,
          reimbursementStatus));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (Exception e) {
        //TODO: handle exception
      }
    }
		return reimbursementStatuses;
	}

	@Override
	public ReimbursementStatus getById(int id) {
		return null;
	}

	@Override
	public List<ReimbursementStatus> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(ReimbursementStatus e) {
		
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