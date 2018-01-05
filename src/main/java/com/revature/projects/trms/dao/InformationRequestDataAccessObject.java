package com.revature.projects.trms.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.revature.projects.trms.beans.InformationRequest;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.lang.model.util.ElementScanner6;

public class InformationRequestDataAccessObject extends GenericDataAccessObject<InformationRequest> {

  public InformationRequestDataAccessObject() {
    super();
  }

  private void populateList(List<InformationRequest> infoRequests, ResultSet rs) throws SQLException {
    while(rs.next()) {
      int irId = rs.getInt("InformationRequestID");
      int requesterId = rs.getInt("RequesterID");
      int requesteeId = rs.getInt("RequesteeID");
      int reimbursementId = rs.getInt("ReimbursementID");
      String requesterMessage = rs.getString("RequesterMessage");
      String requesterResponse = rs.getString("RequesteeResponse");
      ZonedDateTime requestDate = rs.getObject("RequestDate", ZonedDateTime.class);
      ZonedDateTime responseDate = rs.getObject("ResponseDate", ZonedDateTime.class);

      infoRequests.add(new InformationRequest(irId, requesterId, requesteeId, reimbursementId, requesterMessage, requesterResponse, requestDate, responseDate));
    }
  }

	@Override
	public List<InformationRequest> getAll() {
    List<InformationRequest> infoRequests = new LinkedList<InformationRequest>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from  InformationRequest";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      populateList(infoRequests, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
		return infoRequests;
	}

	@Override
	public InformationRequest getById(int id) {
    InformationRequest ir = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from InformationRequest where InformationRequestID=?";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      if(rs.next()) {
        int informationRequestId = rs.getInt("InformationRequestID");
        int requesterId = rs.getInt("RequesterID");
        int requesteeId = rs.getInt("RequesteeID");
        int reimbursementId = rs.getInt("ReimbursementID");
        String requesterMessage = rs.getString("RequesterMessage");
        String requesteeResponse = rs.getString("RequesteeResponse");
        ZonedDateTime requestDate = rs.getObject("RequestDate", ZonedDateTime.class);
        ZonedDateTime responseDate = rs.getObject("ResponseDate", ZonedDateTime.class);

        ir = new InformationRequest(
          informationRequestId,
          requesterId,
          requesteeId,
          reimbursementId,
          requesterMessage,
          requesteeResponse,
          requestDate,
          responseDate);
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
		return ir;
	}

	@Override
	public List<InformationRequest> getByAttribute(String attributeName, Object attributeValue) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<InformationRequest> infoRequests = new LinkedList<InformationRequest>();

    try {
      String sql = "select * from InformationRequest where " + attributeName + "=?";

      ps = connection.prepareStatement(sql);

      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Date"))
        ps.setDate(1, Date.valueOf(((ZonedDateTime)attributeValue).toLocalDate()));
      else
        ps.setString(1, (String) attributeValue);

      rs = ps.executeQuery();

      populateList(infoRequests, rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }

		return infoRequests;
	}

	@Override
	public void createNew(InformationRequest e) {
    PreparedStatement ps = null;
    
    try {
      String sql = "insert into InformationRequest (RequesterID, RequesteeID, ReimbursementID, RequesterMessage, RequesteeResponse, RequestDate, ResponseDate) values (?, ?, ?, ?, ?, ?, ?)";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, e.getRequesterId());
      ps.setInt(2, e.getRequesteeId());
      ps.setInt(3, e.getReimbursementId());
      ps.setString(4, e.getRequesterMessage());
      ps.setString(5, e.getRequesteeResponse());
      ps.setTimestamp(6, Timestamp.from(e.getRequestDate().toInstant()));
      ps.setTimestamp(7, Timestamp.from(e.getResponseDate().toInstant()));
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
      String sql = "delete from InformationRequest where InformationRequestID=?";
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
      String sql = "delete from InformationRequest where " + attributeName + "=?";
      
      ps = connection.prepareStatement(sql);
      
      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Date"))
        ps.setDate(1, Date.valueOf(((ZonedDateTime)attributeValue).toLocalDate()));
      else
        ps.setString(1, (String) attributeValue);

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
      String sql = "update InformationRequest set " + attributeName + "=? where InformationRequestID=?";

      ps = connection.prepareStatement(sql);

      if(attributeName.endsWith("ID"))
        ps.setInt(1, (Integer) attributeValue);
      else if(attributeName.endsWith("Date"))
        ps.setDate(1, Date.valueOf(((ZonedDateTime)attributeValue).toLocalDate()));
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
        e.printStackTrace();
      }
    }
	}

	@Override
	public int getCount() {
    CallableStatement cs = null;
    int infoRequestCount = 0;
    
    try {
      String sql = "{ CALL SP_Get_Info_Request_Count(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      infoRequestCount = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(cs != null && !cs.isClosed()) cs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return infoRequestCount;
	}

  @Override
  public int getCurrentID() {
    CallableStatement cs = null;
    int currentID = 0;

    try {
      String sql = "{ CALL SP_Get_Curr_InfoRequestID(?) }";
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