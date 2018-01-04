package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.ReimbursementResponse;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

public class ReimbursementResponseDataAccessObject extends GenericDataAccessObject<ReimbursementResponse> {

  public ReimbursementResponseDataAccessObject() {
    super();
  }

  private void populateList(List<ReimbursementResponse> responses, ResultSet rs) throws SQLException {
    while(rs.next()) {
      int reimbursementResponseId = rs.getInt("ReimbursementResponseID");
      int responderId = rs.getInt("ResponderID");
      BigDecimal amountAwarded = rs.getBigDecimal("ReimbursementAmountAwarded");
      BigDecimal amountExceeded = rs.getBigDecimal("ReimbursementAmountExceeded");
      String exceededReason = rs.getString("ReimbursementExceedReason");
      String disapprovalReason = rs.getString("DisapprovalReason");

      responses.add(new ReimbursementResponse(
        reimbursementResponseId,
        responderId,
        amountAwarded,
        amountExceeded,
        exceededReason,
        disapprovalReason
      ));
    }
  }

	@Override
	public List<ReimbursementResponse> getAll() {
    List<ReimbursementResponse> responses = new LinkedList<ReimbursementResponse>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from ReimbursementResponse";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      populateList(responses, rs);
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

    return responses;
	}

	@Override
	public ReimbursementResponse getById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    ReimbursementResponse response = null;

    try {
      String sql = "select * from  ReimbursementResponse where ReimbursementResponseID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();

      if(rs.next()) {
        int reimbursementResponseId = rs.getInt("ReimbursementResponseID");
        int responderId = rs.getInt("ResponderID");
        BigDecimal amountAwarded = rs.getBigDecimal("ReimbursementAmountAwarded");
        BigDecimal amountExceeded = rs.getBigDecimal("ReimbursementAmountExceeded");
        String exceededReason = rs.getString("ReimbursementExceedReason");
        String disapprovalReason = rs.getString("DisapprovalReason");

        response = new ReimbursementResponse(
          reimbursementResponseId,
          responderId,
          amountAwarded,
          amountExceeded,
          exceededReason,
          disapprovalReason
        );
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
		return response;
	}

	@Override
	public List<ReimbursementResponse> getByAttribute(String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<ReimbursementResponse> responses = new LinkedList<ReimbursementResponse>();

    try {
      String sql = "select * from ReimbursementResponse where " + attributeName + "=?";
      ps = connection.prepareStatement(sql);
      
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Reason"))
        ps.setString(1, (String) attributeValue);
      else
        ps.setBigDecimal(1, (BigDecimal) attributeValue);
      
      rs = ps.executeQuery();

      populateList(responses, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps!= null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs!= null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

		return responses;
	}

	@Override
	public void createNew(ReimbursementResponse e) {
    PreparedStatement ps = null;

    try {
      String sql = "insert into ReimbursementResponse (ResponderID, ReimbursementAmountAwarded, ReimbursementAmountExceeded, ReimbursementExceedReason, DisapprovalReason) values (?, ?, ?, ?, ?)";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, e.getResponderId());
      ps.setBigDecimal(2, e.getReimbursementAmountAwarded());
      ps.setBigDecimal(3, e.getReimbursementAmountExceeded());
      ps.setString(4, e.getReimbursementExceedReason());
      ps.setString(5, e.getDisapprovalReason());

      ps.execute();
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
	}

	@Override
	public void deleteById(int id) {
    PreparedStatement ps = null;
    
    try {
      String sql = "delete from ReimbursementResponse where ReimbursementResponseID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    
    try {
      String sql = "delete from ReimbursementResponse where " + attributeValue + "=?";
      ps = connection.prepareStatement(sql);
      
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Reason"))
        ps.setString(1, (String) attributeValue);
      else
        ps.setBigDecimal(1, (BigDecimal) attributeValue);

      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    
    try {
      String sql = "update ReimbursementResponse set " + attributeValue + "=? where ReimbursementResponseID=?";
      ps = connection.prepareStatement(sql);
      
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Reason"))
        ps.setString(1, (String) attributeValue);
      else
        ps.setBigDecimal(1, (BigDecimal) attributeValue);

      ps.setInt(2, id);

      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
	}

	@Override
	public int getCount() {
    CallableStatement cs = null;
    int reimbursementResponseCount = 0;
    
    try {
      String sql = "{ CALL SP_Get_ReimResponse_Count(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      reimbursementResponseCount = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(cs != null && !cs.isClosed()) cs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    
    return reimbursementResponseCount;
	}

}